package com.example.finalprogect_shift.ui.fragments.auth

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.finalprogect_shift.App
import com.example.finalprogect_shift.R
import com.example.finalprogect_shift.databinding.FragmentSignUpBinding
import com.example.finalprogect_shift.ui.view_options.Options.Companion.visible
import com.example.finalprogect_shift.presentation.BaseStates
import com.example.finalprogect_shift.presentation.signup.SignUpViewModel
import com.example.finalprogect_shift.presentation.signup.SignUpViewModelFactory
import okhttp3.ResponseBody
import javax.inject.Inject

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private lateinit var binding: FragmentSignUpBinding

    @Inject
    lateinit var factory: SignUpViewModelFactory
    private val viewModel: SignUpViewModel by viewModels {factory}

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(viewLifecycleOwner, ::checkFields)

        binding.signUpButton.setOnClickListener {
            val name = binding.signUpName.text.toString().trim()
            val password = binding.signUpPassword.text.toString().trim()
            viewModel.signUpBtnOnClick(name, password)
        }
    }

    private fun checkFields(state: BaseStates<ResponseBody>) {
        when(state) {
            is BaseStates.Loading -> loadingState()
            is BaseStates.EmptyField ->  emptyFieldState()
            is BaseStates.FilledField -> fillFieldState()
            is BaseStates.SuccessResponseBody -> successState(state)
            is BaseStates.Error -> exceptionHandling(state.isNetworkError, state.errorCode, state.errorBody)
        }
    }

    private fun loadingState() {
        binding.apply {
            signUpItemsContainer.visible(false)
            progressBar.visible(true)
        }
    }

    private fun emptyFieldState() {
        binding.apply {
            errorNeedNotEmptyFields.visible(true)
            errorUserExists.visible(false)
            signUpItemsContainer.visible(true)
            progressBar.visible(false)
        }
    }

    private fun fillFieldState() {
        binding.apply {
            errorUserExists.visible(false)
            errorNeedNotEmptyFields.visible(false)
        }
    }

    private fun successState(state: BaseStates<ResponseBody>) {
        binding.signUpName.text.clear()
        binding.signUpName.text.clear()
        Toast.makeText(context, getString(R.string.success_registration), Toast.LENGTH_SHORT).show()
        findNavController().popBackStack()
    }

    private fun exceptionHandling(isNetworkError: Boolean, code: Int?, errorBody: ResponseBody?){
        if (!isNetworkError) {
            binding.apply {
                progressBar.visible(false)
                signUpItemsContainer.visible(true)
            }
            when (code) {
                400 -> binding.errorUserExists.visible(true)

                401 -> {
                    binding.errorUserExists.text = getString(R.string.sign_up_code_401)
                    binding.errorUserExists.visible(true)
                }
                403 -> {
                    binding.errorUserExists.text = getString(R.string.sign_up_code_403)
                    binding.errorUserExists.visible(true)
                }
                404 -> {
                    binding.apply {
                        errorUserExists.text = getString(R.string.sign_up_code_404)
                        signUpItemsContainer.visible(false)
                        errorUserExists.visible(true)
                    }
                }
            }
        } else {
            binding.errorUserExists.text = getString(R.string.IOException_txt)
            binding.errorUserExists.visible(true)
            binding.progressBar.visible(false)
        }
    }
}