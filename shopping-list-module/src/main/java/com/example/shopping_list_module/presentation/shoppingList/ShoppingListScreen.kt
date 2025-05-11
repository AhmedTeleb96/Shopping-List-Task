package com.example.shopping_list_module.presentation.shoppingList

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ShoppingListScreen(viewModel: ShoppingListViewModel = hiltViewModel()) {
    val filteredItems = viewModel.getFilteredSortedItems().collectAsState()

    var name by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
        OutlinedTextField(
            value = quantity,
            onValueChange = { quantity = it },
            label = { Text("Quantity") })
        OutlinedTextField(value = note, onValueChange = { note = it }, label = { Text("Note") })
        Button(onClick = {
            viewModel.addItem(name, quantity, note)
            name = ""; quantity = ""; note = ""
        }, modifier = Modifier.padding(vertical = 8.dp)) {
            Text("Add Item")
        }

        OutlinedTextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                viewModel.setSearchQuery(it)
            },
            label = { Text("Search") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = { viewModel.setFilterBought(false) }) {
                Text("Hide Bought")
            }
            Button(onClick = { viewModel.setFilterBought(true) }) {
                Text("Show All")
            }
            Button(onClick = { viewModel.toggleSortOrder() }) {
                Text("Toggle Sort")
            }
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(
                count = filteredItems.value.size,
                key = { index -> filteredItems.value[index].id }
            ) { index ->
                val item = filteredItems.value[index]
                ShoppingListItem(
                    item = item,
                    onToggleBought = { viewModel.toggleItemBought(item) },
                    onDelete = { viewModel.deleteItem(item.id) },
                )
            }
        }
    }
}

