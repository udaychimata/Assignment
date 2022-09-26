package com.example.assignment.viewmodels


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment.data.Item
import com.example.assignment.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject


@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class DisplayMissionViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private val _uiState = MutableLiveData<Result<List<Item>>>()
    val uiState: LiveData<Result<List<Item>>>
        get() = _uiState
    private val _isLoading = mutableStateOf(false)
    val isLoading: MutableState<Boolean>
        get() = _isLoading
    var dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/uuuu", Locale.US)
    val selectedItemMissionDetails = mutableStateOf("")
    val selectedItem = mutableStateOf(-1)

    init {
        getList()
    }

    private fun getList() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val result = repository.getList()
                for (item in result) {
                    item.launch_date_local =
                        dateTimeFormatter.format(OffsetDateTime.parse(item.launch_date_local))
                }
                val newList = result.reversed()
                _uiState.value = Result.success(newList)
                _isLoading.value = false
            } catch (exception: Exception) {
                _uiState.value = Result.failure(exception)
                _isLoading.value = false
            }
        }
    }
}