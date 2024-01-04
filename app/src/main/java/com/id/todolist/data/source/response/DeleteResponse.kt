package com.id.todolist.data.source.response

import com.google.gson.annotations.SerializedName

data class DeleteResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String
)
