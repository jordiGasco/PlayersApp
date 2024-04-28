package ie.setu.mobileassignment.map

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.squareup.picasso.Picasso
import ie.setu.playersapp.databinding.ActivityPlayerMapsBinding
import ie.setu.playersapp.databinding.ContentPlayerMapsBinding
import ie.setu.playersapp.main.MainApp
import ie.setu.playersapp.map.PlayerMapPresenter
import ie.setu.playersapp.models.PlayerModel

class PlayerMapView : AppCompatActivity() , GoogleMap.OnMarkerClickListener{

    private lateinit var binding: ActivityPlayerMapsBinding
    private lateinit var contentBinding: ContentPlayerMapsBinding
    lateinit var app: MainApp
    lateinit var presenter: PlayerMapPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = application as MainApp
        binding = ActivityPlayerMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        presenter = PlayerMapPresenter(this)

        contentBinding = ContentPlayerMapsBinding.bind(binding.root)

        contentBinding.mapView.onCreate(savedInstanceState)
        contentBinding.mapView.getMapAsync{
            presenter.doPopulateMap(it)
        }
    }
    fun showPlacemark(player: PlayerModel) {
        contentBinding.currentTitle.text = player.Name
        contentBinding.currentDescription.text = player.lastName
        Picasso.get()
            .load(player.image)
            .into(contentBinding.currentImage)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        presenter.doMarkerSelected(marker)
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        contentBinding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        contentBinding.mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        contentBinding.mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        contentBinding.mapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        contentBinding.mapView.onSaveInstanceState(outState)
    }
}