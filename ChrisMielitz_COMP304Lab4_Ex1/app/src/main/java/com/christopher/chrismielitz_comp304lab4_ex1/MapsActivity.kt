package com.christopher.chrismielitz_comp304lab4_ex1

import android.Manifest
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.christopher.chrismielitz_comp304lab4_ex1.ui.theme.AddLocation
import com.christopher.chrismielitz_comp304lab4_ex1.ui.theme.ChrisMielitz_COMP304Lab4_Ex1Theme
import com.christopher.chrismielitz_comp304lab4_ex1.ui.theme.MapsWorker
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch
import com.google.maps.android.*


var longitude = 0.1
var latitude = 0.1

//Loc is short for location

//Currentlocation starts in Newmarket because that is where I was born
var currentLocation = LatLng(44.0592, -79.4613)
class MapsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        var thisClient: FusedLocationProviderClient
        thisClient = LocationServices.getFusedLocationProviderClient(this)
        var locationCallback = object : LocationCallback() {
            //This function gets the current location
            override fun onLocationResult(loc: LocationResult) {
                loc.locations.forEach { location ->
                    longitude = location.longitude
                    latitude = location.latitude
                    currentLocation = LatLng(latitude, longitude)
                }
            }
        }

        super.onCreate(savedInstanceState)
        setContent {
            ChrisMielitz_COMP304Lab4_Ex1Theme {

                Column {
                    GoogleMapS()
                }
            }
        }
    }
}



fun MakeGeoFence() {
    //This creates a geofence
    Geofence.Builder().setCircularRegion(latitude, longitude, 10f).setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_DWELL or Geofence.GEOFENCE_TRANSITION_EXIT).setExpirationDuration(100000000).build()
}

//Triggers
var thisexit = GeofencingRequest.Builder().setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_EXIT).build()
var thisenter = GeofencingRequest.Builder().setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER).build()

//List of locations
var ListOfLocations = mutableListOf<LatLng>()

@Composable
//Shows the google maps
fun GoogleMapS() {
    var startLoc = LatLng(44.0592, -79.4613)
    var camerapositionstate = rememberCameraPositionState {position=CameraPosition.fromLatLngZoom(startLoc, 1f)}
    var mymap = GoogleMap()
    mymap = GoogleMap(Modifier.fillMaxSize(), camerapositionstate){
        Marker(state = MarkerState(position = startLoc), title = "Marker", snippet = "Work here")
        for (LatLng in ListOfLocations) {
            Marker(state = MarkerState(position = LatLng), title = "Marker", snippet = "Work here")
        }


    }

    //This handles the click
    fun onMapReady(mymap: GoogleMap)
    {
        mymap.setOnMapClickListener {loc ->

            var loc = LatLng(loc.latitude, loc.longitude)

            AddLocation(loc)
            MakeGeoFence()
            mymap.addMarker(MarkerOptions().position(loc).title("This").snippet("Work place"))
        }
    }

    //WorkManager function
    fun UploadMyWork() {
        val SubmitWork: WorkRequest = OneTimeWorkRequestBuilder<MapsWorker>().build()
        WorkManager.getInstance().enqueue(SubmitWork)
    }}


