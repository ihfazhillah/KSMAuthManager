package com.ihfazh.ksmauthmanager.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.Serializable

@Serializable
data class LoginBody(val username: String, val password: String)

@Serializable
data class LoginResponse(val token: String)



class Client(
        private val httpClient: HttpClient,
    ) {

        private val baseUrl = "https://cms.ksatriamuslim.com"

      suspend fun login(body: LoginBody): LoginResponse {
            val response = httpClient.post("$baseUrl/auth-token/"){
                contentType(ContentType.Application.Json)
                setBody(body)
            }

          return response.body<LoginResponse>()
        }

    }
