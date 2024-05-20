package com.ihfazh.ksmauthmanager.screen.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ihfazh.ksmauthmanager.ErrorMessage
import com.ihfazh.ksmauthmanager.ui.theme.KSMAuthManagerTheme


typealias LoginCallback = (username: String, password: String) -> Unit

@Composable
fun Login(modifier: Modifier = Modifier, state: LoginState, loginCallback: LoginCallback){
    val focusManager = LocalFocusManager.current


    var username by rememberSaveable {
        mutableStateOf("")
    }

    var password by rememberSaveable {
        mutableStateOf("")
    }

    val enabledButton = !(username.isEmpty() && password.isEmpty()) and  (state !is Submitting)

    Box(modifier = modifier.fillMaxWidth().fillMaxHeight(), contentAlignment = Alignment.Center){

        Column(modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {

            if ((state is IdleLoginState) && (state.error !== null)){
                ErrorMessage(state.error)
            }

            TextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                })
            )
            TextField(
                value = password, onValueChange = { password = it }, label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(autoCorrect = false, imeAction = ImeAction.Go),
                keyboardActions = KeyboardActions(
                    onGo = {
                        loginCallback.invoke(username, password)
                    }
                )
            )

            Button(onClick = {
                loginCallback.invoke(username, password)
            }, enabled = enabledButton) {
                if (state is Submitting){
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(MaterialTheme.typography.bodyMedium.fontSize.value.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                }

                Text("Login")
            }
        }

    }

}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    KSMAuthManagerTheme {
        Login(state = IdleLoginState(""), loginCallback = {_, _ -> })
    }
}