package com.example.jetpackcomposedteti

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposedteti.ui.Counter
import com.example.jetpackcomposedteti.ui.CounterWithoutRemember
import com.example.jetpackcomposedteti.ui.GeocoderDemo
import com.example.jetpackcomposedteti.ui.Greeting
import com.example.jetpackcomposedteti.ui.GreetingModified
import com.example.jetpackcomposedteti.ui.MapView
import com.example.jetpackcomposedteti.ui.PlacePickerScreen
import com.example.jetpackcomposedteti.ui.ReverseGeocoderScreen
import com.example.jetpackcomposedteti.ui.theme.JetpackComposeDTETITheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetpackComposeDTETITheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(Modifier.padding(innerPadding)) {
//                        Greeting("Mahasiswa PAPBL DTETI")
                        //Uncomment untuk demo remember
//                        CounterWithoutRemember()
//                        Counter()
                        //Uncomment untuk demo modifier
//                        GreetingModified("DTETI")
                        //Demo Map
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ){
                            MapView(
                                modifier = Modifier.size(300.dp)
                            )
                        }
                        //Demo Geocoder
//                        GeocoderDemo()
//                        ReverseGeocoderScreen()
                        //Place Picker
//                        PlacePickerScreen()
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Greeting("Android")
}