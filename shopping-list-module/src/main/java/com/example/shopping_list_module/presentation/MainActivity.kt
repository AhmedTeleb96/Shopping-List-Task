package com.example.shopping_list_module.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.shopping_list_module.presentation.shoppingList.ShoppingListScreen
import com.example.shopping_list_module.presentation.ui.theme.ShoppingListTaskTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShoppingListTaskTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .padding(it)
                    ) {
                        ShoppingListScreen()
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ShoppingListTaskTheme {
        ShoppingListScreen()
    }
}