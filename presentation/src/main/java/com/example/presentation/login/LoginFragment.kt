package com.example.presentation.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.presentation.R
import com.example.presentation.databinding.FragmentLoginBinding
import kotlinx.coroutines.launch


class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.email.editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(
                p0: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            ) {
            }

            override fun onTextChanged(
                p0: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            ) {
                viewModel.setEmail(p0?.toString() ?: "")
            }
        })
        binding.password.editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(
                p0: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            ) {
            }

            override fun onTextChanged(
                p0: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            ) {
                viewModel.setPassword(p0?.toString() ?: "")
            }
        })
        binding.email.editText.filters = arrayOf(CyrillicFilter)
        binding.ok.setOnClickListener {
            openURL("https://ok.ru/")
        }
        binding.vk.setOnClickListener {
            openURL("https://vk.com/")
        }
        lifecycleScope.launch {
            viewModel.loginButtonEnabled.collect {
                binding.enter.isEnabled = it
            }
        }
        _binding?.enter?.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_home)
        }
        return root
    }

    fun openURL(inURL: String) {
        val browse = Intent(Intent.ACTION_VIEW, inURL.toUri())

        startActivity(browse)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}