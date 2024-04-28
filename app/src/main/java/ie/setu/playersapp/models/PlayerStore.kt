package ie.setu.playersapp.models

import ie.setu.playersapp.models.PlayerModel

interface PlayerStore {
    fun findAll(): List<PlayerModel>
    fun create(player: PlayerModel)
    fun update(player: PlayerModel)


    fun findById(id: Long): PlayerModel?
    fun delete(player: PlayerModel)
}