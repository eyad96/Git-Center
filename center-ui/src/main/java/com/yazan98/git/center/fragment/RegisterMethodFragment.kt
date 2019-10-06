package com.yazan98.git.center.fragment

import android.os.Bundle
import android.view.View
import com.yazan98.git.center.R
import io.atto.ui.base.AttoFragment
import javax.inject.Inject

/**
 * Created By : Yazan Tarifi
 * Date : 9/29/2019
 * Time : 7:17 PM
 */

class RegisterMethodFragment @Inject constructor() : AttoFragment() {

    override fun getLayoutRes(): Int = R.layout.fragment_register_method
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}