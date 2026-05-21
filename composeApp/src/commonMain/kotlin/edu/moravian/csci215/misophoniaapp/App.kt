package edu.moravian.csci215.misophoniaapp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
                        Row(
                            horizontalArrangement = Arrangement.Center
                        ) {
                            IconButton({ navController.navigate(Hub) }) {
                                Icon(
                                    painterResource(Res.drawable.home_button),
                                    "The home button",
                                    Modifier.size(35.dp),
                                    Color.White
                                )
                            }
                            IconButton({ navController.navigate(HeadphonesSettings) }) {
                                Icon(
                                    painterResource(Res.drawable.headphones),
                                    "The headphones settings button",
                                    Modifier.size(35.dp),
                                    Color.White
                                )
                            }
                            IconButton({ navController.navigate(ViewSurvey) }) {
                                Icon(
                                    painterResource(Res.drawable.view_history),
                                    "The trigger survey history button",
                                    Modifier.size(35.dp),
                                    Color.White
                                )
                            }
                            IconButton({ navController.navigate(AppSettings) }) {
                                Icon(
                                    painterResource(Res.drawable.settings),
                                    "The app settings button",
                                    Modifier.size(35.dp),
                                    Color.White
                                )
                            }
                        }
                    },
                    containerColor = Color.Red
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