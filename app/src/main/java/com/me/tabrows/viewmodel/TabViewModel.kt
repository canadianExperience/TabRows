package com.me.tabrows.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TabViewModel: ViewModel() {

    private val initialUrl = "https://google.ca"
    private val _urlState = MutableStateFlow(initialUrl)
    val urlState get() = _urlState.asStateFlow()

    fun onUrlChange(url: String) = viewModelScope.launch(Dispatchers.Default) {
            _urlState.emit(url)
        }
}