package edu.moravian.csci215.misophoniaapp.screens.login

import BadRequestException
import ErrorResponse
import ForbiddenException
import TooManyRequestsException
import UnauthorizedException
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.moravian.csci215.misophoniaapp.AppColors
import edu.moravian.csci215.misophoniaapp.FilledTextField
import edu.moravian.csci215.misophoniaapp.PrimaryButton
import edu.moravian.csci215.misophoniaapp.ScreenTitle
import edu.moravian.csci215.misophoniaapp.fredokaFontFamily
import edu.moravian.csci215.misophoniaapp.server_data.LoginResponse
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import misophoniaapp.composeapp.generated.resources.Res
import misophoniaapp.composeapp.generated.resources.back_arrow
import misophoniaapp.composeapp.generated.resources.forgot_password
import misophoniaapp.composeapp.generated.resources.go_back
import misophoniaapp.composeapp.generated.resources.login
import misophoniaapp.composeapp.generated.resources.password
import misophoniaapp.composeapp.generated.resources.username
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Serializable
data class PasswordLogin(
    val username: String,
)

@Composable
fun PasswordLoginScreen(
    username: String,
    goBack: () -> Unit,
    storeTokens: suspend (String, String) -> Unit,
    showSnackbar: (String) -> Unit,
    toHub: () -> Unit,
    toForgotPassword: (String) -> Unit,
    onLogin: suspend (String, String, String) -> LoginResponse,
) {
    val (password, setPassword) = remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val fredoka = fredokaFontFamily()

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(AppColors.Background)
                .statusBarsPadding()
                .navigationBarsPadding()
                .imePadding(),
    ) {
        Column(
            modifier =
                Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            Spacer(modifier = Modifier.height(48.dp))

            ScreenTitle(
                text = stringResource(Res.string.login),
                modifier = Modifier.fillMaxWidth(),
            )

            FilledTextField(
                value = username,
                label = stringResource(Res.string.username),
                keyboardType = KeyboardType.Text,
                fontFamily = fredoka,
            )

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                FilledTextField(
                    value = password,
                    onValueChange = setPassword,
                    label = stringResource(Res.string.password),
                    keyboardType = KeyboardType.Password,
                    isPassword = true,
                    fontFamily = fredoka,
                )

                Text(
                    text = stringResource(Res.string.forgot_password),
                    fontFamily = fredoka,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = AppColors.FieldLabel,
                    textDecoration = TextDecoration.Underline,
                    modifier =
                        Modifier
                            .align(Alignment.End)
                            .clickable(onClick = { toForgotPassword(username) }),
                )
            }
        }

        Column(modifier = Modifier.padding(24.dp)) {
            IconButton(
                onClick = goBack,
                modifier =
                    Modifier
                        .size(48.dp)
                        .background(AppColors.Button, CircleShape),
            ) {
                Icon(
                    painter = painterResource(Res.drawable.back_arrow),
                    modifier = Modifier.size(24.dp),
                    contentDescription = stringResource(Res.string.go_back),
                    tint = Color.White,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            PrimaryButton(
                text = stringResource(Res.string.login),
                fontFamily = fredoka,
                onClick = {
                    coroutineScope.launch {
                        try {
                            val tokenResponse = onLogin(username, "password", password)
                            println(tokenResponse)
                            storeTokens(tokenResponse.access_token, tokenResponse.refresh_token)
                            toHub()
                        } catch (exception: UnauthorizedException) {
                            // 401
                            showSnackbar(exception.message ?: "UnauthorizedException")
                        } catch (exception: BadRequestException) {
                            // 400
                            showSnackbar(exception.message ?: "BadRequestException")
                        } catch (exception: TooManyRequestsException) {
                            // 429--exceeded rate limitations
                            showSnackbar(exception.message ?: "Too Many Requests Exception")
                        }
                    }
                },
            )
        }
    }
}
