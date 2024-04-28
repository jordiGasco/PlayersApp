package ie.setu.playersapp.models

import ie.setu.playersapp.models.PlayerModel
import timber.log.Timber.i

var lastId = 0L
internal fun getId() = lastId++

class PlayerMemStore : PlayerStore {

    val players = ArrayList<PlayerModel>()

    override fun findAll(): List<PlayerModel> {
        return players
    }

    override fun findById(id:Long) : PlayerModel? {
        val foundPlayer: PlayerModel? = players.find { it.id == id }
        return foundPlayer
    }

    override fun create(player: PlayerModel) {
        player.id = getId()
        players.add(player)
        i("Created placemark with average hits per bat: ${player.averageHitsPerBat()}")
        logAll()
    }

    override fun update(player: PlayerModel) {
        val foundPlayer: PlayerModel? = players.find { p -> p.id == player.id }
        if (foundPlayer != null) {
            foundPlayer.Name = player.Name
            foundPlayer.lastName = player.lastName
            foundPlayer.age = player.age
            foundPlayer.playerPosition = player.playerPosition
            foundPlayer.numberOfBats = player.numberOfBats
            foundPlayer.hits = player.hits
            foundPlayer.numberOfgames = player.numberOfgames
            foundPlayer.image = player.image
            foundPlayer.lat = player.lat
            foundPlayer.lng = player.lng
            foundPlayer.zoom = player.zoom
            i("Updated placemark with average hits per bat: ${foundPlayer.averageHitsPerBat()}")
            logAll()
        }
    }

    override fun delete(player: PlayerModel) {
        players.remove(player)
    }

    private fun logAll() {
        players.forEach { i("$it") }
    }


}
