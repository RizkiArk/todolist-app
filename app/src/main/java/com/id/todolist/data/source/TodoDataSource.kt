package com.id.todolist.data.source

import com.id.todolist.data.ApiResponse
import com.id.todolist.data.source.request.AddTodoRequest
import com.id.todolist.data.source.request.UpdateTodoRequest
import com.id.todolist.data.source.response.DeleteResponse
import com.id.todolist.data.source.response.TodoResponse
import com.id.todolist.data.source.response.TodosResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject


class TodoDataSource @Inject constructor(
    private val apiService: ApiService
) {
    fun fetchTodos(): Flow<ApiResponse<TodosResponse>> = flow {
        emit(
            try {
                val response = apiService.fetchTodos()
                when (response.code) {
                    200 -> ApiResponse.Success(response)
                    else -> throw Exception()
                }
            } catch (e: Exception) {
                ApiResponse.Error(e.message.toString())
            }
        )
    }

    fun fetchTodo(id: String): Flow<ApiResponse<TodoResponse>> = flow {
        emit(
            try {
                val response = apiService.fetchTodo(id)
                when (response.code) {
                    200 -> ApiResponse.Success(response)
                    else -> throw Exception()
                }
            } catch (e: Exception) {
                ApiResponse.Error(e.message.toString())
            }
        )
    }

    fun deleteTodo(id: String): Flow<ApiResponse<DeleteResponse>> = flow {
        emit(
            try {
                val response = apiService.deleteTodo(id)
                when (response.code) {
                    200 -> ApiResponse.Success(response)
                    else -> throw Exception()
                }
            } catch (e: Exception) {
                ApiResponse.Error(e.message.toString())
            }
        )
    }

    fun updateTodo(id: String, isComplete: Boolean) = flow {
        emit(
            try {
                val response = apiService.updateTodo(id, UpdateTodoRequest(isComplete))
                when (response.code) {
                    200 -> ApiResponse.Success(true)
                    else -> throw Exception()
                }
            } catch (e: Exception) {
                ApiResponse.Error(e.message.toString())
            }
        )
    }

    fun addTodo(todoName: String, isComplete: Boolean) = flow {
        emit(
            try {
                val response = apiService.addTodo(AddTodoRequest(todoName, isComplete))
                when (response.code) {
                    200 -> ApiResponse.Success(true)
                    else -> throw Exception()
                }
            } catch (e: Exception) {
                ApiResponse.Error(e.message.toString())
            }
        )
    }
}