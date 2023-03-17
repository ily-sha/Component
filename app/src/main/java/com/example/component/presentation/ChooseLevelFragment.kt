package com.example.component.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.component.databinding.FragmentChooseLevelBinding
import com.example.component.domain.entity.Level

class ChooseLevelFragment : Fragment() {

    private var _viewBinding: FragmentChooseLevelBinding? = null
    private val viewBinding: FragmentChooseLevelBinding
        get() = _viewBinding ?: throw RuntimeException("FragmentChooseLevelBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentChooseLevelBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.buttonLevelTest.setOnClickListener {
            launchGameFragment(Level.TEST)
        }
        viewBinding.buttonLevelEasy.setOnClickListener {
            launchGameFragment(Level.EASY)
        }
        viewBinding.buttonLevelNormal.setOnClickListener {
            launchGameFragment(Level.NORMAL)
        }
        viewBinding.buttonLevelHard.setOnClickListener {
            launchGameFragment(Level.HARD)
        }
    }

    private fun launchGameFragment(level: Level) {
        findNavController().navigate(
            ChooseLevelFragmentDirections.actionChooseLevelFragmentToGameFragment(
                level
            )
        )

    }

    override fun onDestroy() {
        super.onDestroy()
        _viewBinding = null
    }

}