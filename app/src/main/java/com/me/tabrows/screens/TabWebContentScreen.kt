package com.me.tabrows.screens

import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.LoadingState
import com.google.accompanist.web.rememberWebViewNavigator
import com.google.accompanist.web.rememberWebViewState
import com.google.accompanist.web.WebView
import com.me.tabrows.components.ErrorComponent
import com.me.tabrows.components.LoadingComponent

@Composable
fun TabWebContentScreen(
    modifier: Modifier = Modifier,
    urlPreSelected: String,
    onUrlChange: (String) -> Unit
){
    val webViewState = rememberWebViewState(url = urlPreSelected)
    val webViewNavigator = rememberWebViewNavigator()

    val showLoading = (webViewState.loadingState is LoadingState.Loading)
    val showError = (webViewState.errorsForCurrentRequest.isNotEmpty())

    if(!showError) {
        Column {
            val webClient = remember {
                object : AccompanistWebViewClient() {

                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?
                    ): Boolean {
                        request?.let {
                            onUrlChange(it.url.toString())
                            Log.d("ACCOMPANIST_WEB_VIEW", "Page loaded ${it.url}")
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

                    Log.d("ACCOMPANIST_WEB_VIEW", "Created")
                },
                onDispose = {
                    Log.d("ACCOMPANIST_WEB_VIEW", "Disposed")
                },
                client = webClient
            )
        }
    }
    if(showLoading) {
        LoadingComponent()
    }
    if(showError) {
        ErrorComponent()
    }
}

@Preview
@Composable
fun TabWebContentScreenPreview(){
    TabWebContentScreen(
        urlPreSelected = "https://google.ca",
        onUrlChange = {}
    )
}
