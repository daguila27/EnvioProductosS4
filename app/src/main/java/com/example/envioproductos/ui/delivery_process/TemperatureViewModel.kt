package com.example.envioproductos.ui.delivery_process

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class TempState(
    val temperature: Double = Double.NaN,
    val isAlarm: Boolean = false
)

class TemperatureViewModel(
    private val provider: SimulateTemperature,
    private val limitCelsius: Double = -18.0
) : ViewModel() {

    private val _state = MutableStateFlow(TempState())
    val state: StateFlow<TempState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            provider.temperatures()
                .map { t -> TempState(temperature = t, isAlarm = t > limitCelsius) }
                .collect { _state.value = it }
        }
    }
}