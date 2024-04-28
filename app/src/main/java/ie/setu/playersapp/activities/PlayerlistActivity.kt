package ie.setu.playersapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import ie.setu.playersapp.R
import ie.setu.playersapp.adapters.PlayerAdapter
import ie.setu.playersapp.adapters.PlayerListener
// import ie.setu.playersapp.views.placemarkList.PlacemarkListener
import ie.setu.playersapp.databinding.ActivityListplayersBinding
import ie.setu.playersapp.main.MainApp
import ie.setu.playersapp.models.PlayerModel

class PlayerlistActivity : AppCompatActivity(), PlayerListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityListplayersBinding
    lateinit var presenter: PlayerListPresenter
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListplayersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.topAppBar.title = title
        setSupportActionBar(binding.topAppBar)
        presenter = PlayerListPresenter(this)
        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        loadPlacemarks()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> { presenter.doAddPlacemark() }
           // R.id.item_map -> { presenter.doShowPlacemarksMap() }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPlayerClick(player: PlayerModel, position: Int) {
        this.position = position
        presenter.doEditPlacemark(player, this.position)
    }

    override fun onPlacemarkClick(player: PlayerModel, position: Int) {
        this.position = position
        presenter.doEditPlacemark(player, this.position)
    }

    private fun loadPlacemarks() {
        binding.recyclerView.adapter = PlayerAdapter(presenter.getPlacemarks(), this)
        onRefresh()
    }

    fun onRefresh() {
        binding.recyclerView.adapter?.
        notifyItemRangeChanged(0,presenter.getPlacemarks().size)
    }

    fun onDelete(position : Int) {
        binding.recyclerView.adapter?.notifyItemRemoved(position)
    }
}


