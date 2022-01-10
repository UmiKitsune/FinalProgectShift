package com.example.finalprogect_shift.ui.fragments.loan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.finalprogect_shift.R
import com.example.finalprogect_shift.databinding.FragmentLoanDetailsBinding
import com.example.finalprogect_shift.ui.view_options.LoanState
import com.example.finalprogect_shift.ui.view_options.Options.Companion.visible
import java.text.SimpleDateFormat
import java.util.*

class LoanDetailsFragment : Fragment(R.layout.fragment_loan_details){
    private lateinit var binding : FragmentLoanDetailsBinding
    val args: LoanDetailsFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLoanDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            when(args.loanById.state) {
                LoanState.APPROVED.name -> {
                    detailsImage.setImageResource(R.drawable.ic_approved)
                    detailsStatus.text = LoanState.APPROVED.text
                    detailsDescription.text = getString(R.string.details_descr_success_txt)
                    detailsSuccessGroup.visible(true)
                }
                LoanState.REGISTERED.name -> {
                    detailsImage.setImageResource(R.drawable.ic_registered)
                    detailsStatus.text = LoanState.REGISTERED.text
                    detailsDescription.text = getString(R.string.details_descr_registered_txt)
                }

                LoanState.REJECTED.name -> {
                    detailsImage.setImageResource(R.drawable.ic_rejected)
                    detailsStatus.text = LoanState.REJECTED.text
                    detailsDescription.text = getString(R.string.details_descr_rejected_txt)
                }
            }
            detailsId.text = args.loanById.id.toString()
            detailsAmount.text = args.loanById.amount.toString()
            detailsPercent.text = args.loanById.percent.toString()
            detailsPeriod.text = args.loanById.period.toString()
            detailsDate.text = SimpleDateFormat("dd.MM.yy",
                Locale.getDefault()).format(args.loanById?.date)

        }
    }
}