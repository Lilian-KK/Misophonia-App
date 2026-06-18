package edu.moravian.csci215.misophoniaapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.serialization.Serializable
import misophoniaapp.composeapp.generated.resources.Res
import misophoniaapp.composeapp.generated.resources.cancel_symbol
import misophoniaapp.composeapp.generated.resources.create_account
import misophoniaapp.composeapp.generated.resources.name
import misophoniaapp.composeapp.generated.resources.password
import misophoniaapp.composeapp.generated.resources.phone_number
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Serializable
data object CreateAccount

@Composable
fun CreateAccountScreen(
    onCreateAccount: (phoneNumber: String, name: String, password: String) -> Unit = { _, _, _ -> },
) {
    var phoneNumber by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val primaryColor = Color(0xFF6750A4)
    val surfaceColor = Color(0xFFE6E0E9)
    val onSurfaceVariant = Color(0xFF49454F)

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 52.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        Text(
            text = stringResource(Res.string.create_account),
            fontSize = 28.sp,
            fontWeight = FontWeight.Normal,
            lineHeight = 36.sp,
            textAlign = TextAlign.Center,
            color = Color.Black,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
        )
        Column(verticalArrangement = Arrangement.spacedBy(32.dp)) {
            FilledTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = stringResource(Res.string.phone_number),
                keyboardType = KeyboardType.Phone,
            )

            FilledTextField(
                value = name,
                onValueChange = { name = it },
                label = stringResource(Res.string.name)
            )



            FilledTextField(
                value = password,
                onValueChange = { password = it },
                label = stringResource(Res.string.password),
                keyboardType = KeyboardType.Password,
            )
        }

        Spacer(modifier = Modifier.height(90.dp))

        Button(
            onClick = {
                onCreateAccount(phoneNumber, name, password)
            },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(56.dp),
            shape = RoundedCornerShape(100.dp),
            colors =
                ButtonDefaults.buttonColors(
                    containerColor = primaryColor,
                    contentColor = Color.White,
                ),
            enabled = phoneNumber.isNotEmpty() && name.isNotEmpty() && password.isNotEmpty(),
        ) {
            Text(
                text = stringResource(Res.string.create_account),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                lineHeight = 24.sp,
                letterSpacing = 0.15.sp,
            )
        }
    }
}
