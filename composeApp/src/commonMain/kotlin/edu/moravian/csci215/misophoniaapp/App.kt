package edu.moravian.csci215.misophoniaapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
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
import edu.moravian.csci215.misophoniaapp.screens.*
import edu.moravian.csci215.misophoniaapp.screens.login.*
import edu.moravian.csci215.misophoniaapp.screens.registration.*
import edu.moravian.csci215.misophoniaapp.server_data.*
import edu.moravian.csci215.misophoniaapp.survey_data.*
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import misophoniaapp.composeapp.generated.resources.Res
import misophoniaapp.composeapp.generated.resources.*
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import responseValidator

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
@Preview
fun App(repository: SurveyRepository, tokenStorage: TokenStorage) {
    val navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    var isLoaded by remember {mutableStateOf<Boolean>(false)}
    var isLoggedIn by remember {mutableStateOf<Boolean>(false)}

    val httpClient = remember {
        HttpClient(CIO) {
            defaultRequest {
                url {
                    protocol = URLProtocol.HTTP
                    //nora note: this host is only for the android studio emulator!! change the ip for other devices
                    host = "10.0.2.2"
                    port = 8000
                }
                contentType(ContentType.Application.Json)
            }
            expectSuccess = true
            HttpResponseValidator { handleResponseExceptionWithRequest(::responseValidator) }
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
            install(Auth) {
                bearer {
                    sendWithoutRequest { true }
                    loadTokens { loadTokens(tokenStorage) }
//                    refreshTokens(refreshTokens) //todo
//                    refreshTokens {
//                        val response = client.post("http://10.0.2.2:8000") {
//                            markAsRefreshTokenRequest() // Prevents infinite loops
//                            setBody(RefreshTokenRequest(refreshToken = tokenStorage.refreshToken))
//                        }
//
//                        if (response.status == HttpStatusCode.OK) {
//                            val newTokens = response.body<LoginResponse>()
//
//                            // 3. Save the newly issued tokens to secure storage
//                            tokenStorage.storeTokens(newTokens.access_token, newTokens.refresh_token)
//
//                            // Return the new tokens so Ktor can automatically retry your original request
//                            BearerTokens(newTokens.access_token, newTokens.refresh_token)
//                        } else {
//                            null // Refresh failed, clear session or redirect to login
//                        }
//                    }
                    sendWithoutRequest { request ->
                        //todo: confirm with server-side which endpoints do not require a provided bearer token
                        val endpointsToSkip = listOf("/login", "/health")
                        // Do NOT send the header if targeting the auth/refresh endpoint
                        //!request.url.toString().contains("/login")
                        !endpointsToSkip.any { request.url.toString().contains(it) }
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        isLoggedIn = tokenStorage.refreshToken.first() != null
        println("isLoggedIn:" + tokenStorage.refreshToken.first())
        isLoaded = true
    }

    if (!isLoaded) {
        LoadingScreen()
        return
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
                startDestination = if (isLoggedIn == true) Hub else Setup
            ) {
                composable<Setup> {
                    SetupScreen(
                        onLogin = { navController.navigate(BaseLogin) },
                        onCreateAccount = { navController.navigate(CreateAccount) },
                    )
                }
                composable<BaseLogin> {
                    BaseLoginScreen(
                        showSnackbar = {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(it)
                            }
                        },
                        toCodeLogin = { username: String -> navController.navigate(CodeLogin(username)) },
                        toPasswordLogin = { username: String -> navController.navigate(PasswordLogin(username)) },
                        goBack = { navController.navigateUp() }
                    )
                }
                composable<CodeLogin> { navBackStackEntry ->
                    val username = navBackStackEntry.toRoute<CodeLogin>().username
                    CodeLoginScreen(
                        username = username,
                        goBack = { navController.navigateUp() },
                        storeTokens = { accessToken: String, refreshToken: String ->
                            coroutineScope.launch {
                                tokenStorage.storeTokens(accessToken, refreshToken)
                            }
                        },
                        showSnackbar = {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(it)
                            }
                        },
                        toHub = { navController.navigate(Hub) },
                        sendCode = { username: String ->
                            sendLoginCode(httpClient, username) },
                        onLogin = { username: String, login_method: String, login_value: String ->
                            logIn(httpClient, username, login_method, login_value)
                        }
                    )
                }
                composable<PasswordLogin> { navBackStackEntry ->
                    val username = navBackStackEntry.toRoute<PasswordLogin>().username
                    PasswordLoginScreen(
                        username = username,
                        goBack = { navController.navigateUp() },
                        storeTokens = { accessToken: String, refreshToken: String ->
                            coroutineScope.launch {
                                tokenStorage.storeTokens(accessToken, refreshToken)
                            }
                        },
                        showSnackbar = {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(it)
                            }
                        },
                        toHub = { navController.navigate(Hub) },
                        onLogin = { username: String, login_method: String, login_value: String ->
                            logIn(httpClient, username, login_method, login_value)
                        },
                    )
                }
                composable<CreateAccount> {
                    CreateAccountScreen(
                        showSnackbar = {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(it)
                            }
                        },
                        goBack = { navController.navigateUp() },
                        sendCode = { phoneNumber: String ->
                            sendRegisterCode(httpClient, phoneNumber)
                        },
                        toVerifyPhoneNumber = { phoneNumber: String, username: String, password: String ->
                            navController.navigate(VerifyPhoneNumber(phoneNumber, username, password))
                        }
                    )
                }
                composable<VerifyPhoneNumber> { navBackStackEntry ->
                    val phoneNumber = navBackStackEntry.toRoute<VerifyPhoneNumber>().phoneNumber
                    val username = navBackStackEntry.toRoute<VerifyPhoneNumber>().username
                    val password = navBackStackEntry.toRoute<VerifyPhoneNumber>().password
                    VerifyPhoneNumberScreen(
                        showSnackbar = {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(it)
                            }
                        },
                        goBack = { navController.navigateUp() },
                        toHub = { navController.navigate(Hub)},
                        createAccount = { code: String ->
                            createAccount(httpClient, phoneNumber, code, username, password)
                        },
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
                    AppSettingsScreen(
                        clearTokens = {
                            coroutineScope.launch {
                                tokenStorage.wipeTokens()
                            }
                        },
                        logOut = { logout(httpClient, tokenStorage.refreshToken.first().toString()) }, //keep an eye on this one make sure its storing properly
                        toSetup = { navController.navigate(Setup) }
                    )
                }
                composable<WifiNetworks> {
                    WifiNetworksScreen()
                }
            }
        }
    }
}