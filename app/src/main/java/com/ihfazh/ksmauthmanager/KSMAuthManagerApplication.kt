package com.ihfazh.ksmauthmanager

import android.app.Application
import io.ktor.http.ContentType

class KSMAuthManagerApplication: Application() {

    lateinit var compositionRoot : CompositionRoot

    override fun onCreate() {
        super.onCreate()
        compositionRoot = CompositionRoot(this)
    }
}