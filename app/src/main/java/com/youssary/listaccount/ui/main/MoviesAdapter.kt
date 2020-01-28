package com.youssary.listaccount.ui.main

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.youssary.listaccount.R
import com.youssary.listaccount.model.AccountDbResult
import com.youssary.listaccount.ui.common.basicDiffUtil
import com.youssary.listaccount.ui.common.inflate
import kotlinx.android.synthetic.main.adapter_list_account_content.view.*
import kotlinx.android.synthetic.main.list_main_activity.view.*

class MoviesAdapter(private val listener: (AccountDbResult) -> Unit) :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    var movies: List<AccountDbResult> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.id == new.id }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.list_main_activity, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
        holder.itemView.setOnClickListener { listener(movie) }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(account: AccountDbResult) {

            itemView.tvId.text = account.id.toString()
            itemView.tvDate.text = account.date
            itemView.tvAmount.text = account.getFormat(account.amount)
            itemView.tvFee.text = account.getFormat(account.fee)
            itemView.tvDescription.text = account.description
            itemView.tvTotal.text = account.getFormat(account.getTotal())
            if (account.amount >= 0) {
                itemView.lyType.setBackgroundResource(R.color.colorRed)
            } else {
                itemView.lyType.setBackgroundResource(R.color.green)
            }
        }
    }
}
