package edu.moravian.csci215.misophoniaapp.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import kotlinx.serialization.Serializable

@Serializable
data object WifiNetworks

@Composable
fun WifiNetworksScreen() {
    Text("This is the Wifi Networks Screen")
}

