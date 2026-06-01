package com.example.academicarchitecturelab_g2

import android.app.Application
import com.example.academicarchitecturelab_g2.data.repository.InMemoryTaskRepository
import com.example.academicarchitecturelab_g2.domain.repository.AcademicTaskRepository

class AcademicApplication : Application() {
    val repository: AcademicTaskRepository by lazy { InMemoryTaskRepository() }
}
