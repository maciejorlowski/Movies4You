package com.maciej.movies4you.pages.appActivity.tv_shows.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.viewpager.widget.ViewPager
import com.inverce.mod.v2.core.IMEx
import com.maciej.movies4you.R
import com.maciej.movies4you.base.BaseAppFragment
import com.maciej.movies4you.functional.data.Constants
import com.maciej.movies4you.functional.data.MediaType
import com.maciej.movies4you.functional.data.SharedPrefs
import com.maciej.movies4you.functional.data.StandardListType
import com.maciej.movies4you.functional.picture.GlideImageLoader
import com.maciej.movies4you.functional.rxbus.RxBus
import com.maciej.movies4you.functional.rxbus.RxEvent
import com.maciej.movies4you.functional.viewModel
import com.maciej.movies4you.models.body.StandardListTypeBody
import com.maciej.movies4you.models.movies.MovieDetails
import com.maciej.movies4you.pages.appActivity.movieDetails.MovieDetailsFragmentArgs
import com.maciej.movies4you.pages.appActivity.movieDetails.MovieDetailsViewModel
import com.maciej.movies4you.pages.appActivity.movieDetails.addMovieToList.AddMovieToListDialog
import com.maciej.movies4you.pages.appActivity.movieDetails.detailsActors.MovieDetailsActorsFragment
import com.maciej.movies4you.pages.appActivity.movieDetails.detailsInfo.MovieDetailsInfoFragment
import com.maciej.movies4you.pages.appActivity.movieDetails.detailsReviews.MovieDetailsReviewsFragment
import com.maciej.movies4you.pages.appActivity.movieDetails.rating.RatingDialog
import com.maciej.movies4you.pages.appActivity.movieDetails.viewPager.MovieDetailsPagerAdapter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.app_fragment_movie_details.*

class TvShowDetailsFragment : BaseAppFragment() {
//
//    private val viewModel by viewModel<MovieDetailsViewModel>()
//    private val pagerListener: PagerListener = this::onChangeViewPager
//
//    private var mPager: ViewPager? = null
//    private lateinit var movieDetails: MovieDetails
//    private val args: MovieDetailsFragmentArgs by navArgs()
//
//
//    companion object {
//        const val NUM_PAGES = 3
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        viewModel.initialize(args.movieId)
//        return inflater.inflate(R.layout.app_fragment_movie_details, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        prepareTopBar()
//        setupObservables()
//        setupListeners()
//    }
//
//    private fun onChangeViewPager(position: Int): Fragment {
//        return when (position) {
//            0 -> MovieDetailsInfoFragment.newInstance(movieDetails)
//            1 -> MovieDetailsActorsFragment.newInstance(args.movieId)
//            else -> MovieDetailsReviewsFragment.newInstance(args.movieId)
//        }
//    }
//
//    private fun prepareTopBar() {
//        actions?.topBar()?.showBackArrow(true)
//        actions?.topBar()?.setTitle(getString(R.string.label_movie_details))
//    }
//
//    private fun setupViewPager() {
//        mPager = view?.findViewById(R.id.app_frag_movie_details_pager)
//        mPager?.adapter = MovieDetailsPagerAdapter(fragmentManager!!, pagerListener)
//    }
//
//    private fun setupObservables() {
//
//        viewModel.observableProgress.observe(this, Observer {
//            if (it == true) {
//                app_frag_movie_details_progress_bar.visibility = View.VISIBLE
//            } else {
//                app_frag_movie_details_progress_bar.visibility = View.GONE
//            }
//        })
//
//        viewModel.observableErrorMessage.observe(this, Observer {
//            if (!it.isNullOrEmpty()) {
//                Toast.makeText(IMEx.context, it, Toast.LENGTH_SHORT).show()
//            }
//        })
//
//        viewModel.observableDetails.observe(this, Observer {
//            if (it != null) {
//                movieDetails = it
//                prepareViews()
//                if (mPager == null) {
//                    setupViewPager()
//                    app_frag_movie_details_tab_layout.setupWithViewPager(mPager)
//                    app_frag_movie_details_tab_layout.setTitlesAtTabs(resources.getStringArray(R.array.movie_details_tab_layout_names).toList())
//                }
//            }
//        })
//
//        viewModel.observableFavorite.observe(this, Observer {
//            app_frag_movie_details_hearth_icon.isActivated = it
//        })
//
//        viewModel.observableWatched.observe(this, Observer {
//            app_frag_movie_details_movie_watched.isChecked = it
//        })
//
//        app_frag_movie_details_rating_bar.setOnTouchListener { _, _ ->
//            val dialog = RatingDialog.newInstance(viewModel.rate)
//            dialog.setTargetFragment(this@MovieDetailsFragment, Constants.DIALOG_CODE)
//            dialog.show(requireFragmentManager(), "dialog")
//            false
//        }
//
//        app_frag_movie_details_movie_watched.setOnCheckedChangeListener { _, isChecked ->
//            if (SharedPrefs.getTmdbUserLogged()) {
//                viewModel.addToStandardList(
//                    StandardListTypeBody(
//                        MediaType.Movie.type,
//                        args.movieId,
//                        watchlist = isChecked
//                    ), StandardListType.Watched.queryName
//                )
//            } else {
//                app_frag_movie_details_movie_watched.isChecked = false
//                actionWithoutPermission()
//            }
//        }
//    }
//
//    private fun prepareViews() {
//        GlideImageLoader.loadImage(
//            app_frag_movie_details_image,
//            Constants.Urls.IMAGES_URL + movieDetails.backdropPath,
//            roundCorners = 15
//        )
//
//        app_frag_movie_details_movie_title.text = movieDetails.title
//        app_frag_movie_details_rating_text.text = movieDetails.popularity.toString()
//        app_frag_movie_details_vote_count.text =
//            context?.getString(R.string.movie_details_count_people_vote, movieDetails.voteCount)
//        app_frag_movie_details_rating_bar.rating = (movieDetails.voteAverage / 2)
//    }
//
//    private fun setupListeners() {
//        app_frag_movie_details_hearth_icon.setOnClickListener {
//            if (SharedPrefs.getTmdbUserLogged()) {
//                viewModel.addToStandardList(
//                    StandardListTypeBody(
//                        MediaType.Movie.type,
//                        args.movieId,
//                        favorite = !app_frag_movie_details_hearth_icon.isActivated
//                    ),
//                    StandardListType.Favorite.queryName
//                )
//            } else {
//                actionWithoutPermission()
//            }
//        }
//
//        app_frag_movie_details_plus_icon.setOnClickListener {
//            if (SharedPrefs.getTmdbUserLogged()) {
//                AddMovieToListDialog.newInstance(movieDetails.movieId)
//            } else {
//                actionWithoutPermission()
//            }
//        }
//    }
//
//    private fun actionWithoutPermission() {
//        RxBus.publish(RxEvent.EventRequestNoPermission())
//    }
//
//    override fun onRatingMovie(rate: Float) {
//        viewModel.rateMovie(rate)
//    }
//
//    private operator fun CompositeDisposable.plusAssign(subscribe: Disposable) {
//        this.add(subscribe)
//    }
}