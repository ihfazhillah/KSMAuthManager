package com.ihfazh.ksmauthmanager

import android.accounts.AccountManager
import android.content.Context
import com.ihfazh.ksmauthmanager.remote.Client
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class CompositionRoot(context: Context) {
    private val ktor = HttpClient(OkHttp) {

        install(HttpTimeout) {
            socketTimeoutMillis = 120_000
        }

        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                encodeDefaults = true
                isLenient = true
                allowSpecialFloatingPointValues = true
                allowStructuredMapKeys = true
                prettyPrint = false
                useArrayPolymorphism = false
            })
        }
    }

    val client = Client(ktor)

    val accountManager = AccountManager.get(context)

}