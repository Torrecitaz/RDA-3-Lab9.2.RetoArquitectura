package com.example.academicarchitecturelab_g2

import android.app.Application
import com.example.academicarchitecturelab_g2.data.repository.InMemoryGradeRepository
import com.example.academicarchitecturelab_g2.domain.repository.GradeRepository

class AcademicApplication : Application() {
    val repository: GradeRepository by lazy { InMemoryGradeRepository() }
}
