package com.id.todolist.domain

import com.id.todolist.data.Resource
import com.id.todolist.domain.model.TodoModel
import kotlinx.coroutines.flow.Flow

interface IRepository {
    fun fetchTodos(): Flow<Resource<List<TodoModel>>>
    fun fetchTodo(id: String): Flow<Resource<TodoModel>>
    fun deleteTodo(id: String): Flow<Resource<String>>
    fun updateTodo(id: String, isComplete: Boolean): Flow<Resource<Boolean>>
    fun addTodo(todoName: String, isComplete: Boolean): Flow<Resource<Boolean>>
}