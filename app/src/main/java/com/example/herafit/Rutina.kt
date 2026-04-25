package com.example.herafit
data class Rutina(
    var id: String = "",
    var nombre: String = "",
    var descripcion: String = "",
    var nivel: String = "",          // principiante / intermedio / avanzado
    var duracionMin: Int = 0
)
