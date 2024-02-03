package com.example.jittrivia.home_screen

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.jittrivia.components.ShowProgressBar
import com.example.jittrivia.data.RadioButtonState
import com.example.jittrivia.data.ScreenState
import com.example.jittrivia.model.QuestionItem
import com.example.jittrivia.ui.theme.backgroundBlue
import com.example.jittrivia.ui.theme.darkPurple
import com.example.jittrivia.utils.UIState
import com.example.jittrivia.viewmodel.QuestionsViewModel
import kotlinx.coroutines.Dispatchers

@Composable
fun HomeScreen(navHostController: NavHostController, questionsViewModel: QuestionsViewModel) {



    val ui_state = remember {
        questionsViewModel.ui_state
    }

    LaunchedEffect(Dispatchers.IO){
        questionsViewModel.getResponse()
    }

    when(ui_state.value){
        UIState.LOADING->{
            Log.d("Size load" , questionsViewModel.questionsList.toString())
            ShowProgressBar()
        }
        UIState.SUCCESS->{
            Log.d("Size suc" , questionsViewModel.questionsList.toString())
            QuestionsScreen(questionsViewModel.questionsList)
        }
        UIState.ERROR->{

        }
    }

}

@Composable
fun QuestionsScreen(questions: List<QuestionItem>?) {

    if (!questions.isNullOrEmpty()){




        //initially 0th item in the list should be displayed.
        val screenState = remember {
            mutableStateOf(
                ScreenState(
                    questionNo = 0,
                    selectedRbIdx = -1,
                    answerIdx = -1,
                    questions[0].choices
                )
            )
        }
        val questionNo = screenState.value.questionNo

        val selectedPosition = remember {
            mutableStateOf(-1)
        }
        val question = questions[screenState.value.questionNo]
        val choices = question.choices

        var answerPosition = -1

        for (i in choices.indices){
            if(question.answer == choices[i]){
                answerPosition = i
            }
        }

        val radioButtonState = RadioButtonState(
            choices = choices,-1
        )


        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = backgroundBlue)
                .padding(16.dp)
        ) {

            Text(
                text = "Question ${questionNo+1} / ${questions.size}",
                style = TextStyle(
                    color = Color.White ,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier.padding(start = 15.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            DrawDottedLine(
                pathEffect = PathEffect
                    .dashPathEffect(
                        floatArrayOf(10f,10f) , 0f
                    )
            )

            Text(
                text = question.question,
                style = TextStyle(
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W400
                ),
                modifier = Modifier
                    .padding(top = 26.dp)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(50.dp))

            // choices

            CreateRadioButtonGroup(screenState.value){idx->
                screenState.value = ScreenState(questionNo , selectedRbIdx = idx , -1 , choices)
            }




            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){
                Button(onClick = {

                    if (screenState.value.questionNo<questions.size){
                        screenState.value = ScreenState(screenState.value.questionNo+1 ,-1,-1,questions[screenState.value.questionNo+1].choices)
                    }

                }) {
                    Text(text = "Next")
                }
            }

        }
    }

}

@Composable
fun CreateRadioButtonGroup(screenState: ScreenState , onClick : (Int)->Unit) {


    screenState.choices.forEachIndexed { index, optionTxt ->

        Row(
            modifier = Modifier
                .padding(3.dp)
                .fillMaxWidth()
                .height(45.dp)
                .border(
                    width = 2.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            darkPurple,
                            darkPurple
                        )
                    ),
                    shape = RoundedCornerShape(15.dp)
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            RadioButton(
                selected = (index == screenState.selectedRbIdx) ,
                onClick = {
                    onClick.invoke(index)
                }
            )

            Text(
                text = optionTxt,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W300,
                    color = Color.White
                )
            )

        }

        Spacer(modifier = Modifier.height(10.dp))
    }



}

fun getColorByState(index: Int, selectedItemPosition: Int): Color {
    return if(selectedItemPosition==-1){
        Color.White
    }
    else if (index == selectedItemPosition){
        Color.Green
    }
    else{
        Color.Red
    }
}

@Composable
fun DrawDottedLine(
    pathEffect: PathEffect
){

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp),
        onDraw = {
            drawLine(
                color = Color.LightGray,
                start = Offset(0f,0f),
                end = Offset(size.width, y = 0f),
                pathEffect = pathEffect
            )
        }
    )

}



@Preview
@Composable
fun Demo(){
    QuestionsScreen(
        questions = listOf(
            QuestionItem("How are you" , "Question",
                answer = "I am Fine",
                choices = listOf("I am Fine" , "I am Not Fine")
            )
        )
    )
}
