package com.example.myapplication.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.model.Book

@Composable
fun TablaDeLibros(
    libros: List<Book>,
    onEliminarLibro: (String) -> Unit,
    onShowSnackbar: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (libros.isEmpty()) {
            Text(
                text = "No hay libros en el carrito",
                modifier = Modifier.padding(16.dp)
            )
        } else {
            libros.forEach { libro ->
                LibroCard(
                    libro = libro,
                    onEliminar = { onEliminarLibro(libro.id) },
                    onShowSnackbar = onShowSnackbar
                )
            }
        }
    }
}

@Composable
fun LibroCard(
    libro: Book,
    onEliminar: () -> Unit,
    onShowSnackbar: (String) -> Unit
) {
    var showConfirmDialog by remember { mutableStateOf(false) }
    val subtotal = libro.precioUnitario * libro.cantidad
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = androidx.compose.ui.graphics.Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = libro.tituloDelLibro,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Precio: S/. ${String.format("%.2f", libro.precioUnitario)} | Cantidad: ${libro.cantidad}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Subtotal: S/. ${String.format("%.2f", subtotal)}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            
            IconButton(onClick = { showConfirmDialog = true }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar"
                )
            }
        }
    }
    
    // Confirmation dialog
    if (showConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmDialog = false },
            title = { Text("Confirmar eliminación") },
            text = { Text("¿Eliminar '${libro.tituloDelLibro}' del carrito?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onEliminar()
                        showConfirmDialog = false
                        onShowSnackbar("Libro eliminado del carrito")
                    }
                ) {
                    Text("Sí")
                }
            },
            dismissButton = {
                TextButton(onClick = { showConfirmDialog = false }) {
                    Text("No")
                }
            }
        )
    }
}
