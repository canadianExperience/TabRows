package com.me.tabrows

import android.os.Bundle
import android.widget.TableLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.*
import com.me.tabrows.ui.theme.TabRowsTheme
import kotlinx.coroutines.launch

@ExperimentalPagerApi
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TabRowsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TabLayout()
                }
            }
        }
    }
}

@ExperimentalPagerApi
@Composable
fun TabLayout() {
    val pagerState = rememberPagerState(pageCount = 2)
    Column(
        modifier = Modifier.background(Color.White)
    ) {
        TopBar()
        Tabs(pagerState = pagerState)

        //Tab screen
        HorizontalPager(state = pagerState) { page ->
            when (page) {
                0 -> TabContentScreen(data = "Welcome to Home Screen")
                1 -> TabContentScreen(data = "Welcome to Shopping Screen")
            }
        }
    }
}

@Composable
fun TopBar(){
    TopAppBar(backgroundColor = Color.Red) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Tab Layout Example",
                style = TextStyle(color = Color.White),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(all = Dp(5F)),
                textAlign = TextAlign.Center
            )
        }
    }
}

@ExperimentalPagerApi
@Composable
fun Tabs(pagerState: PagerState) {
    val list = listOf(
        "Home" to Icons.Default.Home,
        "Shopping" to Icons.Default.ShoppingCart
    )
    val scope = rememberCoroutineScope()

    Surface(
        color = Color.White,
        border = BorderStroke(width = 1.dp, color = Color.LightGray)
    ){
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            divider = {
                Divider(modifier = Modifier.height(8.dp))
            },
            backgroundColor = Color.White,
//        contentColor = Color.Red,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(modifier = Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                    height = 8.dp,
                    color = Color.Red
                )
            }
        ) {
            list.forEachIndexed { index, _ ->
                Row(
                    modifier = Modifier.height(IntrinsicSize.Min)
                ){
                    if(index > 0){
                        Divider(
                            color = Color.Gray,
                            modifier = Modifier
                                .padding(top = 16.dp, bottom = 16.dp)
                                .fillMaxHeight()
                                .width(2.dp)
                        )
                    }
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
                        selected = pagerState.currentPage == index,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                    )

                }
            }
        }
    }
}

@Composable
fun TabContentScreen(data: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = data,
            style = MaterialTheme.typography.h5,
            color = Color.Blue,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@ExperimentalPagerApi
@Preview
@Composable
fun TableLayoutPreview(){
    TabLayout()
}