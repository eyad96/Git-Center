package com.yazan98.git.center.domain.usecases

import com.yazan98.git.center.domain.models.User
import com.yazan98.git.center.domain.params.LoginUseCaseParams
import io.atto.android.errors.InvalidValueException
import io.atto.android.rx.AttoRequestProvider
import io.atto.android.usecase.reactive.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created By : Yazan Tarifi
 * Date : 9/18/2019
 * Time : 4:39 PM
 */

class LoginUseCase @Inject constructor() : SingleUseCase<User, LoginUseCaseParams>() {

    override fun execute(request: Single<User>): Single<User> {
        return AttoRequestProvider<User>().getSingle(request)
    }

    @Throws(InvalidValueException::class)
    override fun validate(content: LoginUseCaseParams) {
        when {
            content.username.isEmpty() -> throw InvalidValueException("Username Required")
            content.password.isEmpty() -> throw InvalidValueException("Password Required")
        }
    }
}