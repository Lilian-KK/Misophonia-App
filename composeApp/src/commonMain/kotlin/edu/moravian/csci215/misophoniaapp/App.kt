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
import androidx.lifecycle.viewmodel.compose.viewModel
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
import edu.moravian.csci215.misophoniaapp.server_data.createAccount
import edu.moravian.csci215.misophoniaapp.server_data.logIn
import edu.moravian.csci215.misophoniaapp.survey_data.SurveyElement
import edu.moravian.csci215.misophoniaapp.survey_data.SurveyRepository
import edu.moravian.csci215.misophoniaapp.survey_data.SurveyType
import edu.moravian.csci215.misophoniaapp.survey_data.SurveyVM
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
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
    val snackbarHostState = remember { SnackbarHostState() }

    val httpClient = remember {
        HttpClient(CIO) {
            defaultRequest {
                url {
                    protocol = URLProtocol.HTTP
                    //nora note: this host is only for the android studio emulator!! change the ip for other devices
                    host = "10.0.2.2"
                    port = 8000
                }
            }
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }
    }

    val currentScreen = navBackStackEntry?.destination?.route
    println(currentScreen)
    val screensWithBottomBar = listOf(AppSettings.toString(), HeadphonesSettings.toString(), Hub.toString(), SurveyHistory.toString(), WifiNetworks.toString())
    println(screensWithBottomBar)
    val hasBottomBar = screensWithBottomBar.any { currentScreen?.contains(it) == true || currentScreen?.contains("ViewSurvey") == true}

    MaterialTheme {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            bottomBar = {
                if (hasBottomBar) {
                    BottomAppBar(
                        actions = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly,
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
                        onLogin = { phoneNumber: String, password: String ->
                            logIn(httpClient, phoneNumber, password)
                        },
                        toHub = { navController.navigate(Hub) },
                        onForgotPassword = { navController.navigate(ForgotPassword) },
                        showSnackbar = {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(it)
                            }
                        },
                        goBack =  { navController.navigateUp() }
                    )
                }
                composable<ForgotPassword> {
                    ForgotPasswordScreen(
                        onContinue = { navController.navigate(Hub) },
                        goBack =  { navController.navigateUp() }
                    )
                }
                composable<CreateAccount> {
                    CreateAccountScreen(
                        onCreateAccount = { phoneNumber: String, name: String, password: String ->
                            coroutineScope.launch {
                                createAccount(httpClient, phoneNumber, name, password)
                            }
                        },
                        toHub = { navController.navigate(Hub) },
                        goBack = { navController.navigateUp() }
                    )
                }
                composable<Hub> {
                    HubScreen { surveyType ->
                        navController.navigate(SurveyCompanion(surveyType))
                    }
                }
                composable<HeadphonesSettings> {
                    HeadphonesSettingsScreen {
                        navController.navigate(WifiNetworks)
                    }
                }
                composable<SurveyCompanion> { navBackStackEntry ->
                    val surveyType = navBackStackEntry.toRoute<SurveyCompanion>().surveyType
                    SurveyScreen(repository, surveyType) {
                        navController.navigate(SurveyHistory)
                    }
                }
                composable<SurveyHistory> {
                    SurveyHistoryScreen(repository) { surveyId: Long, surveyType: SurveyType ->
                        navController.navigate(ViewSurvey(surveyId, surveyType))
                    }
                }
                composable<ViewSurvey> { navBackStackEntry ->
                    val surveyId = navBackStackEntry.toRoute<ViewSurvey>().surveyId
                    val surveyType = navBackStackEntry.toRoute<ViewSurvey>().surveyType
                    ViewSurveyScreen(surveyId, surveyType, repository)
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