package com.me.tabrows.screens

import androidx.compose.runtime.Composable
import com.google.accompanist.web.*
import com.me.tabrows.components.ErrorComponent
import com.me.tabrows.components.LoadingComponent
import com.me.tabrows.components.WebViewComponent

@Composable
fun TabWebContentScreen(
    webViewState: WebViewState,
    webViewNavigator: WebViewNavigator,
    onUrlChange: (String) -> Unit
){
    val showLoading = (webViewState.loadingState is LoadingState.Loading)
    val showError = (webViewState.errorsForCurrentRequest.isNotEmpty())

    if(!showError) {
        WebViewComponent(
            webViewState = webViewState,
            webViewNavigator = webViewNavigator,
            onUrlChange = onUrlChange
        )
    }
    if(showLoading) {
        LoadingComponent()
    }
    if(showError) {
        ErrorComponent()
    }
}
