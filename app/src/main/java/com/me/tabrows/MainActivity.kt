package com.me.tabrows

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import com.google.accompanist.pager.*
import com.me.tabrows.screens.TabsScreen
import com.me.tabrows.ui.theme.TabRowsTheme
import com.me.tabrows.viewmodel.TabViewModel

@ExperimentalLifecycleComposeApi
@ExperimentalPagerApi
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: TabViewModel by viewModels()

        Log.d("MY_VIEW_MODEL", "$viewModel")

        setContent {
            TabRowsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TabsScreen(viewModel = viewModel)
                }
            }
        }
    }
}
