package com.example.composition.presentation

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.composition.R
import com.example.composition.data.GameRepositoryImpl
import com.example.composition.domain.entites.GameResult
import com.example.composition.domain.entites.GameSettings
import com.example.composition.domain.entites.Level
import com.example.composition.domain.entites.Question
import com.example.composition.domain.usecase.GenerateQuestionUseCase
import com.example.composition.domain.usecase.GetGameSettingsUseCase

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var level: Level
    private lateinit var gameSettings: GameSettings

    private var timer: CountDownTimer? = null

    private val context = application

    private val repository = GameRepositoryImpl

    private val generateQuestionUseCase = GenerateQuestionUseCase(repository)
    private val getGameSettingsUseCase = GetGameSettingsUseCase(repository)

    private val _formattedTime = MutableLiveData<String>()
    val formattedTime: LiveData<String> //отображение времени до конца игры
        get() = _formattedTime

    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question> //отображение вопросов и вариантов ответов, видимого числа
        get() = _question

    private val _percentOfRightAnswers = MutableLiveData<Int>()
    val percentOfRightAnswers: LiveData<Int> //отображаем процент правильных ответов
        get() = _percentOfRightAnswers

    private val _progressAnswers = MutableLiveData<String>()
    val progressAnswers: LiveData<String> //отображаем прогресс по ответам
        get() = _progressAnswers

    private val _enoughAnswers = MutableLiveData<Boolean>()
    val enoughAnswers: LiveData<Boolean> //отображаем достаточное количество правильных ответов
        get() = _enoughAnswers

    private val _enoughPercent = MutableLiveData<Boolean>()
    val enoughPercent: LiveData<Boolean> //отображаем достаточный процент правильных ответов
        get() = _enoughPercent

    private val _minPercent = MutableLiveData<Int>()
    val minPercent: LiveData<Int> //отображаем минимальный процент правильных ответов
        get() = _minPercent

    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult> //отображаем результат игры
        get() = _gameResult

    private var countOfRightAnswers = 0
    private var countOfQuestion = 0

    fun startGame(level: Level) {
        getGameSettings(level)
        startTimer()
        generateQuestion()
        updateProgress()
    }

    private fun getGameSettings(level: Level) {
        this.level = level
        this.gameSettings = getGameSettingsUseCase(level)
        _minPercent.value = gameSettings.minPercentOfRightAnswers
    }

    private fun startTimer() {
        timer = object : CountDownTimer(
            gameSettings.gameTimeInSeconds * MILLIS_IN_SECONDS, MILLIS_IN_SECONDS
        ) {
            override fun onTick(millisUntilFinished: Long) {
                _formattedTime.value = formatTime(millisUntilFinished)
            }
            override fun onFinish() {
                finishGame()
            }
        }
        timer?.start()
    }

    private fun generateQuestion() {
        _question.value = generateQuestionUseCase(gameSettings.maxSumValue)
    }

    fun chooseAnswer(number: Int) {
        checkAnswer(number)
        updateProgress()
        generateQuestion()
    }

    private fun checkAnswer(number: Int) {
        val rightAnswer = question.value?.rightAnswer
        if (number == rightAnswer) {
            countOfRightAnswers++
        }
        countOfQuestion++
    }

    private fun updateProgress() {
        val percent = calculatePercent()
        _percentOfRightAnswers.value = percent
        _progressAnswers.value = String.format(context.resources.getString(R.string.progress_answer),
            //получаем строку из ресурсов
            countOfRightAnswers, //передаем количество правильных ответов
            gameSettings.minCountOfRightAnswers //берем из настроек игры мин кол-во правильных ответов
        )
        _enoughAnswers.value = countOfRightAnswers >= gameSettings.minCountOfRightAnswers
        _enoughPercent.value = percent >= gameSettings.minPercentOfRightAnswers
    }

    private fun calculatePercent(): Int {
        if (countOfQuestion == 0) {
            return 0
        }
        return ((countOfRightAnswers / countOfQuestion.toDouble()) * 100).toInt()
    }

    private fun formatTime(millisUntilFinished: Long): String {
        val seconds = millisUntilFinished / MILLIS_IN_SECONDS
        val minutes = seconds / SECONDS_IN_MINUTES
        val leftSeconds = seconds - (minutes * SECONDS_IN_MINUTES)
        return String.format("%02d:%02d", minutes, leftSeconds)
    }

    private fun finishGame() {
        _gameResult.value = GameResult(
            enoughAnswers.value == true && enoughPercent.value == true,
            countOfRightAnswers,
            countOfQuestion,
            gameSettings
        )
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }

    companion object {
        private const val MILLIS_IN_SECONDS = 1000L
        private const val SECONDS_IN_MINUTES = 60
    }
}