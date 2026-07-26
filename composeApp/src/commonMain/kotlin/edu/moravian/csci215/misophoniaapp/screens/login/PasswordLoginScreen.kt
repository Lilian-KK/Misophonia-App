package edu.moravian.csci215.misophoniaapp.screens.login

import ErrorResponse
import UnauthorizedException
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.moravian.csci215.misophoniaapp.server_data.LoginResponse
import io.ktor.client.call.body
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import misophoniaapp.composeapp.generated.resources.Res
import misophoniaapp.composeapp.generated.resources.back_arrow
import misophoniaapp.composeapp.generated.resources.go_back
import misophoniaapp.composeapp.generated.resources.login
import misophoniaapp.composeapp.generated.resources.password
import misophoniaapp.composeapp.generated.resources.phone_number
import misophoniaapp.composeapp.generated.resources.username
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Serializable
data class PasswordLogin(
    val username: String
)

@Composable
fun PasswordLoginScreen(
    username: String,
    goBack: () -> Unit,
    storeTokens: suspend (String, String) -> Unit,
    showSnackbar: (String) -> Unit,
    toHub: () -> Unit,
    onLogin: suspend (String, String, String) -> LoginResponse,
    ) {
    val (password, setPassword) = remember { mutableStateOf("")}
    val coroutineScope = rememberCoroutineScope()

   Column(modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding(),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
       Column(
           modifier = Modifier
               .weight(1f)
               .verticalScroll(rememberScrollState())
               .padding(16.dp),
           verticalArrangement = Arrangement.spacedBy(32.dp),
       ) {
           FilledTextField(
               value = username,
               label = stringResource(Res.string.username),
               keyboardType = KeyboardType.Text
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
               }
           }


           Box(modifier = Modifier
               .fillMaxWidth()
               .padding(horizontal = 16.dp, vertical = 16.dp),
           ) { Button(
               onClick = {
                   coroutineScope.launch {
                       try {
                           val tokenResponse = onLogin(username, "password", password)
                           println(tokenResponse)
                           storeTokens(tokenResponse.access_token, tokenResponse.refresh_token)
                           toHub()
                       } catch (exception: UnauthorizedException) {
                           val error = exception.response?.body<ErrorResponse>()
                           showSnackbar(error?.message ?: "")
                       }
                       //todo add more exceptions
                   } },
               modifier = Modifier
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
}