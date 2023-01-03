package com.me.tabrows.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.web.rememberWebViewNavigator
import com.google.accompanist.web.rememberWebViewState
import com.me.tabrows.components.TabsComponent
import com.me.tabrows.viewmodel.TabViewModel

@ExperimentalLifecycleComposeApi
@ExperimentalPagerApi
@Composable
fun TabsScreen(
    viewModel: TabViewModel
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val urlState by remember { viewModel.urlState }.collectAsStateWithLifecycle(
        lifecycle = lifecycleOwner.lifecycle,
        minActiveState = Lifecycle.State.STARTED
    )
    val pagerState = rememberPagerState(pageCount = 3)
    val webViewState = rememberWebViewState(url = urlState)
    val webViewNavigator = rememberWebViewNavigator()

    TabsComponent(
        pagerState = pagerState,
        webViewState = webViewState,
        webViewNavigator = webViewNavigator,
        onUrlChange = viewModel::onUrlChange
    )
}
