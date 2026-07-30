package edu.moravian.csci215.misophoniaapp.screens

import BadRequestException
import ErrorResponse
import ForbiddenException
import NotFoundException
import TooManyRequestsException
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
import io.ktor.client.plugins.ClientRequestException
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
data object AppSettings

@Composable
fun AppSettingsScreen(
    showSnackbar: (String) -> Unit,
    clearTokens: suspend () -> Unit,
    logOut: suspend () -> Unit,
    changePassword: suspend (String, String) -> Unit,
    changePhoneNumber: suspend (String, String) -> Unit,
    toSetup: () -> Unit,
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
            coroutineScope.launch {
                try {
                    logOut()
                    clearTokens()
                    toSetup()
                } catch (exception: UnauthorizedException) { //401
                    showSnackbar(exception.message ?: "UnauthorizedException")
                } catch (exception: BadRequestException) { //400
                    showSnackbar(exception.message ?: "BadRequestException")
                }
            }
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
                coroutineScope.launch {
                    try {
                        changePassword(currentPassword, newPassword)
                    } catch (exception: BadRequestException) {
                        showSnackbar(exception.message ?: "BadRequestException")
                    } catch (exception: UnauthorizedException) {
                        showSnackbar(exception.message ?: "UnauthorizedException")
                    } catch (exception: ForbiddenException) { //403--current password field is incorrect
                        showSnackbar(exception.message ?: "ForbiddenException")
                    } catch (exception: NotFoundException) { //404--user not found
                        showSnackbar(exception.message ?: "Not Found Exception")
                    } catch (exception: TooManyRequestsException) { //429--exceeded rate limitations
                        showSnackbar(exception.message ?: "Too Many Requests Exception")
                    }
                }
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
