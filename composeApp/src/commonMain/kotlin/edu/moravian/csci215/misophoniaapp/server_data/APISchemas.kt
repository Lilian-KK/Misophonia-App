package edu.moravian.csci215.misophoniaapp.server_data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class UserCreate (
    val credentials: LoginCredentials,
    val authMethod: String
)

////NOTE: this is with current flow
//@Serializable
//data class LoginCredentials (
//    // constraints: min_length=3, max_length=32, pattern=r"^[a-zA-Z0-9_.-]+$"
//    val username: String,
//    // constraints: min_length=8, max_length=72
//    val password: String
//)

//NOTE: this is the new flow, where phone number is the unique key and we don't care about the name (vanity plate)
@Serializable
data class LoginCredentials (
    // constraints: min_length=10, max_length=10, all numbers--and once input, if no +1 for country code, add it
    val phoneNumber: String,
    // constraints: min_length=8, max_length=72
    val password: String
)

@Serializable
data class TokenResponse (
    val token: String,
    val tokenType: String = "Bearer",
    val scope: String,
    val expiresIn: Int
)

@Serializable
data class TokenPayload (
    //userID is actually: uuid.UUID = Field(alias="sub")
    val userID: String,
    val scope: String,
    val exp: Int,
    val model_config: Map<String, Boolean> = mapOf("populate_by_name" to true)
)

suspend fun createAccount(
    client: HttpClient,
    phoneNumber: String,
    name: String,
    password: String
) {
    client.post("user") {
        contentType(ContentType.Application.Json)

        setBody(
            UserCreate(
                credentials = LoginCredentials(
                    phoneNumber = phoneNumber,
                    password = password ),
                //todo: what should authMethod value look like?
                authMethod = ""
            )
        )
    }
}

suspend fun logIn(
    client: HttpClient,
    phoneNumber: String,
    password: String
) {
    return client.post("login") {
        contentType(ContentType.Application.Json)

        setBody(
            LoginCredentials(
                phoneNumber = phoneNumber,
                password = password
            )
        )
    }.body()
}
