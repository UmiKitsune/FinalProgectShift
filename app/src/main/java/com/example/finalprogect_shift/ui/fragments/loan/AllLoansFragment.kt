package com.example.finalprogect_shift.ui.fragments.loan

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
import androidx.navigation.navOptions
import com.example.finalprogect_shift.App
import com.example.finalprogect_shift.R
import com.example.finalprogect_shift.databinding.FragmentAllLoansBinding
import com.example.finalprogect_shift.ui.AuthActivity
import com.example.finalprogect_shift.ui.view_options.Options.Companion.visible
import com.example.finalprogect_shift.data.storage.UserPreferencesStorage
import com.example.finalprogect_shift.domain.models.GetLoanResponse
import com.example.finalprogect_shift.presentation.BaseStates
import com.example.finalprogect_shift.presentation.allLoans.AllLoansViewModel
import com.example.finalprogect_shift.presentation.allLoans.AllLoansViewModelFactory
import com.example.finalprogect_shift.ui.adapters.allLoans.AllLoansAdapter
import okhttp3.ResponseBody
import javax.inject.Inject

class AllLoansFragment : Fragment() {

    private lateinit var binding: FragmentAllLoansBinding
    private val args: AllLoansFragmentArgs by navArgs()

    @Inject
    lateinit var factory: AllLoansViewModelFactory
    private val viewModel: AllLoansViewModel by viewModels { factory }

    @Inject
    lateinit var storage: UserPreferencesStorage

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllLoansBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = AllLoansAdapter(
            onItemClick = { loanId ->
                storage.token.asLiveData().observe(viewLifecycleOwner, { token ->
                    viewModel.onItemClick(token!!, loanId)
                })
                viewModel.state.observe(viewLifecycleOwner, ::checkStates)
            }
        )
        adapter.loans = args.loans?.loans ?: emptyList()
        if (adapter.loans.isEmpty()) {
            binding.apply {
                loansRecyclerView.visible(false)
                emptyLoansList.visible(true)
            }
        }
        binding.loansRecyclerView.adapter = adapter

    }

    private fun checkStates(state: BaseStates<GetLoanResponse>) {
        when (state) {
            is BaseStates.Loading -> loadingState()
            is BaseStates.SuccessResponseBody -> successState(state.value)
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
            loansRecyclerView.visible(false)
        }
    }

    private fun successState(responseBody: GetLoanResponse) {
        val action = AllLoansFragmentDirections.actionAllLoansInfoFragmentToLoanDetailsFragment(
            loanById = responseBody
        )
        findNavController().navigate(action,
            navOptions {
                anim {
                    enter = R.animator.item_open_enter
                    exit = R.animator.item_open_exit
                    popEnter = R.animator.item_close_enter
                    popExit = R.animator.item_close_exit
                }
            })
    }

    private fun exceptionHandling(isNetworkError: Boolean, code: Int?, errorBody: ResponseBody?) {
        if (!isNetworkError) {
            when (code) {
                403, 401 -> {
                    Toast.makeText(context, getString(R.string.main_error_403), Toast.LENGTH_SHORT)
                        .show()
                    Intent(context, AuthActivity::class.java).also {
                        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        requireActivity().startActivity(it)
                    }

                }
                404 -> {
                    binding.apply {
                        errorAllLoansFr.text = getString(R.string.sign_up_code_404)
                        errorAllLoansFr.visible(true)
                        loansRecyclerView.visible(false)
                    }
                }
            }
        } else {
            binding.apply {
                errorAllLoansFr.text = getString(R.string.IOException_txt)
                errorAllLoansFr.visible(true)
                loansRecyclerView.visible(false)
            }
        }
    }

}