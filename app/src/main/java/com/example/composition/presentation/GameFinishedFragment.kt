package com.example.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.composition.R
import com.example.composition.databinding.GameFinishedFragmentBinding
import com.example.composition.domain.entites.GameResult

class GameFinishedFragment : Fragment() {

    private val args by navArgs<GameFinishedFragmentArgs>()

    private var _binding: GameFinishedFragmentBinding? = null
    private val binding: GameFinishedFragmentBinding
        get() = _binding ?: throw  RuntimeException("GameFinishedFragmentBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = GameFinishedFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupOnClickListener()
        bindViews()
    }

    private fun bindViews() {
        with(binding) {
            emojiResult.setImageResource(getSmileResId())
            tvRequiredAnswer.text = String.format(
                getString(R.string.required_answer),
                args.gameResul.gameSettings.minCountOfRightAnswers
            )
            tvScoreAnswer.text = String.format(
                getString(R.string.score_answer),
                args.gameResul.countOfRightAnswers
            )
            tvRequiredPercentage.text = String.format(
                getString(R.string.required_percentage),
                args.gameResul.gameSettings.minPercentOfRightAnswers
            )
            tvScorePercentage.text = String.format(
                getString(R.string.score_percentage),
                getPercentOfRightAnswers()
            )
        }
    }

    private fun getSmileResId(): Int {
        return if (args.gameResul.winner)
            R.drawable.ic_emoji_happy
        else R.drawable.ic_emoji_sad
    }

    private fun setupOnClickListener() {
        binding.buttonRetry.setOnClickListener {
            retryGame()
        }
    }

    private fun getPercentOfRightAnswers() = with(args.gameResul) {
        if (countOfQuestions == 0) {
            return 0
        }
        ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
    }

    private fun retryGame() {
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}