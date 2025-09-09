package com.example.envioproductos.utils

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

object Utils {


    fun gradosARadianes(grados: Double): Double {
        return grados * PI / 180.0
    }

    fun radianesAGrados(radianes: Double): Double {
        return radianes * 180.0 / PI
    }


    fun formatNumber(value: Number, decimals: Int): String {
        val symbols = DecimalFormatSymbols(Locale.getDefault()).apply {
            groupingSeparator = '.'
            decimalSeparator = ','
        }

        // 👇 el punto (.) en el pattern es el separador decimal, después lo cambiamos con symbols
        val pattern = buildString {
            append("#,##0")
            if (decimals > 0) {
                append(".")
                repeat(decimals) { append("0") }
            }
        }

        val formatter = DecimalFormat(pattern, symbols)
        return formatter.format(value.toDouble())
    }

    fun haversine(
        lat1: Double, lon1: Double,
        lat2: Double, lon2: Double
    ): Double {
        val R = 6371000.0 // Radio de la Tierra en metros
        val dLat = gradosARadianes(lat2 - lat1)
        val dLon = gradosARadianes(lon2 - lon1)

        val a = sin(dLat / 2).pow(2.0) +
                cos(gradosARadianes(lat1)) *
                cos(gradosARadianes(lat2)) *
                sin(dLon / 2).pow(2.0)

        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return (R * c)/1000 // resultado en kilometros
    }


    fun calculateDeliveryCost(
        distance: Double, productCostAmount: Int
    ): Int {
        var costByKm: Int = 150;
        if (productCostAmount >= 50000 && distance.toInt() < 20) {
            costByKm = 0
        }
        if (25000 <= productCostAmount && productCostAmount <= 49999) {
            costByKm = 150
        }
        if (productCostAmount <= 24999) {
            costByKm = 300
        }

        return (costByKm*distance).roundToInt()
    }
}