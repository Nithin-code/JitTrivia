package com.example.jittrivia.network


import com.example.jittrivia.model.QuestionItem
import retrofit2.http.GET

interface APIInterface {

    @GET("master/world.json")
    suspend fun getQuestionsList() : List<QuestionItem>

}