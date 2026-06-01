package com.example.academicarchitecturelab_g2.domain.usecase

import com.example.academicarchitecturelab_g2.domain.model.AcademicTask
import com.example.academicarchitecturelab_g2.domain.repository.AcademicTaskRepository
import kotlinx.coroutines.flow.Flow

class GetTasksUseCase(private val repository: AcademicTaskRepository) {
    operator fun invoke(): Flow<List<AcademicTask>> {
        return repository.getTasksStream()
    }
}
