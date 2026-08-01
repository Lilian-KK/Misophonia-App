package edu.moravian.csci215.misophoniaapp.screens.login

import BadRequestException
import ForbiddenException
import TooManyRequestsException
import UnauthorizedException
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import edu.moravian.csci215.misophoniaapp.CodeChip
import edu.moravian.csci215.misophoniaapp.CodeEntryPrompt
import edu.moravian.csci215.misophoniaapp.FilledTextField
import edu.moravian.csci215.misophoniaapp.OtpCodeInput
import edu.moravian.csci215.misophoniaapp.ResendCodeRow
import edu.moravian.csci215.misophoniaapp.fredokaFontFamily
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import misophoniaapp.composeapp.generated.resources.Res
import misophoniaapp.composeapp.generated.resources.back_arrow
import misophoniaapp.composeapp.generated.resources.go_back
import misophoniaapp.composeapp.generated.resources.password
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
    showSnackbar: (String) -> Unit
) {
    var code by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("")}
    val coroutineScope = rememberCoroutineScope()
    val fredoka = fredokaFontFamily()

    Column() {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            CodeEntryPrompt(fontFamily = fredoka)

            OtpCodeInput(
                fontFamily = fredoka,
                onCodeChange = { code = it },
            )

            ResendCodeRow(
                fontFamily = fredoka,
                onResendClick = {
                    coroutineScope.launch {
                        try {
                            sendCode(username)
                            showSnackbar("A new code was sent to your phone number")
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

//        repeat(6) { index ->
//            if (index > 0) Spacer(modifier = Modifier.width(8.dp))
//            CodeChip(
//                value = code[index],
//                focusRequester = focusRequesters[index],
//                onValueChange = { newValue ->
//                    if (newValue.length <= 1 && (newValue.isEmpty() || newValue.all { it.isDigit() })) {
//                        code = code.toMutableList().apply { this[index] = newValue }
//                        if (newValue.isNotEmpty() && index < 5) {
//                            focusRequesters[index + 1].requestFocus()
//                        }
//                    }
//                },
//            )
//        }
        }

        IconButton(onClick = goBack, modifier = Modifier.size(48.dp)) {
            Icon(
                painter = painterResource(Res.drawable.back_arrow),
                contentDescription = stringResource(Res.string.go_back),
                modifier = Modifier.size(24.dp),
                tint = Color.DarkGray
            )
        }

        FilledTextField(
            value = newPassword,
            onValueChange = { newPassword = it },
            label = stringResource(Res.string.password),
            keyboardType = KeyboardType.Password,
            isPassword = true
        )

        Button(
            onClick = {
                coroutineScope.launch {
                    try {
                        resetPassword(username, code, newPassword)
                        goBack()
                    } catch (exception: ForbiddenException) { //403
                        showSnackbar(
                            exception.message?.substringAfter("40: ") ?: "UnauthorizedException"
                        )
                    } catch (exception: BadRequestException) { //400
                        showSnackbar(
                            exception.message?.substringAfter("400: ") ?: "BadRequestException"
                        )
                    } catch (exception: TooManyRequestsException) { //429--exceeded rate limitations
                        showSnackbar(
                            exception.message?.substringAfter("429: ")
                                ?: "Too Many Requests Exception"
                        )
                    }
                }
            }
        ) {
            Text("Update password")
        }
    }
}