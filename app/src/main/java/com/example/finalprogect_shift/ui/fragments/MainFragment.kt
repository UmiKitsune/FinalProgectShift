package com.example.finalprogect_shift.ui.fragments

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
import androidx.navigation.navOptions
import com.example.finalprogect_shift.App
import com.example.finalprogect_shift.R
import com.example.finalprogect_shift.databinding.FragmentMainBinding
import com.example.finalprogect_shift.ui.AuthActivity
import com.example.finalprogect_shift.ui.view_options.Options.Companion.visible
import com.example.finalprogect_shift.data.storage.UserPreferencesStorage
import com.example.finalprogect_shift.domain.models.LoanConditions
import com.example.finalprogect_shift.domain.models.ShowLoansList
import com.example.finalprogect_shift.presentation.BaseStates
import com.example.finalprogect_shift.presentation.main.MainViewModel
import com.example.finalprogect_shift.presentation.main.MainViewModelFactory
import okhttp3.ResponseBody
import javax.inject.Inject


class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var binding: FragmentMainBinding

    @Inject
    lateinit var factory: MainViewModelFactory
    private val viewModel: MainViewModel by viewModels { factory }

    @Inject
    lateinit var storage: UserPreferencesStorage

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.getLoanBtn.setOnClickListener {
            storage.token.asLiveData().observe(viewLifecycleOwner, { token ->
                viewModel.onGetLoanBtnClick(token!!)
            })
            viewModel.loanCondition.observe(viewLifecycleOwner, ::checkConditionStates)
        }

        binding.showAllLoanBtn.setOnClickListener {
            storage.token.asLiveData().observe(viewLifecycleOwner, { token ->
                viewModel.onShowAllLoanBtnClick(token!!)
            })
            viewModel.loansListData.observe(viewLifecycleOwner, ::checkAllLoansStates)
        }

    }

    private fun checkConditionStates(state: BaseStates<LoanConditions>) {
        when (state) {
            is BaseStates.Loading -> loadingState()
            is BaseStates.SuccessResponseBody -> conditionSuccessState(state.value)
            is BaseStates.Error -> exceptionHandling(
                state.isNetworkError,
                state.errorCode,
                state.errorBody
            )
        }
    }

    private fun checkAllLoansStates(state: BaseStates<ShowLoansList>) {
        when (state) {
            is BaseStates.Loading -> loadingState()
            is BaseStates.SuccessResponseBody -> loansSuccessState(state.value)
            is BaseStates.Error -> exceptionHandling(
                state.isNetworkError,
                state.errorCode,
                state.errorBody
            )
        }
    }

    private fun loadingState() {
        binding.apply {
            mainItemContainer.visible(false)
            progressBar.visible(true)
        }
    }

    private fun conditionSuccessState(conditions: LoanConditions) {
        val action = MainFragmentDirections.actionMainFragmentToGetLoanFragment(
            loanMaxAmount = conditions.maxAmount,
            loanPercent = conditions.percent.toString(),
            loanPeriod = conditions.period
        )
        findNavController().navigate(action,
            navOptions {
                anim {
                    enter = R.animator.fragment_open_enter
                    exit = R.animator.fragment_open_exit
                    popEnter = R.animator.fragment_close_enter
                    popExit = R.animator.fragment_close_exit
                }
            })
    }

    private fun loansSuccessState(showLoansListData: ShowLoansList) {
        val action = MainFragmentDirections.actionMainFragmentToAllLoansInfoFragment(
            loans = showLoansListData
        )
        findNavController().navigate(action,
            navOptions {
                anim {
                    enter = R.animator.fragment_open_enter
                    exit = R.animator.fragment_open_exit
                    popEnter = R.animator.fragment_close_enter
                    popExit = R.animator.fragment_close_exit
                }
            })
    }

    private fun exceptionHandling(isNetworkError: Boolean, code: Int?, errorBody: ResponseBody?) {
        if (!isNetworkError) {
            when (code) {
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
                        errorMainFr.text = getString(R.string.sign_up_code_404)
                        errorMainFr.visible(true)
                        mainItemContainer.visible(false)
                    }
                }
            }
        } else {
            binding.errorMainFr.text = getString(R.string.IOException_txt)
            binding.errorMainFr.visible(true)
        }
    }

}