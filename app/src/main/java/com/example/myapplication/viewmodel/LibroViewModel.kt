package com.example.myapplication.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.Book
import com.example.myapplication.model.CarritoDeCompras

class LibroViewModel : ViewModel() {
    var carrito by mutableStateOf(CarritoDeCompras())
        private set
    
    fun agregarLibro(book: Book) {
        carrito = carrito.copy(
            libros = carrito.libros + book,
            calculado = false
        )
    }
    
    fun eliminarLibro(bookId: String) {
        val nuevosLibros = carrito.libros.filter { it.id != bookId }
        carrito = carrito.copy(
            libros = nuevosLibros,
            calculado = if (nuevosLibros.isEmpty()) false else carrito.calculado
        )
        // Recalcular automáticamente si ya estaba calculado
        if (carrito.calculado && nuevosLibros.isNotEmpty()) {
            calcularTotal()
        }
    }
    
    fun limpiarCarrito() {
        carrito = CarritoDeCompras()
    }
    
    fun calcularTotal() {
        carrito = carrito.copy(calculado = true)
    }
}
