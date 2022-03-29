package com.example.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.composition.R
import com.example.composition.databinding.ChooseLevelFragmentBinding
import com.example.composition.domain.entites.Level
import java.lang.RuntimeException

class ChooseLevelFragment : Fragment() {


    private var _binding: ChooseLevelFragmentBinding? = null
    private val binding: ChooseLevelFragmentBinding
        get() = _binding ?: throw RuntimeException("ChooseLevelFragment == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ChooseLevelFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            buttonTest.setOnClickListener {
                launchGameLevel(Level.TEST)
            }
            buttonEasyLevel.setOnClickListener {
                launchGameLevel(Level.EASY)
            }
            buttonNormalLevel.setOnClickListener {
                launchGameLevel(Level.NORMAL)
            }
            buttonHardLevel.setOnClickListener {
                launchGameLevel(Level.HARD)
            }
        }
    }

    private fun launchGameLevel(level: Level) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, GameFragment.newInstance(level))
            .addToBackStack(GameFragment.NAME)
            .commit()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {

        fun newInstance(): ChooseLevelFragment {
            return ChooseLevelFragment()

        }

    }
}