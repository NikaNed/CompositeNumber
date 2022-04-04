package com.example.composition.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.composition.domain.entites.Level
import java.lang.RuntimeException

class GameViewModelFactory(
    private val level: Level,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T { //создаем объект viewModel
        if (modelClass.isAssignableFrom(GameViewModel :: class.java)) { //делаем проверку,что пытаемся
            //получить именно объект viewModel, а ни какой-то другой
            return GameViewModel(level, application) as T
        }
            throw RuntimeException ("Unknown viewModel class $modelClass") //если с помощью этой
        //фабрики мы пытаемся получить viewModel другого класса, то бросаем исключение
    }
}