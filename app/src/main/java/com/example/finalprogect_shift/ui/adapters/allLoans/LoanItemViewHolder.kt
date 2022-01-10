package com.example.finalprogect_shift.ui.adapters.allLoans

import androidx.recyclerview.widget.RecyclerView
import com.example.finalprogect_shift.R
import com.example.finalprogect_shift.databinding.ItemLoanBinding
import com.example.finalprogect_shift.ui.view_options.LoanState
import com.example.finalprogect_shift.domain.models.GetLoanResponse

class LoanItemViewHolder(
    private val binding: ItemLoanBinding,
    private val onItemClick: (Int) -> Unit
): RecyclerView.ViewHolder(binding.root) {

    fun bind(loan: GetLoanResponse) {
        with(binding) {
            when(loan.state) {
                LoanState.APPROVED.name -> {
                    itemContainer.setBackgroundResource(R.drawable.border_green)
                    itemLoanState.text = LoanState.APPROVED.text
                }
                LoanState.REGISTERED.name -> {
                    itemContainer.setBackgroundResource(R.drawable.border_yellow)
                    itemLoanState.text = LoanState.REGISTERED.text
                }
                LoanState.REJECTED.name -> {
                    itemContainer.setBackgroundResource(R.drawable.border_red)
                    itemLoanState.text = LoanState.REJECTED.text
                }
            }
            itemAmount.text = loan.amount.toString()
            itemPercent.text = loan.percent.toString()
        }

    }

}