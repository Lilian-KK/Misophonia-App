package edu.moravian.csci215.misophoniaapp.server_data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.flow.first
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class UserCreate (
    val login_credentials: LoginCredentials,
    val auth_method: String
)

//NOTE: this is the new flow, where phone number is the unique key and we don't care about the name (vanity plate)
@Serializable
data class LoginCredentials (
    // constraints: min_length=10, max_length=10, all numbers--and once input, if no +1 for country code, add it
    val username: String,
    // constraints: min_length=8, max_length=72
    val password: String
)

@Serializable
data class LoginResponse (
    val access_token: String,
    val refresh_token: String,
    val token_type: String = "Bearer",
    val expires_in: Int = 900
)

@Serializable
data class AccesssTokenPayload (
    //userID is actually: uuid.UUID = Field(alias="sub")
    val user_id: String,
    val scope: String,
    val exp: Int,
    val model_config: Map<String, Boolean> = mapOf("populate_by_name" to true)
)

@Serializable
data class RefreshTokenPayload (
    val token_raw: String,
    val token_hash: String,
    val expires_at: LocalDateTime
)

suspend fun createAccount(
    client: HttpClient,
    phoneNumber: String,
    name: String,
    password: String
) {
    println(phoneNumber)
    println(password)
    println(name)
    val response = client.post("user") {
        contentType(ContentType.Application.Json)

        setBody(
            UserCreate(
                login_credentials = LoginCredentials(
                    username = phoneNumber,
                    password = password ),
                //todo: what should authMethod value look like? (ok liam says is old vers of phone number)
                auth_method = ""
            )
        )
    }

    println("for create user: " + response.status)
    val responseJson = response.body<LoginResponse>()
    println("for create user refresh: " + responseJson.refresh_token)
    println("for create user access: " + responseJson.access_token)
}

//had private applied to it
suspend fun logIn(
    client: HttpClient,
    phoneNumber: String,
    password: String,
): LoginResponse = client.post("login") {
        setBody(
            LoginCredentials(
                username = phoneNumber,
                password = password
            )
        )
    }.body()

//.body<TokenResponse>().token (when returning just the token as a string)

fun loadTokens() {

}

fun refreshTokens() {

}

fun saveNewTokens() {

}

fun markAsSkipped() {

}

object AuthAPI {
    suspend fun loadTokens(): BearerTokens? {
        val accessToken = TokenStorage.accessToken.first()
        val refreshToken = TokenStorage.refreshToken.first()

        return if (accessToken != null && refreshToken != null) {
            BearerTokens(accessToken, refreshToken)
        } else {
            null
        }
    }
}