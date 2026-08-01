package edu.moravian.csci215.misophoniaapp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import misophoniaapp.composeapp.generated.resources.Res
import misophoniaapp.composeapp.generated.resources.cancel_symbol
import misophoniaapp.composeapp.generated.resources.clear
import misophoniaapp.composeapp.generated.resources.closed_eye
import misophoniaapp.composeapp.generated.resources.didnt_get_code
import misophoniaapp.composeapp.generated.resources.enter_code
import misophoniaapp.composeapp.generated.resources.fredoka_regular
import misophoniaapp.composeapp.generated.resources.fredoka_semibold
import misophoniaapp.composeapp.generated.resources.hide_password
import misophoniaapp.composeapp.generated.resources.open_eye
import misophoniaapp.composeapp.generated.resources.send_another_code
import misophoniaapp.composeapp.generated.resources.show_password
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

// -----------------------------------------------------------------------
// COLORS
// Shared across every screen. Update here once and it propagates
// everywhere these are referenced.
// -----------------------------------------------------------------------
object AppColors {
    val Background = Color(0xFFFDFDFB)
    val Button = Color(0xFF9263AA)
    val ButtonStroke = Color(0x59000000)   // #000000 at 35% opacity
    val Title = Color(0xFF9263AA)
    val FieldBorder = Color(0xFFD9D9D9)
    val FieldLabel = Color(0xFF1D1B20)
    val FieldPlaceholder = Color(0xFFA8A8A8)
}

// -----------------------------------------------------------------------
// FONT
// Requires fredoka_regular.ttf and fredoka_semibold.ttf under
// commonMain/composeResources/font/
// -----------------------------------------------------------------------
@Composable
fun fredokaFontFamily(): FontFamily = FontFamily(
    Font(Res.font.fredoka_regular, FontWeight.Normal),
    Font(Res.font.fredoka_semibold, FontWeight.SemiBold),
    Font(Res.font.fredoka_semibold, FontWeight.Bold)
)

// -----------------------------------------------------------------------
// REUSABLE COMPONENTS
// -----------------------------------------------------------------------

/**
 * The standard full-width purple button used across Setup, Loading, and
 * Login screens: rounded corners, 35%-opacity black stroke, white Fredoka
 * label text.
 */
@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    fontFamily: FontFamily = fredokaFontFamily(),
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = AppColors.Button),
        border = BorderStroke(1.dp, AppColors.ButtonStroke)
    ) {
        Text(
            text = text,
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            color = Color.White
        )
    }
}

/**
 * The standard screen title style used on Setup and Login screens:
 * bold Fredoka, purple, centered.
 */
@Composable
fun ScreenTitle(
    text: String,
    modifier: Modifier = Modifier,
    fontFamily: FontFamily = fredokaFontFamily(),
) {
    Text(
        text = text,
        fontFamily = fontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 34.sp,
        lineHeight = 40.sp,
        color = AppColors.Title,
        textAlign = TextAlign.Center,
        modifier = modifier
    )
}

/**
 * The standard bordered, rounded text field used across Login and other
 * form screens: label above, white rounded box with a light-gray border,
 * gray placeholder text shown inside when empty, optional password-reveal
 * toggle, and a clear ("x") button that appears once there's input.
 */
