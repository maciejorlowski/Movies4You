package com.maciej.movies4you.pages.entryActivity.register

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import com.maciej.movies4you.functional.data.Constants
import com.maciej.movies4you.functional.webView.MyWebViewClient
import com.maciej.movies4you.R
import com.maciej.movies4you.base.BaseEntryFragment
import kotlinx.android.synthetic.main.entry_fragment_register.*



class RegisterFragment : BaseEntryFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.entry_fragment_register, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun prepareWebView(){
        CookieManager.getInstance().setAcceptCookie(true)

        frag_register_webview.settings.javaScriptEnabled = true
        frag_register_webview.settings.builtInZoomControls = false
        frag_register_webview.settings.domStorageEnabled = true
        frag_register_webview.webViewClient = MyWebViewClient()
        frag_register_webview.loadUrl(Constants.Urls.REGISTER_URL)
    }
}