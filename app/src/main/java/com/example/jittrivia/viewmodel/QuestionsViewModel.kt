package com.example.jittrivia.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jittrivia.MyApplication
import com.example.jittrivia.model.QuestionItem
import com.example.jittrivia.network.APIInterface
import com.example.jittrivia.utils.UIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class QuestionsViewModel : ViewModel() {
    private val apiInterface: APIInterface = MyApplication.mAPIInterface
    private var _questions : List<QuestionItem> = emptyList()

    var questionsList = _questions

    var ui_state = mutableStateOf(UIState.LOADING)

    fun getResponse(){
        try {
            viewModelScope.launch {
                val response = apiInterface.getQuestionsList()
                _questions = response
                questionsList = _questions
                Log.d("SIZE view" , questionsList.size.toString())
                ui_state.value = UIState.SUCCESS
            }
        }catch (t : Exception){
            ui_state.value = UIState.ERROR
        }
    }

}