package ie.setu.playersapp.activities

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
//import ie.setu.mobileassignment.views.map.PlacemarkMapView

import ie.setu.playersapp.models.PlayerModel
import ie.setu.playersapp.main.MainApp

class PlayerListPresenter(val view: PlayerlistActivity) {

    var app: MainApp
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    private var position: Int = 0

    init {
        app = view.application as MainApp
        registerMapCallback()
        registerRefreshCallback()
    }

    fun getPlacemarks() = app.players.findAll()

    fun doAddPlacemark() {
        val launcherIntent = Intent(view, PlayerActivity::class.java)
        refreshIntentLauncher.launch(launcherIntent)
    }

    fun doEditPlacemark(placemark: PlayerModel, pos: Int) {
        val launcherIntent = Intent(view, PlayerActivity::class.java)
        launcherIntent.putExtra("placemark_edit", placemark)
        position = pos
        refreshIntentLauncher.launch(launcherIntent)
    }

    /*fun doShowPlacemarksMap() {
        val launcherIntent = Intent(view, PlacemarkMapView::class.java)
        mapIntentLauncher.launch(launcherIntent)
    }*/

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            view.registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()
            ) {
                if (it.resultCode == Activity.RESULT_OK)
                    view.onRefresh()
                else if (it.resultCode == 99)   // Deleting
                    view.onDelete(position)
            }
    }
    private fun registerMapCallback() {
        mapIntentLauncher = view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        {
        }
    }
}