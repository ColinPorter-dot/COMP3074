package ca.gbc.comp3074.porter_colin.lab5

import android.location.Location
// import android.location.LocationRequest
import android.os.Bundle
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import ca.gbc.comp3074.porter_colin.lab5.ui.theme.Lab5Theme
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.location.LocationRequest

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab5Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LocationComponent(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun LocationComponent(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val fusedClient = remember(context) {
        LocationServices.getFusedLocationProviderClient(context)
    }
    val scope = rememberCoroutineScope()
    val location = remember {
        mutableStateOf<Location?>(null)
    }
    val status = remember { mutableStateOf("Tap button to get location") }
    val locationCallBack = remember { mutableStateOf<LocationCallback?>(null)}

    fun hasPermission(): Boolean{
        val fine = ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == android.content.pm.PackageManager.PERMISSION_GRANTED
        val coarse = ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == android.content.pm.PackageManager.PERMISSION_GRANTED
        return fine || coarse
    }

    fun fetchLocation(){
        if (!hasPermission()){
            status.value = "No permission"
            return
        }
        status.value = "Getting location..."
        val tokenSource = CancellationTokenSource()
        fusedClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            tokenSource.token
        ).addOnSuccessListener { loc ->
            status.value = "Got location: %.5f, %.5f".format(
                loc.latitude, loc.longitude
            )
            location.value = loc
        }.addOnFailureListener { error ->
            status.value = "Error: ${error.localizedMessage ?: "Unknown error"}"
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {
        result ->
        if (result.any { it.value }) {
            fetchLocation()
        } else {
            status.value = "No permission"
        }
    }

    fun startTracking(){
        if (!hasPermission()){
            status.value = "No permission"
            return
        }
        status.value = "Tracking location..."
        val locationCallBack = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult){
                result.lastLocation?.let { loc ->
                    status.value = "Got location: %.5f, %.5f".format(loc.latitude, loc.longitude)
                    location.value = loc
                }
            }
        }
        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            5000L
        ).setMinUpdateIntervalMillis(2000L).build()
        fusedClient.requestLocationUpdates(
            locationRequest, locationCallBack, Looper.getMainLooper()
        )
    }

    fun stopTracking(){
        locationCallBack.value?.let {
            fusedClient.removeLocationUpdates { it }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "status: ${status.value}",
            modifier = modifier.fillMaxWidth()
        )
        Button(onClick = {
            if (hasPermission()){
                fetchLocation()
            } else {
                permissionLauncher.launch(
                    arrayOf(
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }
        }) {
            Text(text = "Get Location")
        }
        Button(onClick = {
            if (hasPermission()){
                fetchLocation()
            } else {
                permissionLauncher.launch(
                    arrayOf(
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )}
        }) {
            Text(text = "Start Tracking")
        }
        Button(onClick = {
            stopTracking()
        }) {
            Text(text = "Stop Tracking")
        }
    }


}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Lab5Theme {
        LocationComponent()
    }
}