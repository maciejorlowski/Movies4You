package com.maciej.movies4you.pages.entryActivity.login

import com.maciej.movies4you.functional.data.Constants
import com.maciej.movies4you.functional.data.SharedPrefs
import com.maciej.movies4you.base.BaseViewModel
import com.maciej.movies4you.functional.database.MyDatabase
import com.maciej.movies4you.functional.rest.RestInterface
import com.maciej.movies4you.models.body.AuthTokenBody
import com.maciej.movies4you.models.body.SessionBody
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class LoginViewModel : BaseViewModel() {

    private var requestToken: String? = null

    @Inject
    lateinit var restInterface: RestInterface

    init {
        getRequestToken()
    }

    private fun getRequestToken() {
        subscription.add(restInterface.createRequestToken()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    requestToken = it.token
                }, { error ->
                    onRequestError(error)
                }
            ))
    }

    fun loginUser(username: String, password: String, done: (Boolean) -> Unit) {
        authorizeToken(username, password) {
            done(it)
        }
    }

    fun loginGuest(done: (Boolean) -> Unit) {
        subscription.add( restInterface.loginAsGuest()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { onRequestStart() }
            .doOnTerminate { onRequestExecute() }
            .subscribe(
                {
                    SharedPrefs.setGuestSessionId(it.sessionId)
                    SharedPrefs.setGuestSessionDuedate(it.duedate)
                    done(true)
                }, { error ->
                    onRequestError(error)
                    done(false)
                }
            ))
    }

    private fun authorizeToken(username: String, password: String, done: (Boolean) -> Unit) {
        subscription.add(
            restInterface.authorizeRequestToken(authTokenBody = AuthTokenBody(username, password, requestToken))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { onRequestStart() }
                .subscribe(
                    {
                        createSession { result ->
                            done(result)
                        }
                    }, { error ->
                        onRequestError(error)
                        done(false)
                    }
                ))
    }

    private fun createSession(done: (Boolean) -> Unit) {
        subscription.add(
            restInterface.createSession(sessionBody =  SessionBody(requestToken))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { onRequestStart() }
                .doOnTerminate { onRequestExecute() }
                .subscribe(
                    {
                        SharedPrefs.setSessionId(it.sessionId)
                        SharedPrefs.setTmdbUserLogged(true)
                        getUserDetails { result ->
                            done(result)
                        }
                    }, { error ->
                        onRequestError(error)
                        done(false)
                    }
                ))
    }

    private fun getUserDetails(done: (Boolean) -> Unit) {
        subscription.add(
            restInterface.getUserDetails()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { onRequestStart() }
                .doOnTerminate { onRequestExecute() }.subscribe(
                    {
                        MyDatabase.userDetailsDao.delete()
                        MyDatabase.userDetailsDao.insert(it)
                        done(true)
                    },
                    { error ->
                        onRequestError(error)
                        done(false)
                    }
                ))
    }

    override fun onRequestError(error: Throwable) {
        super.onRequestError(error)
        progressVisibility.value = false
    }
}