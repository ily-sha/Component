package com.example.component.presentation

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.component.R
import com.example.component.data.GameRepoImpl
import com.example.component.domain.entity.GameResult
import com.example.component.domain.entity.GameSettings
import com.example.component.domain.entity.Level
import com.example.component.domain.entity.Question
import com.example.component.domain.usecases.GenerateQuestionUseCase
import com.example.component.domain.usecases.GetGameSettingsUseCase

class GameViewModel(
    private val level: Level,
    private val application: Application
) : ViewModel() {


    private val repository = GameRepoImpl
    private val gameSettingsUseCase = GetGameSettingsUseCase(repository)
    private val generateQuestionUseCase = GenerateQuestionUseCase(repository)


    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    private val _formattedTime = MutableLiveData<String>()
    val formattedTime: LiveData<String>
        get() = _formattedTime


    private val _progressAnswer = MutableLiveData<String>()
    val progressAnswer: LiveData<String>
        get() = _progressAnswer

    private val _percentOfRightAnswer = MutableLiveData<Int>()
    val percentOfRightAnswer: LiveData<Int>
        get() = _percentOfRightAnswer

    private val _requiredCountOfRightAnswer = MutableLiveData<Boolean>()
    val requiredCountOfRightAnswer: LiveData<Boolean>
        get() = _requiredCountOfRightAnswer

    private val _requiredPercentOfRightAnswer = MutableLiveData<Boolean>()
    val requiredPercentOfRightAnswer: LiveData<Boolean>
        get() = _requiredPercentOfRightAnswer

    private val _minPercent = MutableLiveData<Int>()
    val minPercent: LiveData<Int>
        get() = _minPercent

    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult>
        get() = _gameResult


    private lateinit var gameSettings: GameSettings
    private lateinit var timer: CountDownTimer

    private var countOfRightAnswer = 0
    private var countOfAnswer = 0

    init {
        startGame()
    }

    private fun startGame() {
        this.gameSettings = gameSettingsUseCase(level)
        generateNewQuestion()
        startTimer()
        _requiredCountOfRightAnswer.value = false
        _requiredPercentOfRightAnswer.value = false
        _minPercent.value = gameSettings.minPercentOfRightAnswers
        updateProgress()
    }

    fun chooseAnswer(number: Int) {
        val rightAnswer = _question.value?.rightAnswer
        if (rightAnswer == number) countOfRightAnswer++
        countOfAnswer++
        generateNewQuestion()
        updateProgress()

    }


    private fun generateNewQuestion() {
        _question.value = generateQuestionUseCase(gameSettings.maxSumValue)
    }

    private fun startTimer() {
        val gameDuration = gameSettings.gameTimeSettings * MILLIS_IN_MINUTE
        timer = object : CountDownTimer(gameDuration, MILLIS_IN_MINUTE) {
            override fun onTick(p0: Long) {
                updateTimer(p0)
            }

            override fun onFinish() {
                finishTimer()
            }

        }
        timer.start()
    }

    private fun updateTimer(millisecondUntilFinished: Long) {
        var leftSeconds = millisecondUntilFinished / MILLIS_IN_MINUTE
        val leftMinutes = leftSeconds / SECONDS_IN_MINUTE
        leftSeconds -= leftMinutes * SECONDS_IN_MINUTE
        _formattedTime.value = String.format("%02d:%02d", leftMinutes, leftSeconds)
    }

    private fun finishTimer() {
        _gameResult.value = GameResult(
            countOfRightAnswer >= gameSettings.minCountOfRightAnswers,
            countOfRightAnswer,
            countOfAnswer,
            gameSettings
        )
        timer.cancel()

    }

    private fun updateProgressColor() {
        if (gameSettings.minCountOfRightAnswers <= countOfRightAnswer)
            _requiredCountOfRightAnswer.value = true
        if (gameSettings.minPercentOfRightAnswers <= calculatePercentOfRightAnswer())
            _requiredPercentOfRightAnswer.value = true

    }

    private fun calculatePercentOfRightAnswer() =
        ((countOfRightAnswer / countOfAnswer.toDouble()) * 100).toInt()

    private fun updateProgress() {
        val progressAnswerString = application.resources.getString(R.string.progress_answers)
        _progressAnswer.value = String.format(
            progressAnswerString,
            countOfRightAnswer,
            gameSettings.minCountOfRightAnswers
        )
        _percentOfRightAnswer.value = calculatePercentOfRightAnswer()
        updateProgressColor()
    }

    companion object {
        const val MILLIS_IN_MINUTE = 1000L
        const val SECONDS_IN_MINUTE = 60L
    }


}