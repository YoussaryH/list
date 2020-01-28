package com.youssary.listaccount.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.youssary.listaccount.R
import com.youssary.listaccount.model.ListRepository
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MoviesAdapter

 /*   private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val adapter = MoviesAdapter {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        launch {
            val listAmount = APIDb.service
                .getItems()
                .await()

            adapter.listAccount = listAmount
        }

        recycler.adapter = adapter
    }

    private suspend fun requestCoarseLocationPermission(): Boolean =
        suspendCancellableCoroutine { continuation ->
            Dexter
                .withActivity(this@MainActivity)
                .withPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                .withListener(object : BasePermissionListener() {
                    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                        continuation.resume(true)
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                        continuation.resume(false)
                    }
                }
                ).check()
        }
        fun validarFecha(fecha: String): Boolean {
        val date = fecha
        val dateformat =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        val output = SimpleDateFormat("dd.MM.yyyy hh:mm")
        var d: Date? = null
        try {
            d = dateformat.parse(date /*your date as String*/)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }
*/
 override fun onCreate(savedInstanceState: Bundle?) {
     super.onCreate(savedInstanceState)
     setContentView(R.layout.activity_main)

     viewModel = ViewModelProviders.of(
         this,
         MainViewModelFactory(ListRepository(this))
     )[MainViewModel::class.java]

     adapter = MoviesAdapter(viewModel::onMovieClicked)
     recycler.adapter = adapter
     //viewModel.model.observe(this, Observer(::updateUi))
     viewModel.model.observe(this, androidx.lifecycle.Observer {  ::updateUi})
 }

    private fun updateUi(model: MainViewModel.UiModel) {

        progress.visibility = if (model is MainViewModel.UiModel.Loading) View.VISIBLE else View.GONE

        when (model) {
            is MainViewModel.UiModel.Content -> adapter.movies = model.movies
        }
    }
}
