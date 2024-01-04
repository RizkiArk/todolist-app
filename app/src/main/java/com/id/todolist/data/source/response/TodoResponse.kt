package com.id.todolist.data.source.response

import com.google.gson.annotations.SerializedName

data class TodoResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val data: TodoDataResponse
)
