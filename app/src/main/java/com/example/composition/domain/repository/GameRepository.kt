package com.example.composition.domain.repository

import com.example.composition.domain.entites.GameSettings
import com.example.composition.domain.entites.Level
import com.example.composition.domain.entites.Question

interface GameRepository {

    fun generateQuestion(
        maxSumValue: Int,
        countOfOptions: Int
    ): Question

    fun getGameSettings(level: Level): GameSettings
}