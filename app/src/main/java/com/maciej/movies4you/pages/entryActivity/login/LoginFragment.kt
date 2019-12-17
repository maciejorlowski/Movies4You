package com.maciej.movies4you.pages.entryActivity.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.inverce.mod.v2.core.IMEx
import com.maciej.movies4you.functional.data.AppLanguage
import com.maciej.movies4you.R
import com.maciej.movies4you.functional.data.SharedPrefs
import com.maciej.movies4you.base.BaseEntryFragment
import com.maciej.movies4you.functional.database.MyDatabase
import com.maciej.movies4you.functional.utils.hideKeyboard
import com.maciej.movies4you.pages.appActivity.AppActivity
import kotlinx.android.synthetic.main.entry_fragment_login.*

class LoginFragment : BaseEntryFragment() {

    private lateinit var viewModel: LoginViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        return inflater.inflate(R.layout.entry_fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        setupObservables()
        setupSpinner()
    }

    private fun setupListeners() {


        frag_login_register.setOnClickListener {
            val navDirection = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(navDirection)
        }

        login_btn.setOnClickListener {
            view?.hideKeyboard()
            viewModel.loginUser(
                frag_login_input_login_input.text.toString(),
                frag_login_input_password_input.text.toString()
            ) {
                if (it) {
                    startAppActivity()
                }
            }
        }

        login_as_guest_btn.setOnClickListener {
            view?.hideKeyboard()
            viewModel.loginGuest {
                if (it) {
                    startAppActivity()
                }
            }
        }
    }

    private fun startAppActivity() {
//        val intent = Intent(context, AppActivity::class.java)
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        context?.startActivity(intent)
        startActivity(Intent(IMEx.activity, AppActivity::class.java))
        activity?.finish()
    }

    private fun setupObservables() {

        viewModel.observableProgress.observe(this, Observer {
            if (it) {
                frag_login_progress_bar.visibility = View.VISIBLE
            } else {
                frag_login_progress_bar.visibility = View.GONE
            }
        })

        viewModel.observableErrorMessage.observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                Toast.makeText(IMEx.context, it, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupSpinner() {
        frag_login_language_spinner.adapter =
            ArrayAdapter<AppLanguage>(
                IMEx.context,
                R.layout.spinner_language_item,
                AppLanguage.values()
            )

        for (v in AppLanguage.values()) {
            if (v.langCode == SharedPrefs.getLanguageCode()) {
                frag_login_language_spinner.setSelection(v.position)
                break
            }
        }

        frag_login_language_spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    //Ignore
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val languageCode = (parent?.getItemAtPosition(position) as AppLanguage).langCode
                    if (SharedPrefs.getLanguageCode() != languageCode) {
                        SharedPrefs.setLanguageCode(languageCode)
                        actions?.changeLanguage(languageCode)
                    }
                }
            }
    }


}