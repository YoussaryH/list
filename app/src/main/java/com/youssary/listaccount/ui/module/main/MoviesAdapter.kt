package com.youssary.listaccount.ui.module.main

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.youssary.listaccount.R
import com.youssary.listaccount.Util
import com.youssary.listaccount.Util.getDate
import com.youssary.listaccount.database.ListDB
import com.youssary.listaccount.model.server.AccountDbResult
import com.youssary.listaccount.ui.common.basicDiffUtil
import com.youssary.listaccount.ui.common.inflate
import kotlinx.android.synthetic.main.adapter_list_account_content.view.*
import kotlinx.android.synthetic.main.list_main_activity.view.*

class MoviesAdapter(private val listener: (AccountDbResult) -> Unit) :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    var list: List<ListDB> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.id == new.id }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.list_main_activity, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listData = list[position]
        holder.bind(listData)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(dataResult: ListDB) {
            var mTotal = dataResult.amount + dataResult.fee;
            itemView.tvId.text = dataResult.id.toString()
            itemView.tvDate.text = dataResult.date?.let { getDate(it) }
            itemView.tvAmount.text = dataResult.amount?.let { Util.formatearNumeroDecimal2Decimales(it, true) }
            itemView.tvFee.text =
                dataResult.fee?.let { Util.formatearNumeroDecimal2Decimales(it, true) }
            itemView.tvDescription.text = dataResult.description
            itemView.tvTotal.text = mTotal?.let { Util.formatearNumeroDecimal2Decimales(it, true) }
            if (dataResult.amount >= 0) {
                itemView.lyType.setBackgroundResource(R.color.green)
            } else {
                itemView.lyType.setBackgroundResource(R.color.colorRed)
            }
        }
    }
}
