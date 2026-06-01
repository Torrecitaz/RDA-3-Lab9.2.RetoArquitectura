package com.example.academicarchitecturelab_g2.domain.usecase

import com.example.academicarchitecturelab_g2.domain.model.AcademicGrade
import com.example.academicarchitecturelab_g2.domain.repository.GradeRepository
import kotlinx.coroutines.flow.Flow

class GetGradesUseCase(private val repository: GradeRepository) {
    operator fun invoke(): Flow<List<AcademicGrade>> {
        return repository.getGradesStream()
    }
}
