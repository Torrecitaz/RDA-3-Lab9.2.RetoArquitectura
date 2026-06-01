package com.example.academicarchitecturelab_g2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.academicarchitecturelab_g2.ui.presentation.tasks.AcademicTaskApp
import com.example.academicarchitecturelab_g2.ui.presentation.tasks.AcademicTaskViewModel
import com.example.academicarchitecturelab_g2.ui.presentation.tasks.AcademicTaskViewModelFactory

class MainActivity : ComponentActivity() {

    private val viewModel: AcademicTaskViewModel by viewModels {
        AcademicTaskViewModelFactory((application as AcademicApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AcademicTaskApp(viewModel = viewModel)
        }
    }
}
