package com.example.component.domain.usecases

import com.example.component.domain.GameRepository
import com.example.component.domain.entity.GameSettings
import com.example.component.domain.entity.Level

class GetGameSettingsUseCase(val gameRepository: GameRepository) {

    operator fun invoke(level: Level): GameSettings {
        return gameRepository.getGameSettings(level)
    }
}