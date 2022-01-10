package com.example.finalprogect_shift.ui.fragments.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.finalprogect_shift.App
import com.example.finalprogect_shift.R
import com.example.finalprogect_shift.databinding.FragmentSignInBinding
import com.example.finalprogect_shift.ui.LoanActivity
import com.example.finalprogect_shift.ui.view_options.Options.Companion.visible
import com.example.finalprogect_shift.presentation.BaseStates
import com.example.finalprogect_shift.presentation.signin.SignInViewModel
import com.example.finalprogect_shift.presentation.signin.SignInViewModelFactory
import okhttp3.ResponseBody
import javax.inject.Inject

class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private lateinit var binding: FragmentSignInBinding

    @Inject
    lateinit var factory: SignInViewModelFactory
    private val viewModel: SignInViewModel by viewModels { factory }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signInButton.setOnClickListener {

            val name = binding.signInLogin.text.toString().trim()
            val password = binding.signInPassword.text.toString().trim()
            viewModel.onSignInBtnClick(name, password)

            viewModel.state.observe(viewLifecycleOwner, ::checkFields)
        }

        binding.moveToSignUpButton.setOnClickListener {
            openFragment(R.id.action_signInFragment_to_signUpFragment)
        }
    }

    private fun checkFields(state: BaseStates<ResponseBody>) {
        when (state) {
            is BaseStates.Loading -> loadingState()
            is BaseStates.EmptyField -> emptyFieldState()
            is BaseStates.FilledField -> fillFieldState()
            is BaseStates.SuccessResponseBody -> successState(state.value.string())
            is BaseStates.Error -> exceptionHandling(state.isNetworkError, state.errorCode, state.errorBody)
        }
    }

    private fun loadingState() {
        binding.apply {
            sigInItemsContainer.visible(false)
            progressBar.visible(true)
        }
    }

    private fun emptyFieldState() {
        binding.apply {
            errorNeedNotEmptyFields.visible(true)
            errorNeedSignUp.visible(false)
            sigInItemsContainer.visible(true)
            progressBar.visible(false)
        }
    }

    private fun fillFieldState() {
        binding.apply {
            errorNeedSignUp.visible(false)
            errorNeedNotEmptyFields.visible(false)
        }
    }

    private fun successState(token: String) {
        viewModel.setToken(token)

        Intent(context, LoanActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            requireActivity().startActivity(it)
        }

        binding.progressBar.visible(true)
    }

    private fun exceptionHandling(isNetworkError: Boolean, code: Int?, errorBody: ResponseBody?) {
        if (!isNetworkError) {
            binding.apply {
                progressBar.visible(false)
                sigInItemsContainer.visible(true)
            }
            when (code) {
                401 -> {
                    binding.errorNeedSignUp.text = getString(R.string.sign_in_code_401_txt)
                    binding.errorNeedSignUp.visible(true)
                }
                403 -> {
                    binding.errorNeedSignUp.visible(true)
                }
                404 -> binding.errorNeedSignUp.visible(true)
            }
        } else {
            binding.errorNeedSignUp.text = getString(R.string.IOException_txt)
            binding.errorNeedSignUp.visible(true)
        }

    }

    override fun onPause() {
        super.onPause()
        clearFields()
    }

    private fun clearFields() {
        binding.apply {
            signInLogin.text.clear()
            signInPassword.text.clear()
            errorNeedNotEmptyFields.visible(false)
            errorNeedSignUp.visible(false)
        }
    }

    private fun openFragment(action: Int) {
        findNavController().navigate(
            action,
            null,
            navOptions {
                anim {
                    enter = R.animator.fragment_open_enter
                    exit = R.animator.fragment_open_exit
                    popEnter = R.animator.fragment_close_enter
                    popExit = R.animator.fragment_close_exit
                }
            }
        )
    }

}