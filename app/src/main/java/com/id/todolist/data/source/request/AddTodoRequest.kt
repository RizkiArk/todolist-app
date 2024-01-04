package com.id.todolist.data.source.request

data class AddTodoRequest(
    val todoName: String,
    val isComplete: Boolean
)
