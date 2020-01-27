package com.youssary.listaccount.ui

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.youssary.listaccount.R
import com.youssary.listaccount.model.AccountDbResult
import kotlinx.android.synthetic.main.adapter_list_account_content.view.*
import kotlin.properties.Delegates

class MoviesAdapter(private val listener: (AccountDbResult) -> Unit) :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    var listAccount: List<AccountDbResult> by Delegates.observable(emptyList()) { _, old, new ->
        DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                old[oldItemPosition].id == new[newItemPosition].id

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                old[oldItemPosition] == new[newItemPosition]

            override fun getOldListSize(): Int = old.size

            override fun getNewListSize(): Int = new.size
        }).dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.list_main_activity, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listAccount.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = listAccount[position]
        holder.bind(movie)
        holder.itemView.setOnClickListener { listener(movie) }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(account: AccountDbResult) {
            itemView.tvId.text = account.id.toString()
            itemView.tvDate.text = account.date
            itemView.tvAmount.text = account.amount.toString()
            itemView.tvFee.text = account.fee.toString()
            itemView.tvDescription.text = account.description
            itemView.tvTotal.text = account.getTotal().toString()
        }
    }
}
