package com.ihfazh.ksmauthmanager.screen.login

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed interface LoginState

@Parcelize
data class IdleLoginState(
    val error: String?
): LoginState, Parcelable

@Parcelize
data object Submitting: LoginState, Parcelable

@Parcelize
data class LoginSuccess(val username: String, val token: String): LoginState, Parcelable