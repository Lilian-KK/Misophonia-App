package edu.moravian.csci215.misophoniaapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import edu.moravian.csci215.misophoniaapp.server_data.TokenStorage
import edu.moravian.csci215.misophoniaapp.survey_data.SurveyType
import kotlinx.serialization.Serializable
import misophoniaapp.composeapp.generated.resources.Res
import misophoniaapp.composeapp.generated.resources.amisosr_survey
import org.jetbrains.compose.resources.stringResource

@Serializable
data object AppSettings

@Composable
fun AppSettingsScreen(
    clearTokens: () -> Unit,
    logOut: () -> Unit
) {
    Column {
        Text("This is the app settings screen.")
        Button(onClick = { clearTokens(); logOut() }) {
            Text("Log out")
        }
    }
}
