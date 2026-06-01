package com.example.academicarchitecturelab_g2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.academicarchitecturelab_g2.domain.usecase.AddGradeUseCase
import com.example.academicarchitecturelab_g2.domain.usecase.GetGradesUseCase
import com.example.academicarchitecturelab_g2.ui.presentation.grades.GradeApp
import com.example.academicarchitecturelab_g2.ui.presentation.grades.GradeViewModel
import com.example.academicarchitecturelab_g2.ui.presentation.grades.GradeViewModelFactory

class MainActivity : ComponentActivity() {

    private val viewModel: GradeViewModel by viewModels {
        val app = application as AcademicApplication
        val repository = app.repository
        val getGradesUseCase = GetGradesUseCase(repository)
        val addGradeUseCase = AddGradeUseCase(repository)
        GradeViewModelFactory(getGradesUseCase, addGradeUseCase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GradeApp(viewModel = viewModel)
        }
    }
}
