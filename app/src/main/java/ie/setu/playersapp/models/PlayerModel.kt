package ie.setu.playersapp.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class PlayerModel(var id: Long = 0,
                          var Name: String = "",
                          var lastName: String = "",
                          var age: Int = 0,
                          var playerPosition: String= "",
                          var numberOfBats: Int = 0,
                          var hits: Int = 0,
                          var numberOfgames: Int = 0,
                          var image: Uri = Uri.EMPTY,
                          var lat : Double = 0.0,
                          var lng: Double = 0.0,
                          var zoom: Float = 0f) : Parcelable {
    fun averageHitsPerBat(): Float {
        return if (numberOfBats > 0) hits.toFloat() / numberOfBats else 0.0f
    }
}

@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable