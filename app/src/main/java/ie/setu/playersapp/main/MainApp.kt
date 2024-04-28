package ie.setu.playersapp.main

import android.app.Application

import ie.setu.playersapp.models.PlayerStore
import ie.setu.playersapp.models.PlayersJSONStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    lateinit var players: PlayerStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        //placemarks = PlacemarkMemStore()
        players = PlayersJSONStore(applicationContext)
        i("Placemark started")
    }
}