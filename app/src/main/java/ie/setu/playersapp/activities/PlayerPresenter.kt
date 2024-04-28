package ie.setu.playersapp.activities

import android.app.Activity.RESULT_OK
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import ie.setu.playersapp.editLocation.EditLocationView
import ie.setu.playersapp.helpers.showImagePicker
import ie.setu.playersapp.main.MainApp
import ie.setu.playersapp.models.Location
import ie.setu.playersapp.models.PlayerModel
// import ie.setu.mobileassignment.views.editLocation.EditLocationView
import timber.log.Timber

class PlayerPresenter(private val view: PlayerActivity) {

    var player = PlayerModel()
    var app: MainApp = view.application as MainApp
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    var edit = false;

    init {
        if (view.intent.hasExtra("placemark_edit")) {
            edit = true
            player = view.intent.extras?.getParcelable("placemark_edit")!!
            view.showPlacemark(player)
        }
        registerImagePickerCallback()
        registerMapCallback()
    }
    fun doAddOrSave(title: String, description: String,age: Int, playerPosition: String,numberOfBats: Int,hits: Int,numberOfgames: Int) {
        player.Name = title
        player.lastName = description
        player.age = age
        player.playerPosition = playerPosition
        player.numberOfBats = numberOfBats
        player.hits= hits
        player.numberOfgames = numberOfgames



        if (edit) {
            app.players.update(player)
        } else {
            app.players.create(player)
        }
        view.setResult(RESULT_OK)
        view.finish()


    }
    fun doCancel() {
        view.finish()
    }
    fun doDelete() {
        view.setResult(99)
        app.players.delete(player)
        view.finish()
    }
    fun doSelectImage() {
        showImagePicker(imageIntentLauncher,view)
    }
   fun doSetLocation() {
        val location = Location(52.245696, -7.139102, 15f)
        if (player.zoom != 0f) {
            location.lat =  player.lat
            location.lng = player.lng
            location.zoom = player.zoom
        }
        val launcherIntent = Intent(view, EditLocationView::class.java)
            .putExtra("location", location)
        mapIntentLauncher.launch(launcherIntent)
    }
    fun cachePlacemark (title: String, description: String,age: Int,playerPosition: String,numberOfBats: Int,hits: Int,numberOfgames: Int) {
        player.Name = title
        player.lastName = description
        player.age = age
        player.playerPosition = playerPosition
        player.numberOfBats = numberOfBats
        player.hits = hits
        player.numberOfgames = numberOfgames
    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Result ${result.data!!.data}")
                            player.image = result.data!!.data!!
                            view.contentResolver.takePersistableUriPermission(player.image,
                                Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            view.updateImage(player.image)
                        } // end of if
                    }
                    AppCompatActivity.RESULT_CANCELED -> { } else -> { }
                }            }    }
    private fun registerMapCallback() {
        mapIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Location ${result.data.toString()}")
                            val location = result.data!!.extras?.getParcelable<Location>("location")!!
                            Timber.i("Location == $location")
                            player.lat = location.lat
                            player.lng = location.lng
                            player.zoom = location.zoom
                        } // end of if
                    }
                    AppCompatActivity.RESULT_CANCELED -> { } else -> { }
                }
            }
    }
}