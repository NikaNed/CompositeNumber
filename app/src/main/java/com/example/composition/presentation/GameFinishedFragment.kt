package com.example.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.composition.databinding.GameFinishedFragmentBinding

class GameFinishedFragment : Fragment() {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}