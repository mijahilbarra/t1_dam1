package com.example.myapplication.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myapplication.model.CarritoDeCompras

@Composable
fun ResumenCarrito(
    carrito: CarritoDeCompras,
    onLimpiar: () -> Unit,
    onCalcular: () -> Unit,
    onShowSnackbar: (String, Color?) -> Unit
) {
    var showConfirmDialog by remember { mutableStateOf(false) }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = androidx.compose.ui.graphics.Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (carrito.calculado) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Total de Libros:")
                    Text("${carrito.cantidadTotalLibros}")
                }
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Subtotal:",
                        color = Color.Gray
                    )
                    Text(
                        text = "S/. ${String.format("%.2f", carrito.subtotal)}",
                        color = Color.Gray
                    )
                }
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Descuento (${carrito.descuentoPorcentaje.toInt()}%):",
                        color = Color.Gray
                    )
                    Text(
                        text = "S/. ${String.format("%.2f", carrito.descuento)}",
                        color = Color.Gray
                    )
                }
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Total:",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                        )
                    )
                    Text(
                        text = "S/. ${String.format("%.2f", carrito.total)}",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                        )
                    )
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Subtotal:",
                        color = Color.Gray
                    )
                    Text(
                        text = "S/. 0.00",
                        color = Color.Gray
                    )
                }
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Descuento (0%):",
                        color = Color.Gray
                    )
                    Text(
                        text = "S/. 0.00",
                        color = Color.Gray
                    )
                }
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Total:",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                        )
                    )
                    Text(
                        text = "S/. 0.00",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                        )
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { showConfirmDialog = true },
                    modifier = Modifier.weight(1f),
                    enabled = carrito.libros.isNotEmpty(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary
                    )
                ) {
                    Text("Limpiar")
                }
                
                Button(
                    onClick = {
                        if (carrito.libros.isEmpty()) {
                            onShowSnackbar("Debe haber al menos 1 libro en el carrito", null)
                        } else {
                            onCalcular()
                            
                            // Mostrar Snackbar según descuento
                            val mensaje = when (carrito.descuentoPorcentaje) {
                                0.0 -> "No hay descuento aplicado"
                                10.0 -> "¡Genial! Ahorraste S/. ${String.format("%.2f", carrito.descuento)}"
                                15.0 -> "¡Excelente! Ahorraste S/. ${String.format("%.2f", carrito.descuento)}"
                                20.0 -> "¡Increíble! Ahorraste S/. ${String.format("%.2f", carrito.descuento)}"
                                else -> "Cálculo completado"
                            }
                            
                            val color = when (carrito.descuentoPorcentaje) {
                                0.0 -> Color.Gray
                                10.0 -> Color(0xFF4CAF50) // Verde
                                15.0 -> Color(0xFF2196F3) // Azul
                                20.0 -> Color(0xFFFFD700) // Dorado
                                else -> null
                            }
                            
                            onShowSnackbar(mensaje, color)
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Calcular Total")
                }
            }
        }
    }
    
    // Confirmation dialog for clearing cart
    if (showConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmDialog = false },
            title = { Text("Confirmar acción") },
            text = { Text("¿Está seguro de limpiar el carrito?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onLimpiar()
                        showConfirmDialog = false
                        onShowSnackbar("Carrito limpiado", null)
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