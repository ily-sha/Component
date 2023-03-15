package com.example.component.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.component.R
import com.example.component.databinding.FragmentGameFinishedBinding
import com.example.component.domain.entity.GameResult
import androidx.activity.OnBackPressedCallback as OnBackPressedCallback1

class GameFinishedFragment : Fragment() {
    private lateinit var gameResult: GameResult

    private var _viewBinding: FragmentGameFinishedBinding? = null
    private val viewBinding: FragmentGameFinishedBinding
        get() = _viewBinding ?: throw RuntimeException("FragmentGameFinishedBinding is null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback1(true) {
                override fun handleOnBackPressed() {
                    retryGame()
                }

            })
        viewBinding.buttonRetry.setOnClickListener {
            retryGame()
        }
        setGameResult()
    }

    private fun setGameResult() {
        val pictureId = if (gameResult.winner) R.drawable.ic_smile else R.drawable.ic_sad
        viewBinding.emojiResult.setImageResource(pictureId)
        viewBinding.tvRequiredAnswers.text = String.format(
            getString(R.string.required_score),
            gameResult.gameSettings.minCountOfRightAnswers.toString()
        )
        viewBinding.tvRequiredPercent.text = String.format(
            getString(
                R.string.required_percentage,
                gameResult.gameSettings.minPercentOfRightAnswers.toString()
            )
        )
        viewBinding.tvScoreAnswers.text = String.format(
            getString(
                R.string.score_answers,
                gameResult.countOfRightAnswers.toString()
            )
        )
        viewBinding.tvScorePercentage.text = String.format(
            getString(
                R.string.score_percentage,
                gameResult.percentOfRightAnswer.toString()
            )
        )
    }

    private fun retryGame() {
        requireActivity().supportFragmentManager.popBackStack(GameFragment.NAME, 1)
    }

    override fun onDestroy() {
        super.onDestroy()
        _viewBinding = null
    }

    private fun parseArgs() {
        requireArguments().getParcelable<GameResult>(KEY_GAME_RESULT)?.let {
            gameResult = it
        }
    }


    companion object {
        private const val KEY_GAME_RESULT = "game_result"
        fun newInstance(gameResult: GameResult): GameFinishedFragment {
            return GameFinishedFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_GAME_RESULT, gameResult)
                }
            }
        }
    }


}