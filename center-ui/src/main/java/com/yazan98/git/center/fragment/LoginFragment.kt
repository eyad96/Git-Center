package com.yazan98.git.center.fragment

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.yazan98.git.center.R
import com.yazan98.git.center.domain.models.User
import com.yazan98.git.center.logic.ViewModel.LoginViewModel
import com.yazan98.git.center.logic.view.LoginView
import io.atto.android.state.DefaultState
import io.atto.ui.base.AttoFragment
import io.atto.ui.fragment.AttoRxFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created By : Yazan Tarifi
 * Date : 9/18/2019
 * Time : 4:38 PM
 */

class LoginFragment @Inject constructor() : AttoRxFragment<LoginView , DefaultState , LoginViewModel>() , LoginView {

    @Inject
    lateinit var controller: LoginViewModel
    override fun getLayoutRes(): Int = R.layout.fragment_login

    override fun getViewModel(): LoginViewModel {
        return controller
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        controller = ViewModelProvider(activity!!).get(LoginViewModel::class.java)
    }

    override fun initScreen() {
        controller.setView(this)
    }

    override fun onNewState(newState: DefaultState) {
        GlobalScope.launch {
            when (newState) {
                DefaultState.LOADING -> showLoading()
                DefaultState.EMPTY_RESULT -> hideLoading()
                DefaultState.FINISHED -> hideLoading()
            }
        }
    }

    override suspend fun onSuccessResponse(data: User) {
        withContext(Dispatchers.Main) {

        }
    }

    override suspend fun onErrorResponse(error: String) {
        withContext(Dispatchers.Main) {

        }
    }

    override suspend fun showLoading() {
        withContext(Dispatchers.Main) {

        }
    }

    override suspend fun hideLoading() {
        withContext(Dispatchers.Main) {

        }
    }

}
