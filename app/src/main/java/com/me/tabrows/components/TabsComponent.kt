package com.me.tabrows.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.*
import com.google.accompanist.web.WebViewNavigator
import com.google.accompanist.web.WebViewState
import com.google.accompanist.web.rememberWebViewNavigator
import com.google.accompanist.web.rememberWebViewState
import com.me.tabrows.screens.TabContentScreen
import com.me.tabrows.screens.TabWebContentScreen
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@Composable
fun TabsComponent(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    webViewState: WebViewState,
    webViewNavigator: WebViewNavigator,
    onUrlChange: (String) -> Unit
) {
    val scope = rememberCoroutineScope()
    val list = listOf(
        "Home" to Icons.Default.Home,
        "Shopping" to Icons.Default.ShoppingCart,
        "WebView" to Icons.Default.Settings
    )
    Column(
        modifier = modifier.background(Color.White)
    ){
        Surface(
            color = Color.White,
            border = BorderStroke(width = 1.dp, color = Color.LightGray)
        ){
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                backgroundColor = Color.White,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(modifier = modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                        height = 5.dp,
                        color = Color.Red
                    )
                }
            ) {
                list.forEachIndexed { index, _ ->
                    Tab(
                        icon = {
                            Icon(
                                imageVector = list[index].second,
                                tint = if (pagerState.currentPage == index) Color.Red else Color.Black,
                                contentDescription = null
                            )
                        },
                        text = {
                            Text(
                                list[index].first,
                                color = if (pagerState.currentPage == index) Color.Red else Color.Black
                            )
                        },
                        selected = (pagerState.currentPage == index),
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                    )
                }
            }
        }
        HorizontalPager(
            state = pagerState
        ) { page ->
            when (page) {
                0 -> TabContentScreen(data = "Welcome to Home Screen")
                1 -> TabContentScreen(data = "Welcome to Shopping Screen")
                2 -> TabWebContentScreen(
                    webViewState = webViewState,
                    webViewNavigator = webViewNavigator,
                    onUrlChange = onUrlChange
                )
            }
        }
    }
}

@ExperimentalPagerApi
@Preview(showBackground = true)
@Composable
fun TabsComponentPreview(){
    val pagerState = rememberPagerState(pageCount = 3)
    val webViewState = rememberWebViewState(url = "https://google.ca")
    val webViewNavigator = rememberWebViewNavigator()
    TabsComponent(
        pagerState = pagerState,
        webViewState = webViewState,
        webViewNavigator = webViewNavigator,
        onUrlChange = {}
    )
}