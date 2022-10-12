package com.example.composition.domain.usecase

import com.example.composition.domain.entites.GameSettings
import com.example.composition.domain.entites.Level
import com.example.composition.domain.repository.GameRepository

class GetGameSettingsUseCase(
    private val repository: GameRepository //экземпляр репозитория
) {
    operator fun invoke (level: Level): GameSettings {
        return repository.getGameSettings(level)
    }
}