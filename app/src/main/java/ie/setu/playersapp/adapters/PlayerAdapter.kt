package ie.setu.playersapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ie.setu.playersapp.databinding.CardPlayersBinding
import ie.setu.playersapp.models.PlayerModel

interface PlayerListener {
    fun onPlayerClick(player: PlayerModel, position : Int)
    fun onPlacemarkClick(player: PlayerModel, position: Int)
}

class PlayerAdapter constructor(private var placemarks: List<PlayerModel>,
                                   private val listener: PlayerListener
) :
    RecyclerView.Adapter<PlayerAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardPlayersBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val placemark = placemarks[holder.adapterPosition]
        holder.bind(placemark, listener)
    }

    override fun getItemCount(): Int = placemarks.size

    class MainHolder(private val binding : CardPlayersBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(player: PlayerModel, listener: PlayerListener) {
            binding.playerName.text = player.Name
            binding.playerLastName.text = player.lastName
            binding.playerAge.text = player.age.toString()
            binding.playerPosition.text = player.playerPosition
            Picasso.get().load(player.image).resize(200,200).into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onPlayerClick(player,adapterPosition) }
        }
    }
}