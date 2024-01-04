package com.id.todolist.ui.screen.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.id.todolist.data.Resource
import com.id.todolist.domain.IRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val repository: IRepository
): ViewModel() {
    val _uiState = MutableStateFlow(AddUiState())
    val uiState: StateFlow<AddUiState> = _uiState.asStateFlow()

    fun addTodo(todoName: String, isComplete: Boolean) {
        viewModelScope.launch {
            repository.addTodo(todoName, isComplete).collect { res ->
                when (res) {
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                isError = true,
                                message = res.message.toString()
                            )
                        }
                    }
                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy(
                                isSuccess = false,
                                isError = false,
                                isLoading = true,
                            )
                        }
                    }
                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                isSuccess = true,
                                isError = false,
                                isLoading = false,
                            )
                        }
                    }
                }
            }
        }
    }
}

fun AddViewModel.resetField() {
    _uiState.update {
        it.copy(
            isLoading = false,
            isSuccess = false,
            isError = false,
            message = "",
        )
    }
}

data class AddUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isError: Boolean = false,

    val message: String = ""
)