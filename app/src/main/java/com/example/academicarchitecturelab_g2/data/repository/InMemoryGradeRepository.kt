package com.example.academicarchitecturelab_g2.data.repository

import com.example.academicarchitecturelab_g2.domain.model.AcademicGrade
import com.example.academicarchitecturelab_g2.domain.repository.GradeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.UUID

class InMemoryGradeRepository : GradeRepository {

    private val gradesFlow = MutableStateFlow<List<AcademicGrade>>(
        listOf(
            AcademicGrade(
                id = UUID.randomUUID().toString(),
                activityName = "Práctica del Laboratorio 8",
                subject = "Programación Móvil",
                grade = 8.5
            ),
            AcademicGrade(
                id = UUID.randomUUID().toString(),
                activityName = "Examen Parcial",
                subject = "Arquitectura de Software",
                grade = 9.2
            )
        )
    )

    override fun getGradesStream(): Flow<List<AcademicGrade>> = gradesFlow

    override suspend fun addGrade(grade: AcademicGrade) {
        gradesFlow.value = gradesFlow.value + grade
    }
}
