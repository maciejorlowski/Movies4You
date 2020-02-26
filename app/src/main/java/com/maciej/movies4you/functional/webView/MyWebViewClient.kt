package com.maciej.movies4you.functional.webView

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient
import com.inverce.mod.v2.core.IMEx
import com.inverce.mod.v2.core.activity
import com.maciej.movies4you.functional.data.Constants
import com.maciej.movies4you.pages.entryActivity.EntryActivity



class MyWebViewClient : WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        if (Uri.parse(url).host == Constants.Urls.REGISTER_URL) {
            // This is my web site, so do not override; let my WebView load the page
            return false
        }



        val intent = Intent(IMEx.context, EntryActivity::class.java)
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
        IMEx.context.startActivity(intent)
        activity?.finish()
        return true
    }
}
