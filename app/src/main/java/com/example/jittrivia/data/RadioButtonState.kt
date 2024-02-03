package com.example.jittrivia.data

import androidx.compose.ui.graphics.Color
import com.example.jittrivia.model.QuestionItem
import com.example.jittrivia.ui.theme.darkPurple


data class RadioButtonState(
    var choices : List<String>,
    var selectedItemPosition : Int = -1
)

data class ScreenState(
    var questionNo : Int = 0,
    var selectedRbIdx : Int = -1,
    var answerIdx : Int = -1,
    var choices: List<String>
)
