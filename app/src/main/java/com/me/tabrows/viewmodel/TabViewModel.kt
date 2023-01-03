package com.me.tabrows.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.accompanist.web.WebViewState
import com.google.accompanist.web.rememberWebViewNavigator
import com.google.accompanist.web.rememberWebViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TabViewModel: ViewModel() {

    private val _urlState = MutableStateFlow("https://google.ca")
    val urlState get() = _urlState.asStateFlow()


    fun onUrlChange(url: String) = viewModelScope.launch(Dispatchers.Default) {
            _urlState.emit(url)
        }

}