package com.example.academicarchitecturelab_g2.ui.presentation.grades

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.academicarchitecturelab_g2.domain.usecase.AddGradeUseCase
import com.example.academicarchitecturelab_g2.domain.usecase.GetGradesUseCase

class GradeViewModelFactory(
    private val getGradesUseCase: GetGradesUseCase,
    private val addGradeUseCase: AddGradeUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GradeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GradeViewModel(getGradesUseCase, addGradeUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
