package edu.moravian.csci215.misophoniaapp.screens.login

import BadRequestException
import ErrorResponse
import UnauthorizedException
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.moravian.csci215.misophoniaapp.server_data.LoginResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ServerResponseException
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import misophoniaapp.composeapp.generated.resources.Res
import misophoniaapp.composeapp.generated.resources.back_arrow
import misophoniaapp.composeapp.generated.resources.continue_
import misophoniaapp.composeapp.generated.resources.didnt_get_code
import misophoniaapp.composeapp.generated.resources.enter_code
import misophoniaapp.composeapp.generated.resources.forgot_password
import misophoniaapp.composeapp.generated.resources.go_back
import misophoniaapp.composeapp.generated.resources.phone_number
import misophoniaapp.composeapp.generated.resources.send_another_code
import misophoniaapp.composeapp.generated.resources.send_code
import misophoniaapp.composeapp.generated.resources.username
import misophoniaapp.composeapp.generated.resources.verify_for_pw_creation
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Serializable
data class CodeLogin (
    val username: String
)

@Composable
fun CodeLoginScreen(
    username: String,
    goBack: () -> Unit,
    storeTokens: suspend (String, String) -> Unit,
    showSnackbar: (String) -> Unit,
    toHub: () -> Unit = {},
    sendCode: (String) -> Unit,
    onLogin: suspend (String, String, String) -> LoginResponse
) {
    var codeSent by remember { mutableStateOf(false) }
    var code by remember { mutableStateOf(listOf("", "", "", "", "", "")) }
    val focusRequesters = remember { List(6) { FocusRequester() } }
    val coroutineScope = rememberCoroutineScope()

    LazyColumn(
        modifier =
            Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 16.dp, vertical = 52.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            Text(
                //text = stringResource(Res.string.forgot_password),
                text = "Code Login",
                fontSize = 28.sp,
                fontWeight = FontWeight.Normal,
                lineHeight = 36.sp,
                textAlign = TextAlign.Center,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth(),
            )
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            Text(
                text = "Please click the button and enter the 6-digit code that was sent to your phone.",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                lineHeight = 20.sp,
                letterSpacing = 0.25.sp,
                textAlign = TextAlign.Center,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth(),
            )
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
        }

        item {
            FilledTextField(
                value = username,
                label = stringResource(Res.string.username),
                keyboardType = KeyboardType.Text
            )
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }


        if (!codeSent) {
            item {
                Spacer(modifier = Modifier.height(32.dp))
            }

            item {
                Button(
                    onClick = {
                        try {
                            sendCode(username)
                            codeSent = true
                        } catch (exception: ServerResponseException) {
                            coroutineScope.launch {
                                val error = exception.response?.body<ErrorResponse>()
                                showSnackbar(error?.message ?: "")
                            }
                        } },
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                    shape = RoundedCornerShape(100.dp),
                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF6750A4),
                            contentColor = Color.White,
                        ),
                ) {
                    Text(
                        text = stringResource(Res.string.send_code),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        lineHeight = 24.sp,
                        letterSpacing = 0.15.sp,
                    )
                }
            }
        }

        if (codeSent) {
            item {
                Spacer(modifier = Modifier.height(32.dp))
            }

            item {
                Text(
                    text = stringResource(Res.string.enter_code),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 20.sp,
                    letterSpacing = 0.25.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
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
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }

            item {
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 32.dp),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = stringResource(Res.string.didnt_get_code),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        lineHeight = 20.sp,
                        letterSpacing = 0.25.sp,
                        color = Color.Black,
                    )
                    Text(
                        text = stringResource(Res.string.send_another_code),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        lineHeight = 20.sp,
                        letterSpacing = 0.25.sp,
                        color = Color.Black,
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier.clickable { },
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
            }

            item {
                Button(
                    onClick = {
                        toHub()
                        coroutineScope.launch {
                            try {
                                val tokenResponse = onLogin(username, "code", code.joinToString(""))
                                println(tokenResponse)
                                storeTokens(tokenResponse.access_token, tokenResponse.refresh_token)
                                toHub()
                            } catch (exception: UnauthorizedException) {
                                val error = exception.response?.body<ErrorResponse>()
                                showSnackbar(error?.message ?: "Unauthorized Exception")
                            } catch (exception: BadRequestException) {
                                val error = exception.response?.body<ErrorResponse>()
                                showSnackbar(error?.message ?: "Bad Request Exception")
                            }
                            //todo add more exceptions
                        } },
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                    shape = RoundedCornerShape(100.dp),
                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF6750A4),
                            contentColor = Color.White,
                        ),
                ) {
                    Text(
                        text = stringResource(Res.string.continue_),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        lineHeight = 24.sp,
                        letterSpacing = 0.15.sp,
                    )
                }
            }
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
}

@Composable
fun CodeChip(
    value: String,
    focusRequester: FocusRequester,
    onValueChange: (String) -> Unit,
) {
    var isFocused by remember { mutableStateOf(false) }
    val borderColor = if (isFocused) Color(0xFF6750A4) else Color(0xFFCAC4D0)

    Box(
        modifier =
            Modifier
                .width(42.dp)
                .height(56.dp)
                .border(
                    width = 1.dp,
                    color = borderColor,
                    shape = RoundedCornerShape(8.dp),
                ).background(Color.White, RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center,
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier =
                Modifier
                    .fillMaxSize()
                    .focusRequester(focusRequester)
                    .onFocusChanged { isFocused = it.isFocused },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            cursorBrush = SolidColor(Color(0xFF6750A4)),
            textStyle =
                TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    color = Color(0xFF49454F),
                ),
            decorationBox = { innerTextField ->
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    innerTextField()
                }
            },
        )
    }
}

