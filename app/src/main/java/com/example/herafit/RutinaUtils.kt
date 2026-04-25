package com.example.herafit
fun adaptarRutina(
    rutina: Rutina,
    perfil: PerfilUsuario
): Rutina {

    val nivel = when {
        perfil.edad < 30 && perfil.peso < 70 -> "Avanzado"
        perfil.edad in 30..45 -> "Intermedio"
        else -> "Principiante"
    }

    val descripcionAdaptada = when (nivel) {
        "Avanzado" -> "${rutina.descripcion}\n• 4 series\n• Ritmo alto"
        "Intermedio" -> "${rutina.descripcion}\n• 3 series\n• Ritmo medio"
        else -> "${rutina.descripcion}\n• 2 series\n• Ritmo suave"
    }

    return rutina.copy(
        nivel = nivel,
        descripcion = descripcionAdaptada
    )

}
