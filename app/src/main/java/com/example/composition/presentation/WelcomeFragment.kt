package com.example.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.composition.R
import com.example.composition.databinding.WelcomeFragmentBinding
import java.lang.RuntimeException

class WelcomeFragment: Fragment() {

   private var _binding: WelcomeFragmentBinding? = null
   private val binding: WelcomeFragmentBinding
       get() = _binding ?: throw RuntimeException ("WelcomeFragmentBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= WelcomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonUnderstand
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}