package ie.setu.playersapp.activities

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

import ie.setu.playersapp.activities.PlayerPresenter
import ie.setu.playersapp.models.PlayerModel
import ie.setu.playersapp.R
import ie.setu.playersapp.databinding.ActivityPlayerBinding

import timber.log.Timber.i

class PlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlayerBinding
    private lateinit var presenter: PlayerPresenter
    var player = PlayerModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.topAppBar.title = title
        setSupportActionBar(binding.topAppBar)


        presenter = PlayerPresenter(this)

        binding.chooseImage.setOnClickListener {
            presenter.cachePlacemark(binding.playerName.text.toString(), binding.playerLastName.text.toString(),binding.playerAge.text.toString().toIntOrNull() ?:0, binding.playerPosition.text.toString()
                ,binding.numberOfBats.text.toString().toIntOrNull() ?:0,binding.hits.text.toString().toIntOrNull() ?:0,binding.numberOfGames.text.toString().toIntOrNull() ?:0)
            presenter.doSelectImage()
        }

       binding.playerLocation.setOnClickListener {
           presenter.cachePlacemark(
               binding.playerName.text.toString(),
               binding.playerLastName.text.toString(),
               binding.playerAge.text.toString().toIntOrNull() ?: 0,
               binding.playerPosition.text.toString(),
               binding.numberOfBats.text.toString().toIntOrNull() ?: 0,
               binding.hits.text.toString().toIntOrNull() ?: 0,
               binding.numberOfGames.text.toString().toIntOrNull() ?: 0
           )
           presenter.doSetLocation()
       }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_player, menu)
        val deleteMenu: MenuItem = menu.findItem(R.id.item_delete)
        deleteMenu.isVisible = presenter.edit
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                if (binding.playerName.text.toString().isEmpty()) {
                    Snackbar.make(binding.root, R.string.enter_placemark_title, Snackbar.LENGTH_LONG)
                        .show()
                } else {
                    presenter.doAddOrSave(binding.playerName.text.toString(), binding.playerLastName.text.toString(),binding.playerAge.text.toString().toIntOrNull() ?:0, binding.playerPosition.text.toString()
                        ,binding.numberOfBats.text.toString().toIntOrNull() ?:0,binding.hits.text.toString().toIntOrNull() ?:0,binding.numberOfGames.text.toString().toIntOrNull() ?:0)
                }
            }
            R.id.item_delete -> {
                presenter.doDelete()
            }
            R.id.item_cancel -> {
                presenter.doCancel()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun showPlacemark(player: PlayerModel) {
        binding.playerName.setText(player.Name)
        binding.playerLastName.setText(player.lastName)
        binding.playerAge.setText(player.age.toString())
        binding.playerPosition.setText(player.playerPosition)
        Picasso.get()
            .load(player.image)
            .into(binding.playerImage)
        if (player.image != Uri.EMPTY) {
            binding.chooseImage.setText(R.string.change_player_image)
        }


    }

    fun updateImage(image: Uri) {
        i("Image updated")
        Picasso.get()
            .load(image)
            .into(binding.playerImage)
        binding.chooseImage.setText(R.string.change_player_image)
    }
}