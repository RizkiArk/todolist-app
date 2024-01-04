package com.id.todolist.ui.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.id.todolist.data.Resource
import com.id.todolist.domain.IRepository
import com.id.todolist.domain.model.TodoModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: IRepository
): ViewModel() {
    val _uiState = MutableStateFlow(HomeUIState())
    val uiState: StateFlow<HomeUIState> = _uiState.asStateFlow()

    init {
        fetchTodos()
    }
    fun fetchTodos() {
        viewModelScope.launch {
            repository.fetchTodos().collect {res ->
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
                                listTodo = res.data ?: listOf(),
                                message = ""
                            )
                        }
                    }
                }
            }
        }
    }

    fun deleteTodo(id: String, todoName: String) {
        viewModelScope.launch {
            repository.deleteTodo(id).collect {res ->
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
                                message = res.data.toString() + todoName
                            )
                        }
                        fetchTodos()
                    }
                }
            }
        }
    }

    fun updateTodo(id: String, todoName: String, isComplete: Boolean) {
        viewModelScope.launch {
            repository.updateTodo(id, isComplete).collect {res ->
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
                                message = "Successful Update Todo $todoName to ${isComplete.toString()}"
                            )
                        }
                        fetchTodos()
                    }
                }
            }

        }
    }
}

data class HomeUIState(
    val isSuccess: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false,

    val listTodo: List<TodoModel> = listOf(),
    val message: String = "",
)