package com.example.academicarchitecturelab_g2.domain.usecase

import com.example.academicarchitecturelab_g2.domain.model.AcademicGrade
import com.example.academicarchitecturelab_g2.domain.repository.GradeRepository
import java.util.UUID

class AddGradeUseCase(private val repository: GradeRepository) {
    suspend operator fun invoke(activityName: String, subject: String, grade: Double) {
        if (activityName.trim().isBlank()) {
            throw IllegalArgumentException("El nombre de la actividad no puede estar vacío.")
        }
        if (subject.trim().isBlank()) {
            throw IllegalArgumentException("La asignatura no puede estar vacía.")
        }
        if (grade < 0.0 || grade > 10.0) {
            throw IllegalArgumentException("La nota debe estar estrictamente en el rango de 0.0 a 10.0.")
        }
        val academicGrade = AcademicGrade(
            id = UUID.randomUUID().toString(),
            activityName = activityName.trim(),
            subject = subject.trim(),
            grade = grade
        )
        repository.addGrade(academicGrade)
    }
}
