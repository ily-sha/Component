package com.example.component.domain

import com.example.component.domain.entity.GameSettings
import com.example.component.domain.entity.Level
import com.example.component.domain.entity.Question

interface GameRepository {

    fun getGameSettings(
        level: Level
    ): GameSettings

    fun generateQuestion(
        maxSumValue: Int,
        countOfOptions: Int
    ): Question
}

