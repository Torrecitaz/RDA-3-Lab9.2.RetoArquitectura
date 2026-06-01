package com.example.academicarchitecturelab_g2.domain.repository

import com.example.academicarchitecturelab_g2.domain.model.AcademicGrade
import kotlinx.coroutines.flow.Flow

interface GradeRepository {
    fun getGradesStream(): Flow<List<AcademicGrade>>
    suspend fun addGrade(grade: AcademicGrade)
}
