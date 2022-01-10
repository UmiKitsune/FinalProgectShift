package com.example.finalprogect_shift.ui.adapters.allLoans

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalprogect_shift.databinding.ItemLoanBinding
import com.example.finalprogect_shift.domain.models.GetLoanResponse


class AllLoansAdapter(private val onItemClick: (Int) -> Unit): RecyclerView.Adapter<LoanItemViewHolder>(){

    var loans: List<GetLoanResponse> = emptyList()
    set(newValue) {
        field = newValue
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoanItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLoanBinding.inflate(inflater, parent, false)
        return LoanItemViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: LoanItemViewHolder, position: Int) {
        val loan = loans[position]

        holder.bind(loan)
        holder.itemView.setOnClickListener{onItemClick(loan.id)}
    }

    override fun getItemCount(): Int = loans.size
}