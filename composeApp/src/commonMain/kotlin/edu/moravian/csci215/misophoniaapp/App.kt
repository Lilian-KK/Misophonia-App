package edu.moravian.csci215.misophoniaapp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.moravian.csci215.misophoniaapp.screens.AppSettings
import edu.moravian.csci215.misophoniaapp.screens.AppSettingsScreen
import edu.moravian.csci215.misophoniaapp.screens.HeadphonesSettings
import edu.moravian.csci215.misophoniaapp.screens.HeadphonesSettingsScreen
import edu.moravian.csci215.misophoniaapp.screens.Hub
import edu.moravian.csci215.misophoniaapp.screens.HubScreen
import edu.moravian.csci215.misophoniaapp.screens.Setup
import edu.moravian.csci215.misophoniaapp.screens.SetupScreen
import edu.moravian.csci215.misophoniaapp.screens.Survey
import edu.moravian.csci215.misophoniaapp.screens.SurveyScreen
import edu.moravian.csci215.misophoniaapp.screens.TriggerHistory
import edu.moravian.csci215.misophoniaapp.screens.TriggerHistoryScreen
import edu.moravian.csci215.misophoniaapp.screens.ViewSurvey
import edu.moravian.csci215.misophoniaapp.screens.ViewSurveyScreen
import org.jetbrains.compose.resources.painterResource

import misophoniaapp.composeapp.generated.resources.Res
import misophoniaapp.composeapp.generated.resources.compose_multiplatform
import misophoniaapp.composeapp.generated.resources.headphones
import misophoniaapp.composeapp.generated.resources.home_button
import misophoniaapp.composeapp.generated.resources.settings
import misophoniaapp.composeapp.generated.resources.view_history

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
@Preview
fun App() {
    val navController = rememberNavController()
    val isSetup = false

    MaterialTheme {
        Scaffold(
            bottomBar = {
                BottomAppBar(
                    actions = {
                        IconButton({ navController.navigate(Hub) }) {
                            painterResource(Res.drawable.home_button)
                        }
                        IconButton({ navController.navigate(HeadphonesSettings) }) {
                            painterResource(Res.drawable.headphones)
                        }
                        IconButton({ navController.navigate(ViewSurvey) }) {
                            painterResource(Res.drawable.view_history)
                        }
                        IconButton({ navController.navigate(AppSettings) }) {
                            painterResource(Res.drawable.settings)
                        }
                    }
                )
            }
        ){
            NavHost(
                navController,
                startDestination = if (isSetup) Hub else Setup
            ) {
                composable<Setup> {
                    SetupScreen() {
                        navController.navigate(Hub)
                    }
                }
                composable<Hub> {
                    HubScreen() {
                        //this one will need specific params for which survey, just basic now
                        navController.navigate(Survey)
                    }
                }
                composable<HeadphonesSettings> {
                    HeadphonesSettingsScreen()
                }
                composable<Survey> {
                    SurveyScreen() {
                        navController.navigate(TriggerHistory)
                    }
                }
                composable<TriggerHistory> {
                    TriggerHistoryScreen() {
                        navController.navigate(ViewSurvey)
                    }
                }
                composable<ViewSurvey> {
                    ViewSurveyScreen()
                }
                composable<AppSettings> {
                    AppSettingsScreen()
                }
            }
        }
    }
}