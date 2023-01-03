package com.me.tabrows.components

import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.WebView
import com.google.accompanist.web.WebViewNavigator
import com.google.accompanist.web.WebViewState

@Composable
fun WebViewComponent(
    modifier: Modifier = Modifier,
    webViewState: WebViewState,
    webViewNavigator: WebViewNavigator,
    onUrlChange: (String) -> Unit
){
    Column {
        val webClient = remember {
            object : AccompanistWebViewClient() {

                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    request?.let {
                        onUrlChange(it.url.toString())
                        Log.d("URL_LOADED", "Page loaded ${it.url}")
                    }
                    return super.shouldOverrideUrlLoading(view, request)
                }
            }
        }

        WebView(
            state = webViewState,
            modifier = modifier.weight(1f),
            navigator = webViewNavigator,
            onCreated = { webView ->
                webView.apply {
                    settings.javaScriptEnabled = true
                    // addJavascriptInterface()
                }
            },
            client = webClient
        )
    }
}