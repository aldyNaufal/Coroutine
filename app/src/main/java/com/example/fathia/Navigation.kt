package com.example.fathia

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fathia.model.MahasiswaViewModel

@Composable
fun Navigation(modifier: Modifier = Modifier, authViewModel: MahasiswaViewModel){

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home", builder = {
        composable("home"){
            GetAllMahasiswa(modifier, navController, authViewModel )
        }
        composable("search"){
            SearchMahasiswa(modifier, navController, authViewModel )
        }
        composable("update"){
            UpdateMahasiswa(modifier, navController, authViewModel)
        }
    })
}