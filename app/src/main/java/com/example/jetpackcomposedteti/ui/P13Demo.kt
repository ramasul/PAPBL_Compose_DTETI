package com.example.jetpackcomposedteti.ui

import android.location.Address
import android.location.Geocoder
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.jetpackcomposedteti.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.maplibre.android.MapLibre
import org.maplibre.android.geometry.LatLng
import org.maplibre.android.camera.CameraPosition.Builder
import org.maplibre.android.camera.CameraUpdateFactory
import java.util.Locale

@Composable
fun MapView(
    modifier: Modifier = Modifier
) {
    val mapTilerApiKey = BuildConfig.MAP_TILER_API
    AndroidView(
        modifier = modifier,
        factory = { context ->
            MapLibre.getInstance(context)
            val mapView = org.maplibre.android.maps.MapView(context)
            val styleUrl = "https://api.maptiler.com/maps/basic-v2/style.json?key=${mapTilerApiKey}";
            mapView.onCreate(null)
            mapView.getMapAsync { map ->
                // Set the style after mapView was loaded
                map.setStyle(styleUrl) {
                    map.uiSettings.setAttributionMargins(15, 0, 0, 15)
                    // Set the map view center
                    map.cameraPosition = Builder()
                        .target(LatLng(-7.770717, 110.377724))
                        .zoom(15.0)
                        .bearing(2.0)
                        .build()
                }
            }
            mapView
        }
    )
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun GeocoderDemo(
    modifier: Modifier = Modifier
){
    val context = LocalContext.current
    var locationName by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text("Geocoder Demo", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = locationName,
            onValueChange = { locationName = it },
            label = { Text("Enter location name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = {
            isLoading = true
            result = ""

            val geocoder = Geocoder(context, Locale.getDefault())

            // Non Deprecated Version
            geocoder.getFromLocationName(
                locationName,
                1,
                -90.0, -180.0, 90.0, 180.0,
                object : Geocoder.GeocodeListener {
                    override fun onGeocode(addresses: MutableList<Address>) {
                        isLoading = false
                        result = if (addresses.isNotEmpty()) {
                            val address = addresses[0]
                            "Latitude: ${address.latitude}, Longitude: ${address.longitude}"
                        } else {
                            "No results found."
                        }
                    }

                    override fun onError(errorMessage: String?) {
                        isLoading = false
                        result = "Error: ${errorMessage ?: "Unknown error"}"
                    }
                }
            )
            //Deprecated Version
//            val addresses = geocoder.getFromLocationName(locationName, 1)
//            isLoading = false
//            result = if (addresses != null && addresses.isNotEmpty()) {
//                val address = addresses[0]
//                "Latitude: ${address.latitude}, Longitude: ${address.longitude}"
//            } else {
//                "Location not found."
//            }
        }) {
            Text("Find Coordinates")
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (isLoading) {
            CircularProgressIndicator()
        } else {
            Text(result, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun ReverseGeocoderScreen() {
    val context = LocalContext.current

    var latitudeInput by remember { mutableStateOf("") }
    var longitudeInput by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text("Reverse Geocoder Demo", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = latitudeInput,
            onValueChange = { latitudeInput = it },
            label = { Text("Enter Latitude") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = longitudeInput,
            onValueChange = { longitudeInput = it },
            label = { Text("Enter Longitude") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = {
            val lat = latitudeInput.toDoubleOrNull()
            val lng = longitudeInput.toDoubleOrNull()

            if (lat != null && lng != null) {
                isLoading = true
                result = ""
                val geocoder = Geocoder(context, Locale.getDefault())
                geocoder.getFromLocation(
                    lat, lng, 1,
                    object : Geocoder.GeocodeListener {
                        override fun onGeocode(addresses: MutableList<Address>) {
                            isLoading = false
                            result = if (addresses.isNotEmpty()) {
                                val address = addresses[0]
                                buildString {
                                    appendLine("Full Address: ${address.getAddressLine(0)}")
                                    appendLine("Locality: ${address.locality}")
                                    appendLine("Country: ${address.countryName}")
                                }
                            } else {
                                "No address found."
                            }
                        }

                        override fun onError(errorMessage: String?) {
                            isLoading = false
                            result = "Error: ${errorMessage ?: "Unknown error"}"
                        }
                    }
                )
            } else {
                result = "Invalid latitude or longitude"
            }
        }) {
            Text("Get Address")
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (isLoading) {
            CircularProgressIndicator()
        } else {
            Text(result, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

//From those two, we can "Recreate" Place Picker
@Composable
fun MapLibrePickerMap(
    onLocationChanged: (LatLng) -> Unit
) {
    val context = LocalContext.current
    AndroidView(
        factory = {
            MapLibre.getInstance(context)
            val mapView = org.maplibre.android.maps.MapView(context)
            mapView.onCreate(null)
            mapView.getMapAsync { map ->
                map.setStyle(
                    "https://api.maptiler.com/maps/basic-v2/style.json?key=${BuildConfig.MAP_TILER_API}"
                ) {
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(-7.77, 110.37), 15.0))
                    map.addOnCameraIdleListener {
                        val center = map.cameraPosition.target
                        if (center != null) {
                            onLocationChanged(center)
                        }
                    }
                }
            }
            mapView
        },
        modifier = Modifier.fillMaxSize()
    )
}


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun PlacePickerScreen() {
    val context = LocalContext.current

    var pickedLatLng by remember { mutableStateOf<LatLng?>(null) }
    var pickedAddress by remember { mutableStateOf("Move map to pick a place") }
    var result by remember { mutableStateOf("") }

    Box(Modifier.fillMaxSize()) {
        MapLibrePickerMap(
            onLocationChanged = { latLng ->
                pickedLatLng = latLng
            }
        )

        // Center pin icon
        Icon(
            Icons.Default.Place,
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .align(Alignment.Center),
            tint = Color.Red
        )

        Column(
            Modifier
                .align(Alignment.BottomCenter)
                .background(Color.White.copy(alpha = 0.8f))
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Picked Location: ${pickedLatLng?.latitude ?: "-"}, ${pickedLatLng?.longitude ?: "-"}")
            Text(text = pickedAddress)
        }
    }

    // When pickedLatLng changes, launch a coroutine to reverse geocode
    LaunchedEffect(pickedLatLng) {
        pickedLatLng?.let { latLng ->
            val geocoder = Geocoder(context, Locale.getDefault())
            geocoder.getFromLocation(
                latLng.latitude, latLng.longitude, 1,
                object : Geocoder.GeocodeListener {
                    override fun onGeocode(addresses: MutableList<Address>) {
                        result = if (addresses.isNotEmpty()) {
                            val address = addresses[0]
                            buildString {
                                appendLine("Full Address: ${address.getAddressLine(0)}")
                                appendLine("Locality: ${address.locality}")
                                appendLine("Country: ${address.countryName}")
                            }
                        } else {
                            "No address found."
                        }
                    }

                    override fun onError(errorMessage: String?) {
                        result = "Error: ${errorMessage ?: "Unknown error"}"
                    }
                }
            )
            pickedAddress = result
        }
    }
}

