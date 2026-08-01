package edu.moravian.csci215.misophoniaapp.screens.login

import ErrorResponse
import UnauthorizedException
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import edu.moravian.csci215.misophoniaapp.AppColors
import edu.moravian.csci215.misophoniaapp.FilledTextField
import edu.moravian.csci215.misophoniaapp.PrimaryButton
import edu.moravian.csci215.misophoniaapp.ScreenTitle
import edu.moravian.csci215.misophoniaapp.fredokaFontFamily
import edu.moravian.csci215.misophoniaapp.server_data.LoginResponse
import io.ktor.client.call.body
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import misophoniaapp.composeapp.generated.resources.Res
import misophoniaapp.composeapp.generated.resources.back_arrow
import misophoniaapp.composeapp.generated.resources.forgot_password
import misophoniaapp.composeapp.generated.resources.go_back
import misophoniaapp.composeapp.generated.resources.login
import misophoniaapp.composeapp.generated.resources.password
import misophoniaapp.composeapp.generated.resources.phone_number
import misophoniaapp.composeapp.generated.resources.username
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Serializable
data object BaseLogin

@Composable
fun BaseLoginScreen(
    showSnackbar: (String) -> Unit,
    toCodeLogin: (String) -> Unit,
    toPasswordLogin: (String) -> Unit,
    goBack: () -> Unit,
) {
    val (username, setUsername) = remember { mutableStateOf("") }
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
                onValueChange = setUsername,
                label = stringResource(Res.string.username),
                fontFamily = fredoka,
            )

            PrimaryButton(
                text = "Log in with phone code",
                onClick = {
                    if (username.isEmpty()) {
                        showSnackbar("Field left blank")
                    } else {
                        toCodeLogin(username)
                    }
                },
                fontFamily = fredoka,
            )

            PrimaryButton(
                text = "Log in with password",
                onClick = {
                    if (username.isEmpty()) {
                        showSnackbar("Field left blank")
                    } else {
                        toPasswordLogin(username)
                    }
                },
                fontFamily = fredoka,
            )
        }

        IconButton(
            onClick = goBack,
            modifier =
                Modifier
                    .padding(24.dp)
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
    }
}
