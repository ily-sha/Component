package com.example.component.presentation

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.component.databinding.FragmentGameBinding
import com.example.component.domain.entity.GameResult
import com.example.component.domain.entity.Level
import com.example.component.domain.entity.Question

class GameFragment : Fragment() {


    private val gameViewModel: GameViewModel by lazy {
        ViewModelProvider(
            this,
            GameViewModelFactory(
                GameFragmentArgs.fromBundle(requireArguments()).level,
                requireActivity().application
            )
        )[GameViewModel::class.java]
    }
    private val tvOptions: List<TextView> by lazy {
        listOf(
            viewBinding.tvOption1,
            viewBinding.tvOption2,
            viewBinding.tvOption3,
            viewBinding.tvOption4,
            viewBinding.tvOption5,
            viewBinding.tvOption6
        )
    }

    private var _viewBinding: FragmentGameBinding? = null
    private val viewBinding: FragmentGameBinding
        get() = _viewBinding ?: throw RuntimeException("GameFragment is null")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentGameBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()

    }

    private fun observeLiveData() {
        gameViewModel.gameResult.observe(viewLifecycleOwner) {
            launchGameFinished(it)
        }
        gameViewModel.progressAnswer.observe(viewLifecycleOwner) {
            viewBinding.tvAnswersProgress.text = it
        }
        gameViewModel.formattedTime.observe(viewLifecycleOwner) {
            viewBinding.tvTimer.text = it
        }
        gameViewModel.percentOfRightAnswer.observe(viewLifecycleOwner) {
            viewBinding.progressBar.setProgress(it, true)
        }
        gameViewModel.minPercent.observe(viewLifecycleOwner) {
            viewBinding.progressBar.secondaryProgress = it
        }
        gameViewModel.requiredCountOfRightAnswer.observe(viewLifecycleOwner) {
            viewBinding.tvAnswersProgress.setTextColor(getColorByState(it))
        }
        gameViewModel.requiredPercentOfRightAnswer.observe(viewLifecycleOwner) {
            viewBinding.progressBar.progressTintList = ColorStateList.valueOf(getColorByState(it))
        }
        gameViewModel.question.observe(viewLifecycleOwner) {
            setQuestionParams(it)
        }
    }

    private fun getColorByState(state: Boolean): Int {
        val colorId =
            if (state) android.R.color.holo_green_light else android.R.color.holo_red_light
        return ContextCompat.getColor(requireContext(), colorId)
    }

    private fun setQuestionParams(question: Question) {
        val options = question.options
        for (i in tvOptions.indices) {
            tvOptions[i].text = options[i].toString()
            tvOptions[i].setOnClickListener {
                gameViewModel.chooseAnswer(options[i])
            }
        }

        viewBinding.tvLeftNumber.text = question.visibleNumber.toString()
        viewBinding.tvSum.text = question.sum.toString()
    }


    private fun launchGameFinished(gameResult: GameResult) {
        findNavController().navigate(
            GameFragmentDirections.actionGameFragmentToGameFinishedFragment(
                gameResult
            )
        )
//        findNavController().navigate(
//            R.id.action_gameFragment_to_gameFinishedFragment,
//            Bundle().apply {
//                putParcelable("game_result", gameResult)
//            })
//        requireActivity().supportFragmentManager.beginTransaction()
//            .replace(R.id.main_container, GameFinishedFragment.newInstance(gameResult))
//            .addToBackStack(null).commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        _viewBinding = null
    }


}