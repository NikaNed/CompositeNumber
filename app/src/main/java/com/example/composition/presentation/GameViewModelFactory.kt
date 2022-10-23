package com.example.composition.presentation

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.composition.domain.entites.GameResult
import com.example.composition.domain.entites.Level
import java.lang.RuntimeException

class GameViewModelFactory(
    private val level: Level,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel :: class.java)) {
            return GameViewModel(level, application) as T
        }
            throw RuntimeException ("Unknown viewModel class $modelClass")
    }
}