package com.yazan98.git.center.logic.ViewModel

import androidx.lifecycle.MutableLiveData
import com.yazan98.git.center.data.repository.ProfileRepository
import com.yazan98.git.center.domain.models.User
import com.yazan98.git.center.domain.params.LoginUseCaseParams
import com.yazan98.git.center.domain.usecases.LoginUseCase
import com.yazan98.git.center.logic.view.LoginView
import io.atto.android.state.DefaultState
import io.atto.data.executors.AttoSingleExecutor
import io.atto.logic.AttoRxViewModel
import io.reactivex.Single
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created By : Yazan Tarifi
 * Date : 9/18/2019
 * Time : 4:44 PM
 */

class LoginViewModel @Inject constructor() : AttoRxViewModel<LoginView, DefaultState>() {

    val userObserver: MutableLiveData<User> by lazy { MutableLiveData<User>() }
    private val singleRequest: AttoSingleExecutor<Any> by lazy { AttoSingleExecutor<Any>() }
    private var profileRepository: ProfileRepository?
    private val useCase: LoginUseCase by lazy { LoginUseCase() }

    init {
        singleRequest.addHandler(this)
        profileRepository = ProfileRepository()
    }

    suspend fun login(username: String, password: String) {
        withContext(Dispatchers.IO) {
            try {
                useCase.validate(LoginUseCaseParams(username, password))
                acceptNewState(DefaultState.LOADING)
                profileRepository?.let {
                    executeRequest(useCase.execute(it.login(username, password)))
                }
            } catch (ex: Exception) {
                onError(ex as Throwable)
            }
        }
    }

    override fun executeRequest(request: Any) {
        GlobalScope.launch { executeBackRequest(request) }
    }

    private suspend fun executeBackRequest(request: Any) {
        withContext(Dispatchers.IO) {
            addRxRepository(singleRequest.subscribeRequest(request as Single<Any>))
        }
    }

    override fun onError(error: Throwable) {
        acceptNewState(DefaultState.EMPTY_RESULT)
        error.message?.let {
            GlobalScope.launch { getView().onErrorResponse(it) }
        }
    }

    override fun onSuccess(data: Any) {
        acceptNewState(DefaultState.FINISHED)
        GlobalScope.launch {
            getView().onSuccessResponse(data as User)
        }
    }

    override fun destroyViewModel() {
        super.destroyViewModel()
        singleRequest.destroyExecutor()
        profileRepository = null
    }

}
