package com.id.todolist.data

import com.id.todolist.data.source.TodoDataSource
import com.id.todolist.domain.IRepository
import com.id.todolist.domain.model.TodoModel
import com.id.todolist.domain.model.TodoModelMapper.mapTodoResponseToModel
import com.id.todolist.domain.model.TodoModelMapper.mapTodosResponseToModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class Repository @Inject constructor(
    private val dataSource: TodoDataSource
): IRepository {
    override fun fetchTodos(): Flow<Resource<List<TodoModel>>> = flow {
        emit(Resource.Loading())
        emit(when (val response = dataSource.fetchTodos().first()) {
            is ApiResponse.Error -> Resource.Error(response.error)
            is ApiResponse.Success -> Resource.Success(mapTodosResponseToModel(response.data.data).reversed())
        })
    }

    override fun fetchTodo(id: String): Flow<Resource<TodoModel>> = flow {
        emit(Resource.Loading())
        emit(when (val response = dataSource.fetchTodo(id).first()) {
            is ApiResponse.Error -> Resource.Error(response.error)
            is ApiResponse.Success -> Resource.Success(mapTodoResponseToModel(response.data.data))
        })
    }

    override fun deleteTodo(id: String): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        emit(when (val response = dataSource.deleteTodo(id).first()) {
            is ApiResponse.Error -> Resource.Error(response.error)
            is ApiResponse.Success -> Resource.Success(response.data.message)
        })
    }

    override fun updateTodo(id: String, isComplete: Boolean): Flow<Resource<Boolean>>  = flow {
        emit(Resource.Loading())
        emit(when (val response = dataSource.updateTodo(id, isComplete).first()) {
            is ApiResponse.Error -> Resource.Error(response.error)
            is ApiResponse.Success -> Resource.Success(response.data)
        })
    }

    override fun addTodo(todoName: String, isComplete: Boolean): Flow<Resource<Boolean>>  = flow {
        emit(Resource.Loading())
        emit(when (val response = dataSource.addTodo(todoName, isComplete).first()) {
            is ApiResponse.Error -> Resource.Error(response.error)
            is ApiResponse.Success -> Resource.Success(response.data)
        })
    }
}