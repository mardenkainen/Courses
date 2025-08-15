package com.example.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    private val emailRegex = Pattern.compile("[a-zA-Z0-9+._%\\-]{1,256}@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+")
    private val _loginInfo = MutableStateFlow(LoginInfo("",""))
    val loginInfo = _loginInfo.asStateFlow()
    private val _loginButtonEnabled = MutableStateFlow(false)
    val loginButtonEnabled = _loginButtonEnabled.asStateFlow()

    init {
        viewModelScope.launch {
            _loginInfo.collect {
                _loginButtonEnabled.value = isLoginInfoValid(it)
            }
        }
    }

    fun setEmail(email: String) {
        _loginInfo.value = _loginInfo.value.copy(email = email)
    }

    fun setPassword(password: String) {
        _loginInfo.value = _loginInfo.value.copy(password = password)
    }

    private fun isLoginInfoValid(loginInfo: LoginInfo): Boolean {
        return loginInfo.password!="" && emailRegex.matcher(loginInfo.email).matches()
    }
}