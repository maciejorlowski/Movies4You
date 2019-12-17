package com.maciej.movies4you.pages.appActivity.details.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.DialogFragment
import com.maciej.movies4you.R
import com.maciej.movies4you.base.BaseAppDialog
import kotlinx.android.synthetic.main.dialog_confirm.*

class ConfirmDialog : BaseAppDialog() {

    companion object {
        const val TITLE_RES = "TITLE_RES"
        const val DESC_RES = "DESC_RES"
        const val ICON_RES = "ICON_RES"


        fun newInstance(@StringRes titleRes: Int, @StringRes textRes: Int, @DrawableRes iconRes: Int) =
            ConfirmDialog().apply {
                arguments = Bundle().apply {
                    putInt(TITLE_RES, titleRes)
                    putInt(DESC_RES, textRes)
                    putInt(ICON_RES, iconRes)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_confirm, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareViews()
        setOnClickListeners()
    }

    private fun prepareViews() {
        dial_permission_title.text = getString(arguments?.getInt(TITLE_RES) ?: 0)
        dial_permission_description.text = getString(arguments?.getInt(DESC_RES) ?: 0)
        dial_permission_type_image.setImageDrawable(
            resources.getDrawable(
                arguments?.getInt(ICON_RES) ?: 0
            )
        )
    }

    private fun setOnClickListeners() {
        dial_permission_button_negative.setOnClickListener {
            val listener: ConfirmDialogResult = targetFragment as ConfirmDialogResult
            listener.onConfirmDialogResult(false)
            dismiss()
        }

        dial_permission_button_positive.setOnClickListener {
            val listener: ConfirmDialogResult = targetFragment as ConfirmDialogResult
            listener.onConfirmDialogResult(true)
            dismiss()
        }
    }


    interface ConfirmDialogResult {
        fun onConfirmDialogResult(value: Boolean)
    }
}