package com.ihfazh.ksmauthmanager


import android.accounts.Account
import android.accounts.AccountAuthenticatorResponse
import android.accounts.AccountManager
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ihfazh.ksmauthmanager.screen.login.LoginScreen
import com.ihfazh.ksmauthmanager.screen.login.LoginViewModel
import com.ihfazh.ksmauthmanager.ui.theme.KSMAuthManagerTheme


// got deprecations and wont compile
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}

class MainActivity : ComponentActivity() {
    private var authenticatorResponse: AccountAuthenticatorResponse? = null


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        authenticatorResponse = intent.parcelable(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE)
        if (authenticatorResponse !== null){
            authenticatorResponse?.onRequestContinued()
        }

        setContent {
            KSMAuthManagerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LoginScreen(viewModel = viewModel(factory = LoginViewModel.Factory) ,
                         onLoginSuccess = {username, token -> onLoginSuccess(username, token)} , Modifier.padding(innerPadding))
                }
            }
        }
    }

    private fun onLoginSuccess(username: String, token: String){
        val accountManager = (application as KSMAuthManagerApplication).compositionRoot.accountManager

        // we save the account here
        val account = Account(username, VALUE_ACCOUNT_TYPE)
        // should add account first
        accountManager.addAccountExplicitly(account, "", null)
        accountManager.setAuthToken(account, VALUE_ACCOUNT_TYPE, token)
        val loginResponse = Bundle()

        loginResponse.putString(AccountManager.KEY_ACCOUNT_NAME, username)
        loginResponse.putString(AccountManager.KEY_ACCOUNT_TYPE, VALUE_ACCOUNT_TYPE)
        loginResponse.putString(AccountManager.KEY_AUTHTOKEN, token)
        authenticatorResponse?.onResult(loginResponse)

        val intentResult = Intent().apply {
            putExtras(loginResponse)
        }
        setResult(Activity.RESULT_OK, intentResult)
        finish()
    }

    companion object {
        const val ARG_ACCOUNT_TYPE = "arg account type"
        const val ARG_AUTH_TOKEN_TYPE = "arg auth token type"
        const val ARG_IS_ADDING_NEW_ACCOUNT = "arg is adding new account"
        const val KEY_ACCOUNT_AUTHENTICATOR_RESPONSE = "key account authenticator response"


        const val VALUE_ACCOUNT_TYPE = "com.ihfazh.ksmauthmanager"
    }
}
