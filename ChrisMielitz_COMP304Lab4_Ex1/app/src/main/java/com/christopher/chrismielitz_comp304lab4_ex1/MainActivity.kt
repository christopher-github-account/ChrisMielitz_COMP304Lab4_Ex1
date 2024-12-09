package com.christopher.chrismielitz_comp304lab4_ex1

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.christopher.chrismielitz_comp304lab4_ex1.ui.theme.ChrisMielitz_COMP304Lab4_Ex1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val GoToMaps = Intent(this, MapsActivity::class.java)
            ChrisMielitz_COMP304Lab4_Ex1Theme {
                Column {
                    Text("WorkManager and Maps")
                    Text("Click the button to get started")
                    //This sends the user to google maps
                    FloatingActionButton(onClick = {startActivity(GoToMaps)}) {
                        Icon(Icons.Filled.Add, "hello")

                        for(LatLng in ListOfLocations)
                        {
                            Text(LatLng.toString())
                        }

                    }
                }
            }
        }
    }
}

