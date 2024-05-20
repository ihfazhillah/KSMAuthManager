package com.ihfazh.ksmauthmanager

import android.accounts.AbstractAccountAuthenticator
import android.accounts.Account
import android.accounts.AccountAuthenticatorResponse
import android.accounts.AccountManager
import android.content.Context
import android.content.Intent
import android.os.Bundle

class Authenticator(private val context: Context) : AbstractAccountAuthenticator(context) {
    override fun editProperties(p0: AccountAuthenticatorResponse?, p1: String?): Bundle {
        TODO("Not yet implemented")
    }

    override fun addAccount(
        response: AccountAuthenticatorResponse?,
        accountType: String?,
        authTokenType: String?,
        requiredFeatures: Array<out String>?,
        options: Bundle?
    ): Bundle {
        // Called when user wants to login and add a new account to device
        val intent = Intent(context, MainActivity::class.java)
        intent.putExtra(MainActivity.ARG_ACCOUNT_TYPE, accountType)
        intent.putExtra(MainActivity.ARG_AUTH_TOKEN_TYPE, authTokenType)
        intent.putExtra(MainActivity.ARG_IS_ADDING_NEW_ACCOUNT, true)
        intent.putExtra(MainActivity.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response)

        val bundle = Bundle()
        bundle.putParcelable(AccountManager.KEY_INTENT, intent)
        return bundle
    }


    override fun getAuthToken(
        response: AccountAuthenticatorResponse?,
        account: Account?,
        authTokenType: String?,
        options: Bundle?
    ): Bundle {

        val accountManager = AccountManager.get(context)

        val token = accountManager.peekAuthToken(account, authTokenType)

        if (token.isNotEmpty()){
            val bundle = Bundle()
            bundle.putString(AccountManager.KEY_ACCOUNT_NAME, account?.name)
            bundle.putString(AccountManager.KEY_ACCOUNT_TYPE, account?.type)
            bundle.putString(AccountManager.KEY_AUTHTOKEN, token)
            return bundle
        }

        val intent = Intent(context, MainActivity::class.java)
        intent.putExtra(MainActivity.ARG_ACCOUNT_TYPE, account?.type)
        intent.putExtra(MainActivity.ARG_AUTH_TOKEN_TYPE, authTokenType)
        intent.putExtra(MainActivity.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response)

        val bundle = Bundle()
        bundle.putParcelable(AccountManager.KEY_INTENT, intent)
        return bundle
    }

    override fun getAuthTokenLabel(p0: String?): String {
        TODO("Not yet implemented")
    }

    override fun updateCredentials(
        p0: AccountAuthenticatorResponse?,
        p1: Account?,
        p2: String?,
        p3: Bundle?
    ): Bundle {
        TODO("Not yet implemented")
    }

    override fun hasFeatures(
        p0: AccountAuthenticatorResponse?,
        p1: Account?,
        p2: Array<out String>?
    ): Bundle {
        TODO("Not yet implemented")
    }

    override fun confirmCredentials(
        p0: AccountAuthenticatorResponse?,
        p1: Account?,
        p2: Bundle?
    ): Bundle {
        TODO("Not yet implemented")
    }
}