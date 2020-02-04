package com.youssary.listaccount.ui.module.main

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.youssary.listaccount.R
import com.youssary.listaccount.Util
import com.youssary.listaccount.model.permision.PermissionRequester
import com.youssary.listaccount.model.repository.ListRepository
import com.youssary.listaccount.ui.common.app
import com.youssary.listaccount.ui.common.getViewModel
import com.youssary.listaccount.ui.module.main.MainViewModel.UiModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_data.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MoviesAdapter
    private val coarsePermissionRequester = PermissionRequester(this,
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

        adapter = MoviesAdapter(viewModel::onMovieClicked)
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

                tvId.text = model.list.get(0).id.toString()
                tvDate.text =  model.list.get(0).date?.let { Util.formatFecha(it) }
                tvDescription.text = model.list.get(0).description.toString()
                tvAmount.text = model.list.get(0).amount.toString()
                tvFee.text = model.list.get(0).fee.toString()
                tvTotal.text =
                    (model.list.get(0).amount + model.list.get(0).fee).toString()
                if (model.list.get(0).amount >= 0) tvTotal.setTextColor(resources.getColor(R.color.green))
                else
                    tvTotal.setTextColor(
                        resources.getColor(R.color.red)
                    )
            }
        }
    }

}
