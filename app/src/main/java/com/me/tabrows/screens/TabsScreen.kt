package com.me.tabrows.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.me.tabrows.viewmodel.TabViewModel
import kotlinx.coroutines.launch

@ExperimentalLifecycleComposeApi
@ExperimentalPagerApi
@Composable
fun SetTabsScreen(
    viewModel: TabViewModel
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val urlState by remember { viewModel.urlState }.collectAsStateWithLifecycle(
        lifecycle = lifecycleOwner.lifecycle,
        minActiveState = Lifecycle.State.STARTED
    )

    TabsScreen(
        urlPreSelected = urlState,
        onUrlChange = viewModel::onUrlChange
    )
}

@ExperimentalPagerApi
@Composable
fun TabsScreen(
    modifier: Modifier = Modifier,
    urlPreSelected: String,
    onUrlChange: (String) -> Unit
) {
    val pagerState = rememberPagerState(pageCount = 3)
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
                    urlPreSelected = urlPreSelected,
                    onUrlChange = onUrlChange
                )
            }
        }
    }
}

@ExperimentalPagerApi
@Preview(showBackground = true)
@Composable
fun TabsScreenPreview(){
    TabsScreen(
        urlPreSelected = "https://google.ca",
        onUrlChange = {}
    )
}
