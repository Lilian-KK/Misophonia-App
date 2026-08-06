package edu.moravian.csci215.misophoniaapp.screens.login

import BadRequestException
import ForbiddenException
import TooManyRequestsException
import UnauthorizedException
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import edu.moravian.csci215.misophoniaapp.AppColors
import edu.moravian.csci215.misophoniaapp.CodeEntryPrompt
import edu.moravian.csci215.misophoniaapp.FilledTextField
import edu.moravian.csci215.misophoniaapp.OtpCodeInput
import edu.moravian.csci215.misophoniaapp.PrimaryButton
import edu.moravian.csci215.misophoniaapp.ResendCodeRow
import edu.moravian.csci215.misophoniaapp.ScreenTitle
import edu.moravian.csci215.misophoniaapp.fredokaFontFamily
import edu.moravian.csci215.misophoniaapp.server_data.LoginResponse
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import misophoniaapp.composeapp.generated.resources.Res
import misophoniaapp.composeapp.generated.resources.back_arrow
import misophoniaapp.composeapp.generated.resources.go_back
import misophoniaapp.composeapp.generated.resources.login
import misophoniaapp.composeapp.generated.resources.new_code_sent
import misophoniaapp.composeapp.generated.resources.password
import misophoniaapp.composeapp.generated.resources.username
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Serializable
data class ForgotPassword(
    val username: String
)

@Composable
fun ForgotPasswordScreen(
    username: String,
    goBack: () -> Unit,
    resetPassword: suspend (String, String, String) -> Unit,
    sendCode: suspend (String) -> Unit,
    showSnackbar: (String) -> Unit,
    storeTokens: suspend (String, String) -> Unit,
    toHub: () -> Unit = {},
    onLogin: suspend (String, String, String) -> LoginResponse
) {
    var code by remember { mutableStateOf("") }
    var codeSent by remember { mutableStateOf(false) }
    var newPassword by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val fredoka = fredokaFontFamily()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Background),
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            Spacer(modifier = Modifier.height(48.dp))

            ScreenTitle(
                text = "Forgot Password",
                modifier = Modifier.fillMaxWidth()
            )

            FilledTextField(
                value = username,
                label = stringResource(Res.string.username),
                keyboardType = KeyboardType.Text,
                fontFamily = fredoka,
            )

            if (!codeSent) {
                PrimaryButton(
                    text = "Send Code to my Phone Number",
                    fontFamily = fredoka,
                    onClick = {
                        coroutineScope.launch {
                            try {
                                sendCode(username)
                                codeSent = true
                            } catch (exception: BadRequestException) { //400
                                showSnackbar(exception.message ?: "BadRequestException")
                            } catch (exception: ForbiddenException) { //403--code could not be delivered
                                showSnackbar(exception.message ?: "ForbiddenException")
                            } catch (exception: TooManyRequestsException) { //429--exceeded rate limit
                                showSnackbar(exception.message ?: "Too Many Requests Exception")
                            }
                        }
                    }
                )
            }

            if (codeSent) {
                FilledTextField(
                    value = newPassword,
                    onValueChange = { newPassword = it },
                    label = "New Password",
                    keyboardType = KeyboardType.Password,
                    isPassword = true
                )

                CodeEntryPrompt(fontFamily = fredoka)

                OtpCodeInput(
                    fontFamily = fredoka,
                    onCodeChange = { code = it },
                )

                val newCodeSentMessage = stringResource(Res.string.new_code_sent)
                ResendCodeRow(
                    fontFamily = fredoka,
                    onResendClick = {
                        coroutineScope.launch {
                            try {
                                sendCode(username)
                                showSnackbar(newCodeSentMessage)
                            } catch (exception: BadRequestException) {
                                showSnackbar(exception.message ?: "BadRequest Exception")
                            } catch (exception: ForbiddenException) {
                                showSnackbar(exception.message ?: "Forbidden Exception")
                            } catch (exception: TooManyRequestsException) { //429--exceeded rate limitations
                                showSnackbar(exception.message ?: "Too Many Requests Exception")
                            }
                        }
                    },
                )

                PrimaryButton(
                    text = "Update Password",
                    fontFamily = fredoka,
                    onClick = {
                        coroutineScope.launch {
                            try {
                                resetPassword(username, code, newPassword)
                                showSnackbar("Password reset!")
                                val tokenResponse = onLogin(username, "password", newPassword)
                                println(tokenResponse)
                                storeTokens(tokenResponse.access_token, tokenResponse.refresh_token)
                                toHub()
                            } catch (exception: UnauthorizedException) { //401--invalid username
                                showSnackbar(exception.message ?: "Unauthorized Exception")
                            } catch (exception: BadRequestException) { //400
                                showSnackbar(exception.message ?: "Bad Request Exception")
                            } catch (exception: ForbiddenException) { //403--invalid text code
                                showSnackbar(exception.message ?: "Forbidden Exception")
                            } catch (exception: TooManyRequestsException) { //429--exceeded rate limitations
                                showSnackbar(exception.message ?: "Too Many Requests Exception")
                            }
                        }
                    },
                )

            }
        }

        IconButton(
            onClick = goBack,
            modifier = Modifier
                .size(48.dp)
                .background(AppColors.Button, CircleShape)
        ) {
            Icon(
                painter = painterResource(Res.drawable.back_arrow),
                contentDescription = stringResource(Res.string.go_back),
                modifier = Modifier.size(24.dp),
                tint = Color.White
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

    }
}