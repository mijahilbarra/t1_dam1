package com.example.myapplication.model

data class CarritoDeCompras(
    val libros: List<Book> = emptyList(),
    val calculado: Boolean = false
) {
    val cantidadTotalLibros: Int
        get() = libros.sumOf { it.cantidad }
    
    val subtotal: Double
        get() = libros.sumOf { it.precioUnitario * it.cantidad }
    
    val descuentoPorcentaje: Double
        get() = when (cantidadTotalLibros) {
            in 1..2 -> 0.0
            in 3..5 -> 10.0
            in 6..10 -> 15.0
            else -> if (cantidadTotalLibros >= 11) 20.0 else 0.0
        }
    
    val descuento: Double
        get() = subtotal * (descuentoPorcentaje / 100)
    
    val total: Double
        get() = subtotal - descuento
}
