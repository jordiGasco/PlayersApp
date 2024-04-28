package ie.setu.playersapp.models
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var username: String = ""
) : Parcelable