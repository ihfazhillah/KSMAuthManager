package com.ihfazh.ksmauthmanager.screen.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier


typealias OnLoginSuccess = (username: String, token: String) -> Unit

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLoginSuccess: OnLoginSuccess,
    modifier: Modifier = Modifier
){
    val state = viewModel.state.collectAsState()


    Login(
        state = state.value,
        modifier = modifier,
        loginCallback = { username, password ->
            viewModel.onLoginClicked(username, password)
        }
    )

    LaunchedEffect(state.value){
        if (state.value is LoginSuccess){
            val value = state.value as LoginSuccess
            onLoginSuccess(value.username, value.token)
        }
    }
}

