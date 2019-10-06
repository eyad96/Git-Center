package com.yazan98.git.center.logic.view

import com.yazan98.git.center.domain.models.User
import io.atto.android.view.AttoView

/**
 * Created By : Yazan Tarifi
 * Date : 9/18/2019
 * Time : 4:43 PM
 */

interface LoginView : AttoView {

    suspend fun onSuccessResponse(data: User)

    suspend fun onErrorResponse(error: String)

    suspend fun showLoading()

    suspend fun hideLoading()

}
