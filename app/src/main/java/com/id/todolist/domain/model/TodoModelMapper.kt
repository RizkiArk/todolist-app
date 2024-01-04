package com.id.todolist.domain.model

import com.id.todolist.data.source.response.TodoDataResponse

object TodoModelMapper {

    fun mapTodosResponseToModel(data: List<TodoDataResponse>): List<TodoModel> {
        return data.map {
            mapTodoResponseToModel(it)
        }
    }

    fun mapTodoResponseToModel(data: TodoDataResponse): TodoModel {
        return TodoModel(
            id = data.id, todoName = data.todoName, isCompleted = data.isCompleted
        )
    }
}