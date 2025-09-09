package com.example.envioproductos.ui.delivery_process

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

class SimulateTemperature(
    private val intervalMs: Long = 1500L,
    private val scenario: Scenario = Scenario.STABLE_COLD,
    private val seed: Int = 42
) {

    enum class Scenario { STABLE_COLD, DRIFT_WARMING, SPIKES }

    private val rng = Random(seed)

    fun temperatures(): Flow<Double> = flow {
        var base = -20.0 // punto de partida

        while (true) {
            val value = when (scenario) {
                Scenario.STABLE_COLD ->
                    base + rng.nextDouble(-0.5, 0.5)
                Scenario.DRIFT_WARMING -> {
                    base += 0.3                      // se va calentando
                    base + rng.nextDouble(-0.6, 0.6)
                }
                Scenario.SPIKES -> {
                    val spike = if (rng.nextDouble() < 0.15) 6.0 else 0.0
                    base + rng.nextDouble(-0.7, 0.7) + spike
                }
            }
            emit(value)
            delay(intervalMs)
        }
    }
}
