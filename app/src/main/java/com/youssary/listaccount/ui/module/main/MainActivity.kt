package com.youssary.listaccount.ui.module.main

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.youssary.listaccount.R
import com.youssary.listaccount.Util
import com.youssary.listaccount.Util.formatFecha
import com.youssary.listaccount.Util.formatearNumeroDecimal2Decimales
import com.youssary.listaccount.Util.getDate
import com.youssary.listaccount.database.ListDB
import com.youssary.listaccount.model.permision.PermissionRequester
import com.youssary.listaccount.model.repository.ListRepository
import com.youssary.listaccount.ui.common.app
import com.youssary.listaccount.ui.common.getViewModel
import com.youssary.listaccount.ui.module.main.MainViewModel.UiModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_data.*

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ListAdapter
    private val coarsePermissionRequester = PermissionRequester(
        this,
        Manifest.permission.INTERNET
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = getViewModel {
            MainViewModel(
                ListRepository(
                    app
                )
            )
        }

        adapter = ListAdapter(viewModel::onMovieClicked)
        recycler.adapter = adapter

        viewModel.model.observe(this, Observer(::updateUi))
    }

    private fun updateUi(model: UiModel) {

        progress.visibility = if (model is UiModel.Loading) View.VISIBLE else View.GONE

        when (model) {
            is UiModel.Content -> adapter.list = model.list.toMutableList()
            UiModel.RequestLocationPermission -> coarsePermissionRequester.request {
                viewModel.onCoarsePermissionRequested()
            }
        }
        LoadData(model)
    }


    private fun LoadData(model: UiModel) {
        when (model) {
            is UiModel.DataMax -> {
                data.visibility = View.VISIBLE
                lyNodata.visibility = View.GONE
                var listResult = model.list.get(0)
                showDatMax(listResult)

            }
        }


    }

    fun showDatMax(listResult: ListDB) {
        tvId.text = listResult.id.toString()
        tvDate.text = listResult.date?.let { formatFecha(it) }
        tvDescription.text = listResult.description.toString()
        tvAmount.text =
            listResult.amount.let { formatearNumeroDecimal2Decimales(it, true) }
        tvFee.text =
            listResult.fee.let { Util.formatearNumeroDecimal2Decimales(it, true) }
        tvTotal.text = (listResult.amount + listResult.fee).let {
            Util.formatearNumeroDecimal2Decimales(
                it,
                true
            )
        }
        if (listResult.amount >= 0) tvTotal.setTextColor(resources.getColor(R.color.green))
        else
            tvTotal.setTextColor(
                resources.getColor(R.color.red)
            )
    }

}
