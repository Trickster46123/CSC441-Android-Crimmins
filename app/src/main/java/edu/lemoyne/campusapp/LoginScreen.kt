
package edu.lemoyne.campusapp

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(
    state: LoginUiState,
    onLogin: (String, String) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var email by rememberSaveable {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    BackHandler {
        onBack()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                top = 48.dp,
                start = 16.dp,
                end = 16.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "My Interests Login",
            style = MaterialTheme.typography.headlineMedium
        )

        TextButton(onClick = onBack) {
            Text("Back")
        }

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            singleLine = true
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation()
        )

        Button(
            onClick = {
                onLogin(email.trim(), password)
            },
            enabled = !state.loading &&
                    email.isNotBlank() &&
                    password.isNotBlank()
        ) {
            Text("Login")
        }

        if (state.loading) {
            CircularProgressIndicator()
        }

        if (state.failed) {
            Text(
                text = "Login failed",
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}
