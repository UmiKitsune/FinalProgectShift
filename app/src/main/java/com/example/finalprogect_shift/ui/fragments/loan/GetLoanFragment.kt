package com.example.finalprogect_shift.ui.fragments.loan

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.finalprogect_shift.App
import com.example.finalprogect_shift.R
import com.example.finalprogect_shift.databinding.FragmentGetLoanBinding
import com.example.finalprogect_shift.ui.AuthActivity
import com.example.finalprogect_shift.ui.view_options.Options.Companion.visible
import com.example.finalprogect_shift.data.storage.UserPreferencesStorage
import com.example.finalprogect_shift.domain.models.GetLoanRequest
import com.example.finalprogect_shift.domain.models.GetLoanResponse
import com.example.finalprogect_shift.presentation.BaseStates
import com.example.finalprogect_shift.presentation.getloan.GetLoanViewModel
import com.example.finalprogect_shift.presentation.getloan.GetLoanViewModelFactory
import okhttp3.ResponseBody
import javax.inject.Inject


class GetLoanFragment : Fragment(R.layout.fragment_get_loan) {

    private lateinit var binding: FragmentGetLoanBinding
    private val args: GetLoanFragmentArgs by navArgs()

    @Inject
    lateinit var factory: GetLoanViewModelFactory
    private val viewModel: GetLoanViewModel by viewModels { factory }

    @Inject
    lateinit var storage: UserPreferencesStorage

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGetLoanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            loanMaxAmount.text = args.loanMaxAmount.toString()
            loanPercent.text = args.loanPercent
            loanPeriod.text = args.loanPeriod.toString()
        }

        viewModel.state.observe(viewLifecycleOwner, ::checkConditionStates)

        binding.getLoanBtn.setOnClickListener {

            storage.token.asLiveData().observe(viewLifecycleOwner, { token ->
                viewModel.onGetLoanBtnClick(
                    token!!,
                    GetLoanRequest(
                        amount = if (binding.loanAmount.text.isNotEmpty())
                            binding.loanAmount.text.toString().toInt()
                            else 0,
                        percent = args.loanPercent.toDouble(),
                        period = args.loanPeriod,
                        firstName = binding.loanUserFirstName.text.toString().trim(),
                        lastName = binding.loanUserLastName.text.toString().trim(),
                        phoneNumber = binding.loanUserPhoneNumber.text.toString().trim()
                    ),
                    args.loanMaxAmount
                )
            })
        }

    }

    private fun checkConditionStates(state: BaseStates<GetLoanResponse>) {
        when (state) {
            is BaseStates.Loading -> loadingState()
            is BaseStates.EmptyField -> emptyFieldState()
            is BaseStates.FilledField -> fillFieldState()
            is BaseStates.OutOfAmountRange -> outOfAmountRangeState()
            is BaseStates.SuccessResponseBody -> successState()
            is BaseStates.Error -> exceptionHandling(
                state.isNetworkError,
                state.errorCode,
                state.errorBody
            )
        }
    }

    private fun loadingState() {
        binding.apply {
            progressBar.visible(true)
            getLoanItemContainer.visible(false)
        }
    }

    private fun emptyFieldState() {
        binding.apply{
            errorGetLoanFr.visible(false)
            errorRightAmount.visible(false)
            errorNotEmptyFields.visible(true)
        }
    }

    private fun fillFieldState() {
        binding.apply {
            errorGetLoanFr.visible(false)
            errorRightAmount.visible(false)
            errorNotEmptyFields.visible(false)
        }
    }

    private fun outOfAmountRangeState() {
        binding.apply {
            errorRightAmount.text = getString(R.string.get_loan_amount_range_state_txt)
            errorRightAmount.visible(true)
        }
    }

    private fun successState() {
        val dialog = AlertDialog.Builder(requireContext())
            .setCancelable(false)
            .setIcon(R.drawable.ic_get_loan)
            .setTitle(getString(R.string.dialog_title))
            .setMessage(getString(R.string.dialog_msg))
            .setPositiveButton(getString(R.string.dialog_positive_btn_txt)) { _, _ ->
                findNavController().popBackStack()
            }.create()
        dialog.show()
    }

    private fun exceptionHandling(isNetworkError: Boolean, code: Int?, errorBody: ResponseBody?) {
        if (!isNetworkError) {
            when (code) {
                400 -> {
                    binding.apply {
                        errorRightAmount.visible(true)
                        getLoanItemContainer.visible(true)
                        progressBar.visible(false)
                    }
                }
                403, 401 -> {
                    Intent(context, AuthActivity::class.java).also {
                        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        requireActivity().startActivity(it)
                    }
                    Toast.makeText(context, getString(R.string.main_error_403), Toast.LENGTH_SHORT)
                        .show()
                }
                404 -> {
                    binding.apply {
                        errorGetLoanFr.text = getString(R.string.sign_up_code_404)
                        errorGetLoanFr.visible(true)
                        getLoanItemContainer.visible(false)
                    }
                }
            }
        } else {
            binding.apply {
                errorGetLoanFr.text = getString(R.string.IOException_txt)
                errorGetLoanFr.visible(true)
                getLoanItemContainer.visible(false)
            }
        }
    }

}