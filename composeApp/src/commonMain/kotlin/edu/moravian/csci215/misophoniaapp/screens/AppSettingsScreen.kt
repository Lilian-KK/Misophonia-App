package edu.moravian.csci215.misophoniaapp.screens

import BadRequestException
import ForbiddenException
import NotFoundException
import TooManyRequestsException
import UnauthorizedException
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
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
import edu.moravian.csci215.misophoniaapp.FilledTextField
import edu.moravian.csci215.misophoniaapp.OtpCodeInput
import edu.moravian.csci215.misophoniaapp.fredokaFontFamily
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
data object AppSettings

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
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
    val fredoka = fredokaFontFamily()

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

        //change password
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

        Button(onClick = {
            if (!changingPassword) {
                changingPassword = true
            } else {
                coroutineScope.launch {
                    try {
                        changePassword(currentPassword, newPassword)
                        showSnackbar("Password successfully changed.")
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


        //changing phone number
        if (!changingPhoneNumber) {
            Button(onClick = { changingPhoneNumber = true } ) {
                Text("Change Phone Number")
            }
        } else {
            if (changingPhoneNumber) {
                FilledTextField(
                    value = newPhoneNumber,
                    onValueChange = {newPhoneNumber = it},
                    label = "New Phone Number"
                )
                Spacer(modifier = Modifier.height(24.dp))

                Button(onClick = {
                    //todo add a send code endpoint here
                    codeBoxVisible = true
                } ) {
                    Text("Send Code")
                }

                if (codeBoxVisible) {
                    OtpCodeInput(
                        fontFamily = fredoka,
                        onCodeChange = { code = it },
                    )
                }
            }

            Button(onClick = { coroutineScope.launch {
                    try {
                        changePhoneNumber(newPhoneNumber, code)
                        changingPhoneNumber = false
                        showSnackbar("Phone number successfully changed.")
                    } catch (exception: BadRequestException) {
                        showSnackbar(exception.message ?: "BadRequestException")
                    } catch (exception: UnauthorizedException) {
                        showSnackbar(exception.message ?: "UnauthorizedException")
                    } catch (exception: ForbiddenException) { //403--invalid code
                        showSnackbar(exception.message ?: "ForbiddenException")
                    } catch (exception: NotFoundException) { //404--user not found
                        showSnackbar(exception.message ?: "Not Found Exception")
                    }
                } }
            ) {
                Text("Change Phone Number")
            }
        }

    }
}