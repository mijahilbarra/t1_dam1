package com.example.myapplication.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.model.Book
import com.example.myapplication.model.Categoria

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgregarLibro(
    onAgregarLibro: (Book) -> Unit,
    onShowSnackbar: (String) -> Unit
) {
    var tituloDelLibro by remember { mutableStateOf("") }
    var precioUnitario by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }
    var categoriaExpandida by remember { mutableStateOf(false) }
    var categoriaSeleccionada by remember { mutableStateOf<Categoria?>(null) }
    var showAlertDialog by remember { mutableStateOf(false) }
    var alertDialogMessage by remember { mutableStateOf("") }
    
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
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Agregar Libro",
                style = MaterialTheme.typography.titleMedium
            )
            
            OutlinedTextField(
                value = tituloDelLibro,
                onValueChange = { tituloDelLibro = it },
                label = { Text("Título del Libro") },
                modifier = Modifier.fillMaxWidth()
            )
            
            OutlinedTextField(
                value = precioUnitario,
                onValueChange = { precioUnitario = it },
                label = { Text("Precio Unitario") },
                modifier = Modifier.fillMaxWidth()
            )
            
            OutlinedTextField(
                value = cantidad,
                onValueChange = { cantidad = it },
                label = { Text("Cantidad") },
                modifier = Modifier.fillMaxWidth()
            )
            
            ExposedDropdownMenuBox(
                expanded = categoriaExpandida,
                onExpandedChange = { categoriaExpandida = !categoriaExpandida }
            ) {
                OutlinedTextField(
                    value = categoriaSeleccionada?.toDisplayString() ?: "Seleccione una categoría",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Categoría") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoriaExpandida) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                )
                
                ExposedDropdownMenu(
                    expanded = categoriaExpandida,
                    onDismissRequest = { categoriaExpandida = false }
                ) {
                    Categoria.values().forEach { categoria ->
                        DropdownMenuItem(
                            text = { Text(categoria.toDisplayString()) },
                            onClick = {
                                categoriaSeleccionada = categoria
                                categoriaExpandida = false
                            }
                        )
                    }
                }
            }
            
            Button(
                onClick = {
                    // Validaciones
                    when {
                        tituloDelLibro.isBlank() -> {
                            alertDialogMessage = "Debe ingresar el título del libro"
                            showAlertDialog = true
                        }
                        precioUnitario.toDoubleOrNull() == null || precioUnitario.toDouble() <= 0 -> {
                            alertDialogMessage = "El precio debe ser mayor a 0"
                            showAlertDialog = true
                        }
                        cantidad.toIntOrNull() == null || cantidad.toInt() <= 0 -> {
                            alertDialogMessage = "La cantidad debe ser mayor a 0"
                            showAlertDialog = true
                        }
                        categoriaSeleccionada == null -> {
                            alertDialogMessage = "Debe seleccionar una categoría"
                            showAlertDialog = true
                        }
                        else -> {
                            val categoria = categoriaSeleccionada ?: return@Button
                            val book = Book(
                                tituloDelLibro = tituloDelLibro,
                                precioUnitario = precioUnitario.toDouble(),
                                cantidad = cantidad.toInt(),
                                categoria = categoria
                            )
                            onAgregarLibro(book)
                            
                            // Mostrar Snackbar de éxito
                            onShowSnackbar("Libro agregado al carrito")
                            
                            // Limpiar formulario (mantener categoría)
                            tituloDelLibro = ""
                            precioUnitario = ""
                            cantidad = ""
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary
                )
            ) {
                Text("Agregar al Carrito")
            }
        }
    }
    
    // AlertDialog para validaciones
    if (showAlertDialog) {
        AlertDialog(
            onDismissRequest = { showAlertDialog = false },
            title = { Text("Error de validación") },
            text = { Text(alertDialogMessage) },
            confirmButton = {
                TextButton(onClick = { showAlertDialog = false }) {
                    Text("Aceptar")
                }
            }
        )
    }
}
