package com.maciej.movies4you.pages.appActivity.movieDetails.addMovieToList

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.inverce.mod.v2.core.IMEx
import com.inverce.mod.v2.core.onUi
import com.inverce.mod.v2.core.utils.Screen
import com.inverce.mod.v2.core.verification.isNotNullOrEmpty
import com.maciej.movies4you.R
import com.maciej.movies4you.base.BaseAppDialog
import com.maciej.movies4you.functional.applyArguments
import com.maciej.movies4you.functional.viewModel
import kotlinx.android.synthetic.main.dialog_add_movie_to_list.*
import kotlinx.android.synthetic.main.entry_fragment_login.*

class AddMovieToListDialog : BaseAppDialog() {

    private lateinit var listsAdapter: AddMovieToListAdapter
    private lateinit var listClickedListener: ListClickedListener

    private val viewModel by viewModel<AddMovieToListViewModel>()


    companion object {
        private const val MOVIE_ID = "movieId"
        private const val ANIM_DURATION = 300L


        fun newInstance(movieId: Int) =
            AddMovieToListDialog().applyArguments {
                putInt(MOVIE_ID, movieId)
            }.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.BaseDialogFullscreen)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_add_movie_to_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog_add_movie_to_list_content_container.maxHeight = Screen.screenSize.y / 2
        slideOnAnimation()
        setupListeners()
        setupAdapter()
    }

    private fun setupAdapter() {
        listsAdapter = AddMovieToListAdapter(listClickedListener)
        dialog_add_movie_to_list_my_lists.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = listsAdapter
            setHasFixedSize(true)
        }
    }

    private fun setupListeners() {
        viewModel.observableLists.observe(viewLifecycleOwner, Observer {
            if (it.isNotNullOrEmpty()) {
                listsAdapter.update(it)
                dialog_add_movie_to_list_no_items.visibility = View.GONE
            } else {
                dialog_add_movie_to_list_no_items.visibility = View.VISIBLE
            }
        })

        viewModel.observableErrorMessage.observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                Toast.makeText(IMEx.context, it, Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.observableProgress.observe(this, Observer {
            if (it) {
                dialog_add_movie_to_list_loader.visibility = View.VISIBLE
            } else {
                dialog_add_movie_to_list_loader.visibility = View.GONE
            }
        })

        dialog_add_movie_to_list_close.setOnClickListener {
            dismiss()
        }

        listClickedListener = {
            viewModel.addMovieToList(it, arguments?.getInt(MOVIE_ID) ?: 0){success ->
                if(success){
                    Toast.makeText(context,R.string.tast_add_movie_to_list,Toast.LENGTH_SHORT).show()
                    dismiss()
                }
            }
        }

        listOf(dialog_add_movie_to_list_close, dialog_add_movie_to_list_background_shadow).forEach {
            it.setOnClickListener {
                dismiss()
            }
        }
    }

    private fun slideOffAnimation() {
        dialog_add_movie_to_list_background_shadow.animate()
            .alpha(0f)
            .setDuration(ANIM_DURATION)
            .start()

        dialog_add_movie_to_list_container.animate()
            .y(Screen.screenSize.y.toFloat())
            .setDuration(ANIM_DURATION)
            .start()
    }

    private fun slideOnAnimation() {
        dialog_add_movie_to_list_background_shadow.animate()
            .alpha(1f)
            .setDuration(ANIM_DURATION)
            .start()

        dialog_add_movie_to_list_container.y = Screen.screenSize.y.toFloat()
        dialog_add_movie_to_list_container.animate()
            .translationX(0F)
            .y(0F)
            .setDuration(ANIM_DURATION)
            .start()
    }

    override fun dismiss() {
        slideOffAnimation()
        onUi(ANIM_DURATION) {
            super.dismiss()
        }
    }
}
