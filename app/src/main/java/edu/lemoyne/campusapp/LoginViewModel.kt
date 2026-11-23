
package edu.lemoyne.campusapp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

data class LoginUiState(
    val loading: Boolean = false,
    val failed: Boolean = false
)

class LoginViewModel : ViewModel() {

    private val repository = ShowcaseRepository()

    private val _state = MutableStateFlow(LoginUiState())

    val state: StateFlow<LoginUiState> = _state

    fun login(
        email: String,
        password: String,
        onSuccess: (String) -> Unit
    ) {
        viewModelScope.launch {

            _state.value = LoginUiState(
                loading = true
            )

            try {
                val token = repository.signIn(
                    email = email,
                    password = password
                )

                _state.value = LoginUiState()

                onSuccess(token)

            } catch (error: CancellationException) {
                throw error

            } catch (error: Exception) {

                val httpCode = (error as? HttpException)?.code()

                Log.e(
                    "LoginDebug",
                    "HTTP status: ${httpCode ?: "N/A"}, " +
                            "error type: ${error.javaClass.simpleName}"
                )

                _state.value = LoginUiState(
                    failed = true
                )
            }
        }
    }
}

