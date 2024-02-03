package com.example.jittrivia.model

import com.google.gson.annotations.SerializedName

data class QuestionItem(
    @SerializedName("question")
    val question : String = "",
    @SerializedName("category")
    val category : String = "",
    @SerializedName("answer")
    val answer : String = "",
    @SerializedName("choices")
    val choices : List<String> = emptyList()
)
