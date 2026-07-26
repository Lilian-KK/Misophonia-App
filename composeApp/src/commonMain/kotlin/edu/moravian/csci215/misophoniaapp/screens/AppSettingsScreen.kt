package edu.moravian.csci215.misophoniaapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
data object AppSettings

@Composable
fun AppSettingsScreen(
    clearTokens: () -> Unit,
    logOut: suspend () -> Unit,
    toSetup: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    Column {
        Text(text = "Settings", style = MaterialTheme.typography.headlineSmall)
        Button(onClick = {
            coroutineScope.launch {
                logOut()
            }
            clearTokens()
            toSetup()
        }) {
            Text("Log out")
        }
    }
}
