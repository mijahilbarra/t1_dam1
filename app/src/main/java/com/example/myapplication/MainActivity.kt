package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.launch
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.AgregarLibro
import com.example.myapplication.ui.TablaDeLibros
import com.example.myapplication.ui.ResumenCarrito
import com.example.myapplication.viewmodel.LibroViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: LibroViewModel = viewModel()) {
    val scrollState = rememberScrollState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val snackbarColor = remember { mutableStateOf<Color?>(null) }
    
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Libro Mundo - Carrito de Compras") },
                navigationIcon = {
                    IconButton(onClick = { /* Handle back navigation */ }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Atrás"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    titleContentColor = Color.Black
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // FAB action - will implement later
                },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White,
                shape = FloatingActionButtonDefaults.largeShape
            ) {
                Icon(
                    imageVector = Icons.Filled.ReceiptLong,
                    contentDescription = "Ver Resumen"
                )
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = snackbarColor.value ?: MaterialTheme.colorScheme.inverseSurface,
                    contentColor = Color.White
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AgregarLibro(
                onAgregarLibro = { libro ->
                    viewModel.agregarLibro(libro)
                },
                onShowSnackbar = { message ->
                    scope.launch {
                        snackbarColor.value = null
                        snackbarHostState.showSnackbar(message)
                    }
                }
            )
            
            TablaDeLibros(
                libros = viewModel.carrito.libros,
                onEliminarLibro = { bookId ->
                    viewModel.eliminarLibro(bookId)
                },
                onShowSnackbar = { message ->
                    scope.launch {
                        snackbarColor.value = null
                        snackbarHostState.showSnackbar(message)
                    }
                }
            )
            
            ResumenCarrito(
                carrito = viewModel.carrito,
                onLimpiar = {
                    viewModel.limpiarCarrito()
                },
                onCalcular = {
                    viewModel.calcularTotal()
                },
                onShowSnackbar = { message, color ->
                    scope.launch {
                        snackbarColor.value = color
                        snackbarHostState.showSnackbar(message)
                    }
                }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MyApplicationTheme {
        MainScreen()
    }
}