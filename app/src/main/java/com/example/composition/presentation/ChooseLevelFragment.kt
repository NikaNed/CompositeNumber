package com.example.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.composition.databinding.ChooseLevelFragmentBinding
import com.example.composition.domain.entites.Level

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
        findNavController().navigate(
            ChooseLevelFragmentDirections.actionChooseLevelFragmentToGameFragment(level)
        ) //передаем аргументы
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}