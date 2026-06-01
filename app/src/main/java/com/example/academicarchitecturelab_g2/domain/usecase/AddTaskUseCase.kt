package com.example.academicarchitecturelab_g2.domain.usecase

import com.example.academicarchitecturelab_g2.domain.repository.AcademicTaskRepository

class AddTaskUseCase(private val repository: AcademicTaskRepository) {
    suspend operator fun invoke(title: String) {
        if (title.trim().isBlank()) {
            throw IllegalArgumentException("El título de la tarea académica no puede estar vacío.")
        }
        repository.addTask(title.trim())
    }
}
