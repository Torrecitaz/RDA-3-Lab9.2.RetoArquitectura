package com.example.academicarchitecturelab_g2.ui.presentation.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.academicarchitecturelab_g2.domain.repository.AcademicTaskRepository
import com.example.academicarchitecturelab_g2.domain.usecase.AddTaskUseCase
import com.example.academicarchitecturelab_g2.domain.usecase.GetTasksUseCase

class AcademicTaskViewModelFactory(
    private val repository: AcademicTaskRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AcademicTaskViewModel::class.java)) {
            return AcademicTaskViewModel(
                GetTasksUseCase(repository),
                AddTaskUseCase(repository),
                repository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
