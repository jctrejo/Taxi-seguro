package com.seguro.taxis.utils.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.seguro.taxis.utils.state.LoginState
import com.seguro.taxis.utils.state.ScreenState
import com.seguro.taxis.ui.view.activity.login.LoginInteractor

class LoginViewModel(private val loginInteractor: LoginInteractor) : ViewModel(),
    LoginInteractor.OnLoginFinishedListener {

    private val _loginDataState= MutableLiveData<ScreenState<LoginState>>()
    val loginState: LiveData<ScreenState<LoginState>>
        get() = _loginDataState

    fun onLoginBtnClicked(username: String, password: String) {
        _loginDataState.value = ScreenState.Loading
        loginInteractor.login(username, password, this)
    }

    override fun onUsernameError() {
        _loginDataState.value = ScreenState.Render(LoginState.WRONG_USERNAME)
    }

    override fun onPasswordError() {
        _loginDataState.value = ScreenState.Render(LoginState.WRONG_PASSWORD)
    }

    override fun onSuccess() {
        _loginDataState.value = ScreenState.Render(LoginState.SUCCESS)
    }

    class LoginViewModelFactory(private val loginInteractor: LoginInteractor) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return LoginViewModel(loginInteractor) as T
        }
    }
}
