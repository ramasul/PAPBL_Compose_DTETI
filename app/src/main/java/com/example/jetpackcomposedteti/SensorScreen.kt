package com.example.jetpackcomposedteti

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale
import kotlin.math.sqrt

@Composable
fun SensorScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val sensorManager = remember { context.getSystemService(Context.SENSOR_SERVICE) as SensorManager }
    var sensorValues by remember { mutableStateOf(FloatArray(3)) }
    var isSensorAvailable by remember { mutableStateOf(false) }

    DisposableEffect(Unit) {
        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        val sensorListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                    sensorValues = event.values.clone()
                }
            }
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                // Not needed for this example
            }
        }

        isSensorAvailable = accelerometer != null
        if (isSensorAvailable) {
            sensorManager.registerListener(
                sensorListener,
                accelerometer,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }

        onDispose { // Will be called when the effect leaves the Composition or when the value of the key changes
            sensorManager.unregisterListener(sensorListener)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Your sensor is reading...",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        if (isSensorAvailable) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Accelerometer Data",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Text(
                        text = "X: ${String.format(Locale.US,"%.2f", sensorValues[0])} m/s²",
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                    Text(
                        text = "Y: ${String.format(Locale.US, "%.2f", sensorValues[1])} m/s²",
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                    Text(
                        text = "Z: ${String.format(Locale.US, "%.2f", sensorValues[2])} m/s²",
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                    Text(
                        text = "Total: ${String.format(Locale.US, "%.2f", sqrt(sensorValues[0] * sensorValues[0] + sensorValues[1] * sensorValues[1] + sensorValues[2] * sensorValues[2]))} m/s²",
                        modifier = Modifier.padding(top = 8.dp),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        } else {
            Text(
                text = "Accelerometer sensor not available",
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Preview
@Composable
fun SensorScreenPreview() {
    SensorScreen()
}