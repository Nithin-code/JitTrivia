package com.example.jittrivia.naviagtion

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.jittrivia.home_screen.HomeScreen
import com.example.jittrivia.utils.Screens
import com.example.jittrivia.viewmodel.QuestionsViewModel

@Composable
fun MyNavGraph(navHostController: NavHostController){

    NavHost(navController = navHostController, startDestination = Screens.HomeScreen.route){

        composable(Screens.HomeScreen.route){
            val questionsViewModel = QuestionsViewModel()
            HomeScreen(navHostController , questionsViewModel)
        }



    }

}