@Composable
fun FilledTextField(
    value: String,
    onValueChange: (String) -> Unit = {},
    label: String,
    modifier: Modifier = Modifier,
    fontFamily: FontFamily = fredokaFontFamily(),
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false,
) {
    var isCensored by remember { mutableStateOf(true) }

    Column(modifier = modifier) {
        Text(
            text = label,
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            color = AppColors.FieldLabel,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(8.dp))
                .border(1.dp, AppColors.FieldBorder, shape = RoundedCornerShape(8.dp))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    if (value.isEmpty()) {
                        Text(
                            text = label,
                            fontFamily = fontFamily,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            color = AppColors.FieldPlaceholder,
                        )
                    }
                    BasicTextField(
                        value = value,
                        onValueChange = onValueChange,
                        singleLine = true,
                        textStyle = TextStyle(
                            fontFamily = fontFamily,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            color = AppColors.FieldLabel,
                        ),
                        visualTransformation = if (isPassword && isCensored) PasswordVisualTransformation() else VisualTransformation.None,
                        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
                if (isPassword) {
                    IconButton(
                        onClick = { isCensored = !isCensored },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            painter = if (isCensored) painterResource(Res.drawable.open_eye) else painterResource(Res.drawable.closed_eye),
                            contentDescription = if (isCensored) stringResource(Res.string.show_password) else stringResource(Res.string.hide_password),
                            modifier = Modifier.size(20.dp),
                            tint = Color.DarkGray
                        )
                    }
                }
                if (value.isNotEmpty()) {
                    IconButton(
                        onClick = { onValueChange("") },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.cancel_symbol),
                            contentDescription = stringResource(Res.string.clear),
                            modifier = Modifier.size(18.dp),
                            tint = Color.DarkGray
                        )
                    }
                }
            }
        }
    }
}

/**
 * The "Enter the 6-digit code sent to your phone number" style prompt
 * used above the OTP boxes on code-login/verification screens.
 */
@Composable
fun CodeEntryPrompt(
    modifier: Modifier = Modifier,
    fontFamily: FontFamily = fredokaFontFamily(),
) {
    Text(
        text = stringResource(Res.string.enter_code),
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        textAlign = TextAlign.Center,
        color = AppColors.FieldLabel,
        modifier = modifier.fillMaxWidth(),
    )
}

/**
 * A single digit box within the OTP row. Kept private — always used as
 * part of [OtpCodeInput], never on its own.
 */
@Composable
fun CodeChip(
    value: String,
    focusRequester: FocusRequester,
    onValueChange: (String) -> Unit,
    fontFamily: FontFamily,
) {
    var isFocused by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .width(48.dp)
            .height(56.dp)
            .border(
                width = 1.dp,
                color = AppColors.Button,
                shape = RoundedCornerShape(8.dp),
            )
            .background(Color.White, RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center,
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .onFocusChanged { isFocused = it.isFocused },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            cursorBrush = SolidColor(AppColors.Button),
            textStyle = TextStyle(
                fontFamily = fontFamily,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                color = AppColors.FieldLabel,
            ),
            decorationBox = { innerTextField ->
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                    innerTextField()
                }
            },
        )
    }
}

/**
 * The row of digit boxes used for one-time-code entry. Manages its own
 * per-digit state and focus-advance behavior internally; call sites just
 * read the assembled code via [onCodeChange].
 */
@Composable
fun OtpCodeInput(
    modifier: Modifier = Modifier,
    length: Int = 6,
    fontFamily: FontFamily = fredokaFontFamily(),
    onCodeChange: (String) -> Unit = {},
) {
    var code by remember { mutableStateOf(List(length) { "" }) }
    val focusRequesters = remember { List(length) { FocusRequester() } }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        repeat(length) { index ->
            if (index > 0) Spacer(modifier = Modifier.width(8.dp))
            CodeChip(
                value = code[index],
                focusRequester = focusRequesters[index],
                fontFamily = fontFamily,
                onValueChange = { newValue ->
                    if (newValue.length <= 1 && (newValue.isEmpty() || newValue.all { it.isDigit() })) {
                        code = code.toMutableList().apply { this[index] = newValue }
                        onCodeChange(code.joinToString(""))
                        if (newValue.isNotEmpty() && index < length - 1) {
                            focusRequesters[index + 1].requestFocus()
                        }
                    }
                },
            )
        }
    }
}

/**
 * The "Didn't get the code? Send another code" row used below the OTP
 * boxes on code-login/verification screens.
 */
@Composable
fun ResendCodeRow(
    onResendClick: () -> Unit,
    modifier: Modifier = Modifier,
    fontFamily: FontFamily = fredokaFontFamily(),
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(Res.string.didnt_get_code),
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            color = AppColors.FieldLabel,
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = stringResource(Res.string.send_another_code),
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            color = AppColors.FieldLabel,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.clickable(onClick = onResendClick),
        )
    }
}