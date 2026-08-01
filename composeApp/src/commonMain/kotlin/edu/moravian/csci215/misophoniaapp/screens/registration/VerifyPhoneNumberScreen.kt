package edu.moravian.csci215.misophoniaapp.screens.registration

import BadRequestException
import ConflictException
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import edu.moravian.csci215.misophoniaapp.screens.login.CodeChip
import edu.moravian.csci215.misophoniaapp.server_data.UserResponse
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import misophoniaapp.composeapp.generated.resources.Res
import misophoniaapp.composeapp.generated.resources.back_arrow
import misophoniaapp.composeapp.generated.resources.go_back
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import kotlin.collections.toMutableList

@Serializable
data class VerifyPhoneNumber(
    val phoneNumber: String,
    val username: String,
    val password: String
)

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun VerifyPhoneNumberScreen(
    showSnackbar: (String) -> Unit,
    goBack: () -> Unit,
    toHub: () -> Unit,
    sendCode: suspend (String) -> Unit, //todo: add a resend code button like codelogin has
    createAccount: suspend (String) -> UserResponse,
) {
    val coroutineScope = rememberCoroutineScope()
    var code by remember { mutableStateOf(listOf("", "", "", "", "", "")) }
    val focusRequesters = remember { List(6) { FocusRequester() } }


    Column() {
        Text("verify")

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

        Button(
            onClick = {
                coroutineScope.launch {
                    try {
                        createAccount(code.joinToString(""))
                        toHub()
                    } catch (exception: BadRequestException) {
                        showSnackbar(exception.message?.substringAfter("400: ") ?: "Bad Request Exception")
                    } catch (exception: ConflictException) {
                        showSnackbar(exception.message?.substringAfter("409: ") ?: "Conflict Exception")
                    }
                }
            }
        ) {
            Text("continue to hub")
        }
    }
}
