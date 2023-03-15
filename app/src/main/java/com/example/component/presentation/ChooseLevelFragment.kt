package com.example.component.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.component.R
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
            launchGameFragment(GameFragment.newInstant(Level.TEST))
        }
        viewBinding.buttonLevelEasy.setOnClickListener {
            launchGameFragment(GameFragment.newInstant(Level.EASY))
        }
        viewBinding.buttonLevelNormal.setOnClickListener {
            launchGameFragment(GameFragment.newInstant(Level.NORMAL))
        }
        viewBinding.buttonLevelHard.setOnClickListener {
            launchGameFragment(GameFragment.newInstant(Level.HARD))
        }
    }

    private fun launchGameFragment(gameFragment: GameFragment){
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, gameFragment)
            .addToBackStack(GameFragment.NAME)
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        _viewBinding = null
    }

    companion object {
        const val NAME = "ChooseLevelFragment"
        fun newInstance(): ChooseLevelFragment{
            return ChooseLevelFragment()
        }
    }

}