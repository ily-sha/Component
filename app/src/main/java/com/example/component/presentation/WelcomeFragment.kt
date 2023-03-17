package com.example.component.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.component.R
import com.example.component.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {

    private var _viewBinding: FragmentWelcomeBinding? = null
    private val viewBinding: FragmentWelcomeBinding
        get() = _viewBinding ?: throw RuntimeException("FragmentWelcomeBinding is null")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.buttonUnderstand.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_chooseLevelFragment)
        }
//            requireActivity().supportFragmentManager.beginTransaction()
//                .replace(R.id.main_container, ChooseLevelFragment.newInstance())
//                .addToBackStack(ChooseLevelFragment.NAME)
//                .commit()
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _viewBinding = null
    }


}