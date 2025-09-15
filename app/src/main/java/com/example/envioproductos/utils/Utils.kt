package com.example.envioproductos.utils

// Importaciones necesarias para cálculos matemáticos y formateo de números
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.math.PI
import kotlin.math.sqrt

/**
 * Objeto utilitario con funciones estáticas para:
 *  - Conversión entre grados y radianes
 *  - Formateo de números
 *  - Cálculo de distancia entre coordenadas GPS (Haversine)
 *  - Cálculo del costo de entrega en base a distancia y precio del producto
 */
object Utils {

    /**
     * Convierte grados a radianes.
     */
    fun gradosARadianes(grados: Double): Double {
        return grados * PI / 180.0
    }

    /**
     * Convierte radianes a grados.
     */
    fun radianesAGrados(radianes: Double): Double {
        return radianes * 180.0 / PI
    }

    /**
     * Formatea un número con separadores de miles y decimales personalizados
     */
    fun formatNumber(value: Number, decimals: Int): String {
        // Configura los símbolos de formato: '.' para miles y ',' para decimales
        val symbols = DecimalFormatSymbols(Locale.getDefault()).apply {
            groupingSeparator = '.'
            decimalSeparator = ','
        }

        // Genera el patrón dinámicamente según la cantidad de decimales
        val pattern = buildString {
            append("#,##0") // Parte entera
            if (decimals > 0) {
                append(".") // Separador decimal (temporal)
                repeat(decimals) { append("0") } // Agrega ceros según decimales
            }
        }

        val formatter = DecimalFormat(pattern, symbols)
        return formatter.format(value.toDouble())
    }

    /**
     * Calcula la distancia entre dos coordenadas GPS usando la fórmula Haversine
     * lat1 Latitud del primer punto
     * lon1 Longitud del primer punto
     * lat2 Latitud del segundo punto
     * lon2 Longitud del segundo punto
     */
    fun haversine(
        lat1: Double, lon1: Double,
        lat2: Double, lon2: Double
    ): Double {
        val R = 6371000.0 // Radio de la Tierra en metros
        val dLat = gradosARadianes(lat2 - lat1)
        val dLon = gradosARadianes(lon2 - lon1)

        // Fórmula Haversine
        val a = sin(dLat / 2).pow(2.0) +
                cos(gradosARadianes(lat1)) *
                cos(gradosARadianes(lat2)) *
                sin(dLon / 2).pow(2.0)

        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return (R * c) / 1000 // Convertimos de metros a kilómetros
    }

    /**
     * Calcula el costo de entrega en base a la distancia y precio del producto
     */
    fun calculateDeliveryCost(
        distance: Double, productCostAmount: Int
    ): Int {
        var costByKm: Int = 150 // Costo base por km

        // Aplicar reglas de costos según precio del producto y distancia
        if (productCostAmount >= 50000 && distance.toInt() < 20) {
            costByKm = 0 // Envío gratis para productos caros y distancia corta
        }
        if (25000 <= productCostAmount && productCostAmount <= 49999) {
            costByKm = 150 // Costo estándar para productos de precio medio
        }
        if (productCostAmount <= 24999) {
            costByKm = 300 // Costo mayor para productos baratos
        }

        // Calcula el costo total multiplicando por la distancia y redondea
        return (costByKm * distance).roundToInt()
    }
}
