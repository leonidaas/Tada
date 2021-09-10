package de.leonfuessner.tada.extensions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun ViewModel.onIO(body: suspend () -> Unit) {
    viewModelScope.launch(Dispatchers.IO) {
        body()
    }
}

fun ViewModel.onDefault(body: suspend () -> Unit) {
    viewModelScope.launch(Dispatchers.Default) {
        body()
    }
}

fun ViewModel.onMain(body: suspend () -> Unit) {
    viewModelScope.launch(Dispatchers.Main) {
        body()
    }
}