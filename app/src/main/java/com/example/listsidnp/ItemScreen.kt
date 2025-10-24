package com.example.listsidnp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ItemScreen() {
    var id by remember { mutableStateOf("") }
    var texto by remember { mutableStateOf("") }

    // Lista observable mutable - MÁS ÓPTIMO
    val items = remember {
        mutableStateListOf<Item>().apply {
            for (i in 1..100) {
                add(Item(i.toString(), "Nombre $i | Descripcion $i"))
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                OutlinedTextField(
                    value = id,
                    onValueChange = { id = it },
                    label = { Text("ID") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = texto,
                    onValueChange = { texto = it },
                    label = { Text("Texto") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = {
                            if (id.isNotBlank() && texto.isNotBlank()) {
                                // Busca el índice del item
                                val index = items.indexOfFirst { it.id == id }
                                if (index != -1) {
                                    // Modifica directamente en la lista
                                    items[index] = Item(id, texto)
                                } else {
                                    // Agrega nuevo item
                                    items.add(Item(id, texto))
                                }
                                id = ""
                                texto = ""
                            }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Modificar")
                    }

                    OutlinedButton(
                        onClick = {
                            items.forEach { item ->
                                println("Item: ${item.id} | ${item.texto}")
                            }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Ver Lista")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items) { item ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "ID: ${item.id}",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = item.texto,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }
        }
    }
}
