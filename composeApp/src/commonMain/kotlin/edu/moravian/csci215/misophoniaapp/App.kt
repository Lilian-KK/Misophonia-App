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
import androidx.compose.material3.*
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
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.juul.kable.Bluetooth
import com.juul.kable.Filter
import com.juul.kable.Peripheral
import com.juul.kable.Scanner
import com.juul.kable.characteristicOf
import com.juul.kable.peripheral
import edu.moravian.csci215.misophoniaapp.screens.AppSettings
import edu.moravian.csci215.misophoniaapp.screens.AppSettingsScreen
import edu.moravian.csci215.misophoniaapp.screens.CreateAccount
import edu.moravian.csci215.misophoniaapp.screens.CreateAccountScreen
import edu.moravian.csci215.misophoniaapp.screens.ForgotPassword
import edu.moravian.csci215.misophoniaapp.screens.ForgotPasswordScreen
import edu.moravian.csci215.misophoniaapp.screens.HeadphonesSettings
import edu.moravian.csci215.misophoniaapp.screens.HeadphonesSettingsScreen
import edu.moravian.csci215.misophoniaapp.screens.Hub
import edu.moravian.csci215.misophoniaapp.screens.HubScreen
import edu.moravian.csci215.misophoniaapp.screens.LogIn
import edu.moravian.csci215.misophoniaapp.screens.LoginScreen
import edu.moravian.csci215.misophoniaapp.screens.Setup
import edu.moravian.csci215.misophoniaapp.screens.SetupScreen
import edu.moravian.csci215.misophoniaapp.screens.SurveyCompanion
import edu.moravian.csci215.misophoniaapp.screens.SurveyHistory
import edu.moravian.csci215.misophoniaapp.screens.SurveyHistoryScreen
import edu.moravian.csci215.misophoniaapp.screens.SurveyScreen
import edu.moravian.csci215.misophoniaapp.screens.ViewSurvey
import edu.moravian.csci215.misophoniaapp.screens.ViewSurveyScreen
import edu.moravian.csci215.misophoniaapp.screens.WifiNetworks
import edu.moravian.csci215.misophoniaapp.screens.WifiNetworksScreen
import edu.moravian.csci215.misophoniaapp.survey_data.SurveyRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import misophoniaapp.composeapp.generated.resources.Res
import misophoniaapp.composeapp.generated.resources.app_settings_button
import misophoniaapp.composeapp.generated.resources.compose_multiplatform
import misophoniaapp.composeapp.generated.resources.headphones
import misophoniaapp.composeapp.generated.resources.home_button
import misophoniaapp.composeapp.generated.resources.hp_settings_button
import misophoniaapp.composeapp.generated.resources.settings
import misophoniaapp.composeapp.generated.resources.survey_history_button
import misophoniaapp.composeapp.generated.resources.view_history
import misophoniaapp.composeapp.generated.resources.wifi
import misophoniaapp.composeapp.generated.resources.wifi_settings_button
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
@Preview
fun App(repository: SurveyRepository) {
    val navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()
    val isSetup = false
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = navBackStackEntry?.destination?.route
    println(currentScreen)
    // todo make this less redundant/more efficient, add ViewSurveyScreen
    val screensWithBottomBar = listOf("edu.moravian.csci215.misophoniaapp.screens." + AppSettings.toString(), "edu.moravian.csci215.misophoniaapp.screens." + HeadphonesSettings.toString(), "edu.moravian.csci215.misophoniaapp.screens." + Hub.toString(), "edu.moravian.csci215.misophoniaapp.screens." + SurveyHistory.toString(), "edu.moravian.csci215.misophoniaapp.screens." + WifiNetworks.toString(), "edu.moravian.csci215.misophoniaapp.screens." + ViewSurvey.toString())
    println(screensWithBottomBar)

    MaterialTheme {
        Scaffold(
            bottomBar = {
                if (screensWithBottomBar.contains(currentScreen)) {
                    BottomAppBar(
                        actions = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton({ navController.navigate(Hub) }) {
                                    Icon(
                                        painterResource(Res.drawable.home_button),
                                        stringResource(Res.string.home_button),
                                        Modifier.size(35.dp),
                                        Color.White,
                                    )
                                }
                                IconButton({ navController.navigate(HeadphonesSettings) }) {
                                    Icon(
                                        painterResource(Res.drawable.headphones),
                                        stringResource(Res.string.hp_settings_button),
                                        Modifier.size(35.dp),
                                        Color.White,
                                    )
                                }
                                IconButton({ navController.navigate(SurveyHistory) }) {
                                    Icon(
                                        painterResource(Res.drawable.view_history),
                                        stringResource(Res.string.survey_history_button),
                                        Modifier.size(35.dp),
                                        Color.White,
                                    )
                                }
                                IconButton({ navController.navigate(AppSettings) }) {
                                    Icon(
                                        painterResource(Res.drawable.settings),
                                        stringResource(Res.string.app_settings_button),
                                        Modifier.size(35.dp),
                                        Color.White,
                                    )
                                }
                                IconButton({ navController.navigate(WifiNetworks) }) {
                                    Icon(
                                        painterResource(Res.drawable.wifi),
                                        stringResource(Res.string.wifi_settings_button),
                                        Modifier.size(35.dp),
                                        Color.White,
                                    )
                                }
                            }
                        },
                        containerColor = Color.Red,
                    )
                }
            },
        ) {
            NavHost(
                navController,
                startDestination = if (isSetup) Hub else Setup,
            ) {
                composable<Setup> {
                    SetupScreen(
                        onLogin = { navController.navigate(LogIn) },
                        onCreateAccount = { navController.navigate(CreateAccount) },
                    )
                }
                composable<LogIn> {
                    LoginScreen(
                        onLogin = { navController.navigate(Hub) },
                        onForgotPassword = { navController.navigate(ForgotPassword) },
                    )
                }

                composable<ForgotPassword> {
                    ForgotPasswordScreen(
                        onContinue = { navController.navigate(Hub) },
                    )
                }
                composable<CreateAccount> {
                    CreateAccountScreen(
                        onCreateAccount = { phoneNumber, firstName, lastName, password ->
                            // Handle account creation logic here
                            navController.navigate(Hub)
                        },
                    )
                }
                composable<Hub> {
                    HubScreen {
                        // this one will need specific params for which survey, just basic now
                        navController.navigate(SurveyCompanion)
                    }
                }
                composable<HeadphonesSettings> {
                    HeadphonesSettingsScreen {
                        navController.navigate(WifiNetworks)
                    }
                }
                composable<SurveyCompanion> {
                    SurveyScreen(repository) {
                        navController.navigate(SurveyHistory)
                    }
                }
                composable<SurveyHistory> {
                    SurveyHistoryScreen(repository) { surveyId: Long ->
                        navController.navigate(ViewSurvey(surveyId))
                    }
                }
                composable<ViewSurvey> { navBackStackEntry ->
                    val surveyId = navBackStackEntry.toRoute<ViewSurvey>().surveyId
                    ViewSurveyScreen(surveyId, repository)
                }
                composable<AppSettings> {
                    AppSettingsScreen()
                }
                composable<WifiNetworks> {
                    WifiNetworksScreen()
                }
            }
        }
    }
}