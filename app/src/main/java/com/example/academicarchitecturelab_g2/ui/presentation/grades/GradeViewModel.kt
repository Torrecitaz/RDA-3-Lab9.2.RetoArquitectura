package com.example.academicarchitecturelab_g2.ui.presentation.grades

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.academicarchitecturelab_g2.domain.usecase.AddGradeUseCase
import com.example.academicarchitecturelab_g2.domain.usecase.GetGradesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GradeViewModel(
    private val getGradesUseCase: GetGradesUseCase,
    private val addGradeUseCase: AddGradeUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<GradeUiState>(GradeUiState.Loading)
    val uiState: StateFlow<GradeUiState> = _uiState.asStateFlow()

    var currentScreen by mutableStateOf(GradeScreenType.LIST)
        private set

    var activityName by mutableStateOf("")
        private set

    var subject by mutableStateOf("")
        private set

    var gradeInput by mutableStateOf("")
        private set

    init {
        observeGrades()
    }

    private fun observeGrades() {
        viewModelScope.launch {
            try {
                getGradesUseCase().collect { gradeList ->
                    val average = if (gradeList.isEmpty()) {
                        0.0
                    } else {
                        gradeList.map { it.grade }.average()
                    }
                    Log.d("GradeTracker", "Renderizacion Reactiva: Promedio general calculado con exito: $average")
                    _uiState.value = GradeUiState.Success(gradeList, average)
                }
            } catch (e: Exception) {
                Log.e("GradeTracker", "Error al cargar calificaciones", e)
                _uiState.value = GradeUiState.Error("Error al cargar calificaciones: ${e.localizedMessage}")
            }
        }
    }

    fun onActivityNameChange(newValue: String) {
        activityName = newValue
    }

    fun onSubjectChange(newValue: String) {
        subject = newValue
    }

    fun onGradeInputChange(newValue: String) {
        gradeInput = newValue
    }

    fun onNavigateToForm() {
        activityName = ""
        subject = ""
        gradeInput = ""
        currentScreen = GradeScreenType.FORM
    }

    fun onNavigateToList() {
        currentScreen = GradeScreenType.LIST
    }

    fun dismissErrorAndReturnToForm() {
        observeGrades()
        currentScreen = GradeScreenType.FORM
    }

    fun dismissErrorAndReturnToList() {
        observeGrades()
        currentScreen = GradeScreenType.LIST
    }

    fun onSaveGrade() {
        val parsedGrade = gradeInput.toDoubleOrNull() ?: run {
            Log.w("GradeTracker", "Control Preventivo (Error Logico) - La nota ingresada '$gradeInput' no es un numero decimal valido.")
            _uiState.value = GradeUiState.Error("La nota ingresada no es un número decimal válido.")
            currentScreen = GradeScreenType.LIST
            return
        }

        viewModelScope.launch {
            try {
                addGradeUseCase(
                    activityName = activityName,
                    subject = subject,
                    grade = parsedGrade
                )
                activityName = ""
                subject = ""
                gradeInput = ""
                currentScreen = GradeScreenType.LIST
            } catch (e: IllegalArgumentException) {
                Log.e("GradeTracker", "Control Preventivo (Error Logico) - Regla de negocio violada: ${e.message}")
                _uiState.value = GradeUiState.Error(e.message ?: "Error de validación.")
                currentScreen = GradeScreenType.LIST
            } catch (e: Exception) {
                Log.e("GradeTracker", "Error inesperado al guardar calificacion", e)
                _uiState.value = GradeUiState.Error("Error al guardar: ${e.localizedMessage}")
                currentScreen = GradeScreenType.LIST
            }
        }
    }
}
