package com.maciej.movies4you.pages.appActivity.movieDetails.rating

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.dialog_rating.*
import android.widget.RatingBar.OnRatingBarChangeListener
import androidx.fragment.app.DialogFragment
import com.maciej.movies4you.R
import com.maciej.movies4you.base.BaseAppDialog


class RatingDialog : BaseAppDialog() {

    interface RatingMovieListener {
        fun onRatingMovie(rate: Float)
    }

    companion object {
        const val USER_RATE = "USER_RATE"

        fun newInstance(userRate: Float) = RatingDialog().apply {
            arguments = Bundle().apply {
                putFloat(USER_RATE, userRate)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_rating, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setClickListeners()
        prepareRatingBar()

    }

    private fun setClickListeners() {
        app_rating_dialog_cancel_btn.setOnClickListener { dismiss() }

        app_rating_dialog_ratingbar.onRatingBarChangeListener = OnRatingBarChangeListener { _, rating, _ ->
            app_rating_dialog_rate_text.text = getString(R.string.rating_dialog_vote_max_10, rating)
        }

        app_rating_dialog_accept_btn.setOnClickListener { sendRate() }
    }

    private fun prepareRatingBar() {
        app_rating_dialog_rate_text.text = getString(R.string.rating_dialog_vote_max_10, 1F)
        app_rating_dialog_ratingbar.rating = arguments?.getFloat(USER_RATE) ?: 1F
    }

    private fun sendRate() {
        val listener: RatingMovieListener = targetFragment as RatingMovieListener
        listener.onRatingMovie(app_rating_dialog_ratingbar.rating)
        dismiss()
    }
}