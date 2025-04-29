package com.example.jetpackcomposedteti.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Composable
fun CounterWithoutRemember(modifier: Modifier = Modifier) {
    var count = 0
    Button(onClick = { count++ }) {
        Text("I don't remember being clicked $count times")
    }
}

@Composable
fun Counter() { //Harus di run di main activity untuk menampilkan hasil
    var count by remember { mutableStateOf(0) }

    Button(onClick = { count++ }) {
        Text("I remember being clicked $count times")
    }
}

@Composable
fun GreetingModified(name: String) {
    Column(
        modifier = Modifier
            .padding(32.dp)
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(24.dp)
            )
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(14.dp)
                    .height(72.dp)
                    .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(7.dp))
            )
            Spacer(modifier = Modifier.width(28.dp))
            Column {
                Text(
                    text = "Hello,",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 26.sp,
                    modifier = Modifier.padding(bottom = 6.dp)
                )
                Text(
                    text = name,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 34.sp,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 6.dp)
                )
                Text(
                    text = "Welcome to Jetpack Compose!",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(top = 6.dp)
                )
            }
        }
    }
}

//Preview Section
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Greeting("DTETI FT UGM!")
}

@Preview(showBackground = true)
@Composable
fun CounterWithoutRememberPreview() {
    CounterWithoutRemember()
}

@Preview(showBackground = true)
@Composable
fun GreetingWithModifierPreview() {
    androidx.compose.material3.MaterialTheme {
        GreetingModified("DTETI")
    }
}