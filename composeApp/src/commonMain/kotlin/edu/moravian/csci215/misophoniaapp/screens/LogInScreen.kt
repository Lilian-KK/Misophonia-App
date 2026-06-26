package edu.moravian.csci215.misophoniaapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import misophoniaapp.composeapp.generated.resources.Res
import misophoniaapp.composeapp.generated.resources.back_arrow
import misophoniaapp.composeapp.generated.resources.cancel_symbol
import misophoniaapp.composeapp.generated.resources.clear
import misophoniaapp.composeapp.generated.resources.closed_eye
import misophoniaapp.composeapp.generated.resources.forgot_password
import misophoniaapp.composeapp.generated.resources.go_back
import misophoniaapp.composeapp.generated.resources.hide_password
import misophoniaapp.composeapp.generated.resources.history
import misophoniaapp.composeapp.generated.resources.incorrect_credentials
import misophoniaapp.composeapp.generated.resources.login
import misophoniaapp.composeapp.generated.resources.open_eye
import misophoniaapp.composeapp.generated.resources.password
import misophoniaapp.composeapp.generated.resources.phone_number
import misophoniaapp.composeapp.generated.resources.show_password
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

val SurfaceContainerHighest = Color(0xFFE6E0E9)
val OnSurfaceVariant = Color(0xFF49454F)
private val Primary = Color(0xFF6750A4)

@Serializable
data object LogIn

@Composable
fun LoginScreen(
    onLogin: suspend (String, String) -> HttpStatusCode,
    toHub: () -> Unit,
    onForgotPassword: () -> Unit,
    showSnackbar: (String) -> Unit,
    goBack: () -> Unit
) {
    val (phoneNumber, setPhoneNumber) = remember { mutableStateOf("") }
    val (password, setPassword) = remember { mutableStateOf("")}
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(Color.White)
                .statusBarsPadding()
                .navigationBarsPadding()
                .imePadding(),
    ) {
        Column(
            modifier =
                Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp),
        ) {
            Text(
                text = stringResource(Res.string.login),
                fontSize = 28.sp,
                fontWeight = FontWeight.Normal,
                lineHeight = 36.sp,
                letterSpacing = 0.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
            )

            LoginFields(
                onForgotPassword = onForgotPassword,
                phoneNumber = phoneNumber,
                setPhoneNumber = setPhoneNumber,
                password = password,
                setPassword = setPassword,
                goBack = goBack
            )
        }

        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
        ) {
            val incorrectCredentialsMessage = stringResource(Res.string.incorrect_credentials)
            Button(
                onClick = {
                    coroutineScope.launch {
                        val loginStatus = onLogin(phoneNumber, password)
                    if (loginStatus == HttpStatusCode.OK) {
                        toHub()
                    } else {
                        showSnackbar(incorrectCredentialsMessage)
                    }
                } },
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Primary),
            ) {
                Text(
                    text = stringResource(Res.string.login),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 24.sp,
                    letterSpacing = 0.15.sp,
                    color = Color.White,
                )
            }
        }
    }
}

@Composable
private fun LoginFields(
    onForgotPassword: () -> Unit,
    phoneNumber: String,
    setPhoneNumber: (String) -> Unit,
    password: String,
    setPassword: (String) -> Unit,
    goBack: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(32.dp)) {
        FilledTextField(
            value = phoneNumber,
            onValueChange = setPhoneNumber,
            label = stringResource(Res.string.phone_number),
            keyboardType = KeyboardType.Phone
        )

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            FilledTextField(
                value = password,
                onValueChange = setPassword,
                label = stringResource(Res.string.password),
                keyboardType = KeyboardType.Password,
                isPassword = true
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                IconButton(onClick = goBack, modifier = Modifier.size(48.dp)) {
                    Icon(
                        painter = painterResource(Res.drawable.back_arrow),
                        modifier = Modifier.size(24.dp),
                        contentDescription = stringResource(Res.string.go_back),
                        tint = Color.DarkGray
                    )
                }
                TextButton(
                    onClick = onForgotPassword,
                    contentPadding =
                        androidx.compose.foundation.layout
                            .PaddingValues(0.dp),
                ) {
                    Text(
                        text = stringResource(Res.string.forgot_password),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        lineHeight = 16.sp,
                        letterSpacing = 0.5.sp,
                        color = OnSurfaceVariant,
                        textDecoration = TextDecoration.Underline,
                    )
                }
            }
        }
    }
}

@Composable
fun FilledTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false
) {
    var isCensored by remember { mutableStateOf(true) }

    Column {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .background(SurfaceContainerHighest, shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)),
        ) {
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 8.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = label,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        lineHeight = 16.sp,
                        letterSpacing = 0.4.sp,
                        color = OnSurfaceVariant,
                    )
                    androidx.compose.foundation.text.BasicTextField(
                        value = value,
                        onValueChange = onValueChange,
                        singleLine = true,
                        textStyle =
                            TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color(0xFF1D1B20),
                            ),
                        visualTransformation = if (isPassword && isCensored) PasswordVisualTransformation() else VisualTransformation.None,
                        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .height(28.dp),
                    )
                }
                if (isPassword) {
                    IconButton(
                        onClick = { isCensored = !isCensored },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            painter = if (isCensored) painterResource(Res.drawable.open_eye) else painterResource(Res.drawable.closed_eye),
                            contentDescription = if (isPassword) stringResource(Res.string.show_password) else stringResource(Res.string.hide_password),
                            modifier = Modifier.size(20.dp),
                            tint = Color.DarkGray
                        )
                    }
                }
                IconButton(onClick = { onValueChange("") }, modifier = Modifier.size(48.dp)) {
                    Icon(
                        painter = painterResource(Res.drawable.cancel_symbol),
                        contentDescription = stringResource(Res.string.clear),
                        )
                }
            }
        }
        HorizontalDivider(color = OnSurfaceVariant, thickness = 1.dp)
    }
}