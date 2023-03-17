package com.example.component.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.component.R
import com.example.component.databinding.FragmentGameFinishedBinding
import com.example.component.domain.entity.GameResult
import androidx.activity.OnBackPressedCallback as OnBackPressedCallback1

class GameFinishedFragment : Fragment() {

    private var _viewBinding: FragmentGameFinishedBinding? = null
    private val viewBinding: FragmentGameFinishedBinding
        get() = _viewBinding ?: throw RuntimeException("FragmentGameFinishedBinding is null")


    private val gameResult: GameResult by lazy {
        GameFinishedFragmentArgs.fromBundle(requireArguments()).gameResult
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
        viewBinding.gameResult = gameResult
    }

    private fun retryGame() {
        findNavController().popBackStack()
    }


    override fun onDestroy() {
        super.onDestroy()
        _viewBinding = null
    }


}