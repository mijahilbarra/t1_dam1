package com.example.myapplication.model

data class Book(
    val id: String = java.util.UUID.randomUUID().toString(),
    val tituloDelLibro: String,
    val precioUnitario: Double,
    val cantidad: Int,
    val categoria: Categoria
)

enum class Categoria {
    CIENCIA_FICCION,
    NOVELAS_CORTAS,
    AUTOAYUDA;
    
    fun toDisplayString(): String {
        return when (this) {
            CIENCIA_FICCION -> "Ciencia Ficción"
            NOVELAS_CORTAS -> "Novelas Cortas"
            AUTOAYUDA -> "Autoayuda"
        }
    }
}
