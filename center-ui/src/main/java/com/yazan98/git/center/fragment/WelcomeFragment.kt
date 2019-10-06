package com.yazan98.git.center.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.yazan98.git.center.R
import com.yazan98.git.center.adapter.OnBordingAdapter
import com.yazan98.git.center.domain.models.OnBordingModel
import io.atto.ui.base.AttoFragment
import kotlinx.android.synthetic.main.fragment_welcome.*
import javax.inject.Inject

/**
 * Created By : Yazan Tarifi
 * Date : 9/18/2019
 * Time : 4:28 PM
 */

class WelcomeFragment @Inject constructor() : AttoFragment() {
    override fun getLayoutRes(): Int = R.layout.fragment_welcome
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            WelcomeRecyclerView.apply {
                layoutManager = LinearLayoutManager(it , LinearLayoutManager.HORIZONTAL , false)
                adapter = OnBordingAdapter(arrayListOf(
                    OnBordingModel(R.drawable.boarding_1 , getString(R.string.on_boarding_one_title) , getString(R.string.on_boarding_one_des)),
                    OnBordingModel(R.drawable.boarding_2 , getString(R.string.on_boarding_two_title) , getString(R.string.on_boarding_two_des)),
                    OnBordingModel(R.drawable.boarding_3 , getString(R.string.on_boarding_three_title) , getString(R.string.on_boarding_three_des))
                    ))
                (adapter as OnBordingAdapter).context = it
            }
        }

        ContinueButton.setOnClickListener {

        }
    }
}