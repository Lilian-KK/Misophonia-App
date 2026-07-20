package edu.moravian.csci215.misophoniaapp.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.juul.kable.Bluetooth
import com.juul.kable.Filter
import com.juul.kable.Peripheral
import com.juul.kable.Scanner
import com.juul.kable.characteristicOf
import com.juul.kable.logs.Logging
import com.juul.kable.logs.SystemLogEngine
import kotlinx.coroutines.flow.first
import kotlinx.serialization.Serializable
import misophoniaapp.composeapp.generated.resources.Res
import misophoniaapp.composeapp.generated.resources.create_account
import misophoniaapp.composeapp.generated.resources.login
import misophoniaapp.composeapp.generated.resources.welcome_message
import org.jetbrains.compose.resources.stringResource

@Serializable
data object Setup

//this is the strokes used to draw the headphones logo?
private const val HEADPHONES_PATH =
    "M31.25 187.5V125C31.25 100.136 41.1272 76.2903 58.7087 58.7088C76.2903 41.1272 " +
        "100.136 31.25 125 31.25C149.864 31.25 173.71 41.1272 191.291 58.7088C208.873 76.2903 " +
        "218.75 100.136 218.75 125V187.5M218.75 197.917C218.75 203.442 216.555 208.741 " +
        "212.648 212.648C208.741 216.555 203.442 218.75 197.917 218.75H187.5C181.975 218.75 " +
        "176.676 216.555 172.769 212.648C168.862 208.741 166.667 203.442 166.667 197.917V166.667C" +
        "166.667 161.141 168.862 155.842 172.769 151.935C176.676 148.028 181.975 145.833 " +
        "187.5 145.833H218.75V197.917ZM31.25 197.917C31.25 203.442 33.4449 208.741 37.3519 " +
        "212.648C41.2589 216.555 46.558 218.75 52.0833 218.75H62.5C68.0253 218.75 73.3244 " +
        "216.555 77.2314 212.648C81.1384 208.741 83.3333 203.442 83.3333 197.917V166.667C" +
        "83.3333 161.141 81.1384 155.842 77.2314 151.935C73.3244 148.028 68.0253 145.833 " +
        "62.5 145.833H31.25V197.917Z"

@Composable
fun SetupScreen(
    onLogin: () -> Unit,
    onCreateAccount: () -> Unit,
) {
    val headphonesPath = remember {
        PathParser().parsePathString(HEADPHONES_PATH).toPath()
    }

    //add check that ble permissions have been granted
    LaunchedEffect(Unit) {
        scanKable()
    }

    Column(
        modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(Res.string.welcome_message),
            fontSize = 36.sp,
            fontWeight = FontWeight.Normal,
            lineHeight = 44.sp,
            textAlign = TextAlign.Center,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(30.dp))

        Canvas(modifier = Modifier.size(250.dp)) {
            val scaleX = size.width / 250f
            val scaleY = size.height / 250f
            scale(scaleX, scaleY, pivot = Offset.Zero) {
                drawPath(
                    path = headphonesPath,
                    color = Color(0xFF1E1E1E),
                    style = Stroke(
                        width = 13f,
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(162.dp))

        Button(
            onClick = onLogin,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(100.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF6750A4),
                contentColor = Color.White
            )
        ) {
            Text(
                text = stringResource(Res.string.login),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                lineHeight = 24.sp,
                letterSpacing = 0.15.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onCreateAccount,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(100.dp),
            colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6750A4),
                    contentColor = Color.White,
                ),
        ) {
            Text(
                text = stringResource(Res.string.create_account),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                lineHeight = 24.sp,
                letterSpacing = 0.15.sp
            )
        }
    }
}

//todo:
//filter out unnamed devices, show the user a list of all available peripherals to select
//nimBLE
//let them connect to one, once they connect to it display information (characteristics?) about it
//also put this in a separate bluetooth/wifi folder
suspend fun scanKable() {
    println("proof that testingkable is running")
    val advertisement = Scanner {
        filters {
            match {
                println("before services created")
                services = listOf(Bluetooth.BaseUuid + 0x180F) //battery service
                println("these are the services" + services)
            }
            match {
                Filter.Name.Exact("nimBLE")
            }
        }
        logging {
            engine = SystemLogEngine
            level = Logging.Level.Warnings
            format = Logging.Format.Multiline
        }
    }.advertisements.first()
//    val advertisement = Scanner().advertisements.first()
    //note for future nora: it pauses here
    println("the advertisement is: " + advertisement)

    println("before peripheral created")
    val peripheral = Peripheral(advertisement) { }
    peripheral.connect()
    println("peripheral connected")

    println("before batterydata connected")
    val batteryData = peripheral.read(characteristicOf("0x180F", "0x2A19")) //the battery level characteristic
    println("Hey this is the battery data allegedly:" + batteryData)
}
