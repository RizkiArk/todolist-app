package com.id.todolist.data.source

import com.id.todolist.data.source.request.AddTodoRequest
import com.id.todolist.data.source.request.UpdateTodoRequest
import com.id.todolist.data.source.response.DeleteResponse
import com.id.todolist.data.source.response.TodoResponse
import com.id.todolist.data.source.response.TodosResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface ApiService {
    @GET("todos/")
    suspend fun fetchTodos(): TodosResponse

    @GET("todos/{id}")
    suspend fun fetchTodo(@Path("id") id: String): TodoResponse

    @DELETE("todos/{id}")
    suspend fun deleteTodo(@Path("id") id: String): DeleteResponse

    @PUT("todos/{id}")
    suspend fun updateTodo(
        @Path("id") od: String,
        @Body requestBody: UpdateTodoRequest
    ): TodoResponse

    @POST("todos/")
    suspend fun addTodo(@Body requestBody: AddTodoRequest): TodoResponse
}