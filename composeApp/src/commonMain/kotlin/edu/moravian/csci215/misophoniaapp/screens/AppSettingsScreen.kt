package edu.moravian.csci215.misophoniaapp.screens

import ErrorResponse
import UnauthorizedException
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.moravian.csci215.misophoniaapp.screens.login.FilledTextField
import io.ktor.client.call.body
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
data object AppSettings

@Composable
fun AppSettingsScreen(
    clearTokens: () -> Unit,
    logOut: () -> Unit,
    changePassword: (String, String) -> Unit,
    changePhoneNumber: (String, String) -> Unit,
    toSetup: () -> Unit,
    showSnackbar: (String) -> Unit,
    ) {
    val coroutineScope = rememberCoroutineScope()

    var changingPassword by remember { mutableStateOf(false) }
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }

    var changingPhoneNumber by remember { mutableStateOf(false) }
    var code by remember { mutableStateOf("") }
    var newPhoneNumber by remember { mutableStateOf("") }
    var codeBoxVisible by remember { mutableStateOf(false) }


    Column {
        Text(text = "Settings", style = MaterialTheme.typography.headlineSmall)

        //logout button
        Button(onClick = {
            try {
                logOut()
            } catch (exception: UnauthorizedException) {
                coroutineScope.launch {
                    val error = exception.response?.body<ErrorResponse>()
                    showSnackbar(error?.message ?: "")
                }
            }
            clearTokens()
            toSetup()
        }) {
            Text("Log out")
        }

        //change password input fields
        if (changingPassword) {
            FilledTextField(
                value = currentPassword,
                onValueChange = { currentPassword = it },
                label = "Current Password",
                isPassword = true
            )
            Spacer(modifier = Modifier.height(24.dp))
            FilledTextField(
                value = newPassword,
                onValueChange = { newPassword = it },
                label = "New Password",
                isPassword = true
            )
        }

        //changing password button
        Button(onClick = {
            if (!changingPassword) {
                changingPassword = true
            } else {
                changePassword(currentPassword, newPassword)
                changingPassword = false
            }
        }
        ) {
            Text("Change Password")
        }

//        //change phone number input fields
//        if (changingPhoneNumber) {
//            FilledTextField(
//                value = newPhoneNumber,
//                onValueChange = {newPhoneNumber = it},
//                label = "New Phone Number"
//            )
//            Spacer(modifier = Modifier.height(24.dp))
//
//            Button(onClick = { codeBoxVisible = true } ) {
//                Text("Send Code")
//            }
//
//            if (codeBoxVisible) {
//                FilledTextField(
//                    value = code,
//                    onValueChange = { code = it },
//                    label = "6-digit code"
//                )
//            }
//        }
//
//        //changing phone number button
//        Button(onClick = {
//            if (!changingPhoneNumber) {
//                changingPhoneNumber = true
//            }
//            else {
//                changePhoneNumber(newPhoneNumber, code)
//                changingPhoneNumber = false
//            }
//        }
//        ) {
//            Text("Change Password")
//        }
//    }
    }
}
