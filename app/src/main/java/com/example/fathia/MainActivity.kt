package com.example.fathia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.fathia.model.MahasiswaViewModel
import com.example.fathia.model.MahasiswaViewModelFactory
import com.example.fathia.ui.theme.CoroutinesTheme

class MainActivity : ComponentActivity() {

    // Create the repository instance
    private val repository = Repository()

    // Use a custom factory to provide the repository to MahasiswaViewModel
    private val authViewModel: MahasiswaViewModel by viewModels {
        MahasiswaViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoroutinesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Navigation(
                        modifier = Modifier.padding(innerPadding),
                        authViewModel = authViewModel
                    )
                }
            }
        }
    }
}
