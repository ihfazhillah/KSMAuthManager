package com.ihfazh.ksmauthmanager.screen.login

import android.accounts.Account
import android.accounts.AccountManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.ihfazh.ksmauthmanager.KSMAuthManagerApplication
import com.ihfazh.ksmauthmanager.MainActivity
import com.ihfazh.ksmauthmanager.remote.Client
import com.ihfazh.ksmauthmanager.remote.LoginBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class LoginViewModel(
    private val client: Client,
    private val accountManager: AccountManager
): ViewModel() {

    private var _state = MutableStateFlow<LoginState>(IdleLoginState(null))
    val state = _state.asStateFlow()

    fun onLoginClicked(username: String, password: String){
        _state.value = Submitting
        viewModelScope.launch(Dispatchers.IO){


            try {
                val response = client.login(LoginBody(username, password))

                _state.value = LoginSuccess(username, response.token)

            } catch (e: Exception){
                _state.value = IdleLoginState("Login Error")
            }

        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object: ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]) as KSMAuthManagerApplication
                return LoginViewModel(application.compositionRoot.client, application.compositionRoot.accountManager) as T
            }
        }
    }


}
