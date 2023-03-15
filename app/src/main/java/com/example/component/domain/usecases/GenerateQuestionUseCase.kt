package com.example.component.domain.usecases

import com.example.component.domain.GameRepository
import com.example.component.domain.entity.Question

class GenerateQuestionUseCase(val gameRepository: GameRepository) {

    operator fun invoke(maxSumValue: Int): Question {
        return gameRepository.generateQuestion(maxSumValue, COUNT_OF_OPTIONS)
    }

    companion object {
        const val COUNT_OF_OPTIONS = 6
    }
}
