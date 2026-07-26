package edu.moravian.csci215.misophoniaapp.server_data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.flow.Flow

suspend fun createAccount(
    client: HttpClient,
    phoneNumber: String,
    code: String,
    username: String,
    password: String
): UserResponse {
    println(code)
    println(phoneNumber)
    println(password)
    println(username)
    val response = client.post("user") {
        contentType(ContentType.Application.Json)

        setBody(
            UserCreate(
                code = code,
                login_credentials = LoginCredentials(
                    username = username,
                    password = password ),
                phone = phoneNumber
            )
        )
    }

    println("for create user: " + response.status)
    val responseJson = response.body<LoginResponse>()
    println("for create user refresh: " + responseJson.refresh_token)
    println("for create user access: " + responseJson.access_token)

    return response.body()
}

suspend fun logIn(
    client: HttpClient,
    username: String,
    login_method: String,
    login_value: String,
): LoginResponse = client.post("login") {
    setBody(
        LoginRequest(
            username = username,
            login_method = login_method,
            login_value = login_value
        )
    )
}.body()

suspend fun logout(
    client: HttpClient,
    refreshToken: String
) {
    client.post("logout") {
        setBody(
            LogoutRequest(
                refresh_token = refreshToken
            )
        )
    }
}

suspend fun sendLoginCode(
    client: HttpClient,
    username: String
) {
    println("should be sending the login code")
    client.post("auth/send") {
        setBody(
            PhoneAuthPayload(
                username = username
            )
        )
    }
}

suspend fun sendRegisterCode(
    client: HttpClient,
    phoneNumber: String
) {
    println("should be sending the create account code")
    client.post("auth/send-initial") {
        setBody(
            InitialPhoneAuthRequest(
                phone_number = phoneNumber
            )
        )
    }
}

suspend fun refresh(
    client: HttpClient,
    refreshToken: String
): LoginResponse = client.post("refresh") {
        setBody(
            RefreshRequest(
                refresh_token = refreshToken
            )
        )
    }.body()

suspend fun changePassword(
    client: HttpClient,
    currentPassword: String,
    newPassword: String
) {
    client.post("change-pass") {
        setBody(
            ChangePasswordRequest(
                current_password = currentPassword,
                new_password = newPassword
            )
        )
    }
}

suspend fun resetPassword(
    client: HttpClient,
    username: String,
    code: String,
    newPassword: String
) {
    client.post("reset-pass") {
        setBody(
            ResetPasswordRequest(
                username = username,
                code = code,
                new_password = newPassword
            )
        )
    }
}

suspend fun changePhoneNumber(
    client: HttpClient,
    newPhoneNumber: String,
    code: String
): ChangePhoneResponse = client.post("change-phone") {
        setBody(
            ChangePhoneRequest(
                new_number = newPhoneNumber,
                code = code
            )
        )
    }.body()