package com.example.academicarchitecturelab_g2.ui.presentation.tasks

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.academicarchitecturelab_g2.domain.repository.AcademicTaskRepository
import com.example.academicarchitecturelab_g2.domain.usecase.AddTaskUseCase
import com.example.academicarchitecturelab_g2.domain.usecase.GetTasksUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AcademicTaskViewModel(
    private val getTasksUseCase: GetTasksUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val repository: AcademicTaskRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<AcademicTaskUiState>(AcademicTaskUiState.Loading)

    val uiState: StateFlow<AcademicTaskUiState> = _uiState.asStateFlow()

    var currentScreen by mutableStateOf(ScreenType.LIST)
        private set

    var newTaskTitle by mutableStateOf("")
        private set

    init {
        observeTasks()
    }

    private fun observeTasks() {
        viewModelScope.launch {
            try {
                getTasksUseCase().collect { taskList ->
                    _uiState.value = AcademicTaskUiState.Success(taskList)
                }
            } catch (e: Exception) {
                _uiState.value = AcademicTaskUiState.Error("Error crítico al cargar el listado: ${e.localizedMessage}")
            }
        }
    }

    fun onTaskCheckedChange(taskId: String) {
        viewModelScope.launch {
            try {
                repository.toggleTaskCompletion(taskId)
            } catch (e: Exception) {
                _uiState.value = AcademicTaskUiState.Error("No se pudo actualizar el estado de la tarea.")
            }
        }
    }

    fun onNavigateToCreate() {
        newTaskTitle = ""
        currentScreen = ScreenType.CREATE
    }

    fun onNavigateToList() {
        currentScreen = ScreenType.LIST
    }

    fun onTaskTitleChange(newTitle: String) {
        newTaskTitle = newTitle
    }

    fun onSaveTask() {
        if (newTaskTitle.isBlank()) return
        viewModelScope.launch {
            try {
                addTaskUseCase(newTaskTitle)
                currentScreen = ScreenType.LIST
                newTaskTitle = ""
            } catch (e: IllegalArgumentException) {
                _uiState.value = AcademicTaskUiState.Error(e.message ?: "Dato inválido")
                currentScreen = ScreenType.LIST
            } catch (e: Exception) {
                _uiState.value = AcademicTaskUiState.Error("Error al guardar: ${e.localizedMessage}")
                currentScreen = ScreenType.LIST
            }
        }
    }
}
