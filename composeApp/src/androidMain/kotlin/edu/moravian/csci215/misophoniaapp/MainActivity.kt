package edu.moravian.csci215.misophoniaapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import edu.moravian.csci215.misophoniaapp.survey_data.SurveyRepository

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        val repository = SurveyRepository(createSurveyDatabase(applicationContext).surveyResultDao())

        requestBLEPermissions()

        setContent {
            App(repository = repository)
        }
    }
    private fun requestBLEPermissions() {
        //S is 31 (asking if the build is Android 12+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {

            //These values are integers representing either PERMISSION_GRANTED (0) or PERMISSION_DENIED (-1)
            val scanPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN)
            val connectPermission = ContextCompat.checkSelfPermission(this,Manifest.permission.BLUETOOTH_CONNECT)

            //checks if either of these permissions were not granted
            if (scanPermission != PackageManager.PERMISSION_GRANTED || connectPermission != PackageManager.PERMISSION_GRANTED) {
                //requests that the listed permissions be granted. If they have not been granted to the app, the user gets a pop-up requesting permissions.
                requestPermissions(arrayOf(Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_CONNECT), 1)
            }
        }
    }
}