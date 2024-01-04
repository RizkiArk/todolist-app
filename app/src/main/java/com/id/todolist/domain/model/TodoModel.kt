package com.id.todolist.domain.model

data class TodoModel(
    val id: String,
    val todoName: String,
    val isCompleted: Boolean
) {
    companion object {
        fun empty(): TodoModel = TodoModel(id = "", todoName = "", isCompleted = false)
    }
}
