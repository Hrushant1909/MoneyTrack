package com.hrushant.moneytrack.ui.transaction

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hrushant.moneytrack.data.entity.Transaction
import com.hrushant.moneytrack.databinding.ItemTransactionBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TransactionAdapter :
    RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    private var transactions = emptyList<Transaction>()

    fun submitList(newList: List<Transaction>) {
        transactions = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TransactionViewHolder {

        val binding =
            ItemTransactionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: TransactionViewHolder,
        position: Int
    ) {
        holder.bind(transactions[position])
    }

    override fun getItemCount(): Int {
        return transactions.size
    }

    class TransactionViewHolder(
        private val binding: ItemTransactionBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(transaction: Transaction) {

            binding.tvAmount.text =
                "₹${transaction.amount}"

            binding.tvDescription.text =
                transaction.description

            binding.tvDate.text =
                SimpleDateFormat(
                    "dd MMM yyyy",
                    Locale.getDefault()
                ).format(
                    Date(transaction.date)
                )
        }
    }
}