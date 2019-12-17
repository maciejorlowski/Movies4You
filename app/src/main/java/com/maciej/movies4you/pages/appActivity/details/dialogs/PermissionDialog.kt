package com.maciej.movies4you.pages.appActivity.details.dialogs

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.inverce.mod.v2.core.IMEx
import com.maciej.movies4you.R
import com.maciej.movies4you.base.BaseAppDialog
import com.maciej.movies4you.pages.entryActivity.EntryActivity
import kotlinx.android.synthetic.main.dialog_permission.*

class PermissionDialog : BaseAppDialog() {

    companion object {

        fun newInstance() = PermissionDialog()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_permission, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {

        dial_permission_login_action.setOnClickListener {
            val intent = Intent(IMEx.context, EntryActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
        dial_permission_button_close.setOnClickListener {
            dismiss()
        }
    }
}