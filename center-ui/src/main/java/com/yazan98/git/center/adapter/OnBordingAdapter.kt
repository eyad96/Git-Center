package com.yazan98.git.center.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.yazan98.git.center.R
import com.yazan98.git.center.domain.models.OnBordingModel
import io.atto.utils.ui.AttoBaseAdapter
import kotlinx.android.synthetic.main.row_on_bording.view.*
import javax.inject.Inject

/**
 * Created By : Yazan Tarifi
 * Date : 9/29/2019
 * Time : 6:38 PM
 */

class OnBordingAdapter @Inject constructor(private val data: ArrayList<OnBordingModel>) : AttoBaseAdapter<OnBordingAdapter.ViewHolder>() {
    override fun getItemCount(): Int = data.size
    override fun getLayoutRes(): Int = R.layout.row_on_bording
    override fun getViewHolder(view: View): ViewHolder = ViewHolder(view)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.image.setImageResource(data[position].icon)
        holder.name.text = data[position].name
        holder.description.text = data[position].description
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image = view.OnBoardingImage
        val name = view.OnBoardingTitle
        val description = view.OnBoardingDescription
    }

}