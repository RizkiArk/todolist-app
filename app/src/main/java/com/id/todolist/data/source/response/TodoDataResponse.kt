package com.id.todolist.data.source.response

import com.google.gson.annotations.SerializedName

data class TodoDataResponse(
    @SerializedName("_id")
    val id: String,
    @SerializedName("todoName")
    val todoName: String,
    @SerializedName("isComplete")
    val isCompleted: Boolean
)
