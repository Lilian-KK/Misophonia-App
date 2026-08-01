package edu.moravian.csci215.misophoniaapp.screens.login

import BadRequestException
import ForbiddenException
import TooManyRequestsException
import UnauthorizedException
import androidx.compose.foundation.layout.Arrangement
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
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import misophoniaapp.composeapp.generated.resources.Res
import misophoniaapp.composeapp.generated.resources.back_arrow
import misophoniaapp.composeapp.generated.resources.go_back
import misophoniaapp.composeapp.generated.resources.password
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

//hi lili if you're seeing this!! forgotpassword no longer uses a code to login, instead that's just a different method of logging in now.
//this screen lets you reset your password while you're logging in if you forgot it--design it however you want!
//i don't really know how this works functionality-wise, might have to ask server side for that

@Serializable
data class ForgotPassword(
    val username: String
)

@Composable
fun ForgotPasswordScreen(
    username: String,
    goBack: () -> Unit,
    resetPassword: suspend (String, String, String) -> Unit,
    showSnackbar: (String) -> Unit
) {
    var code by remember { mutableStateOf(listOf("", "", "", "", "", "")) }
    val focusRequesters = remember { List(6) { FocusRequester() } }
    var newPassword by remember { mutableStateOf("")}
    val coroutineScope = rememberCoroutineScope()

    Text("this is the forgotpasswordscreen!")

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        repeat(6) { index ->
            if (index > 0) Spacer(modifier = Modifier.width(8.dp))
            CodeChip(
                value = code[index],
                focusRequester = focusRequesters[index],
                onValueChange = { newValue ->
                    if (newValue.length <= 1 && (newValue.isEmpty() || newValue.all { it.isDigit() })) {
                        code = code.toMutableList().apply { this[index] = newValue }
                        if (newValue.isNotEmpty() && index < 5) {
                            focusRequesters[index + 1].requestFocus()
                        }
                    }
                },
            )
        }
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
                    resetPassword(username, code.joinToString(""), newPassword)
                    goBack()
                } catch (exception: ForbiddenException) { //403
                    showSnackbar(exception.message?.substringAfter("40: ") ?: "UnauthorizedException")
                } catch (exception: BadRequestException) { //400
                    showSnackbar(exception.message?.substringAfter("400: ") ?: "BadRequestException")
                } catch (exception: TooManyRequestsException) { //429--exceeded rate limitations
                    showSnackbar(exception.message?.substringAfter("429: ") ?: "Too Many Requests Exception")
                }
            }
        }
    ) {
        Text("Update password")
    }
}