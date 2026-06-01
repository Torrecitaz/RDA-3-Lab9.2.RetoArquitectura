package com.example.academicarchitecturelab_g2.domain.repository

import com.example.academicarchitecturelab_g2.domain.model.AcademicTask
import kotlinx.coroutines.flow.Flow

interface AcademicTaskRepository {
    fun getTasksStream(): Flow<List<AcademicTask>>
    suspend fun addTask(title: String)
    suspend fun toggleTaskCompletion(taskId: String)
}
