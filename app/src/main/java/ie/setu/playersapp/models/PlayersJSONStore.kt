package ie.setu.playersapp.models

import android.content.Context
import android.net.Uri

import com.google.gson.*
import com.google.gson.reflect.TypeToken
import ie.setu.playersapp.helpers.*

import ie.setu.playersapp.models.PlayerModel
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*

const val JSON_FILE = "players.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<PlayerModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class PlayersJSONStore(private val context: Context) : PlayerStore {

    var players = mutableListOf<PlayerModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<PlayerModel> {
        logAll()
        return players
    }

    override fun findById(id:Long) : PlayerModel? {
        val foundPlayer: PlayerModel? = players.find { it.id == id }
        return foundPlayer
    }

    override fun create(player: PlayerModel) {
        player.id = generateRandomId()
        players.add(player)
        serialize()
    }


    override fun update(player: PlayerModel) {
        val playersList = findAll() as ArrayList<PlayerModel>
        val foundPlayer: PlayerModel? = playersList.find { p -> p.id == player.id }
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
        }
        serialize()
    }



    override fun delete(player: PlayerModel) {
        players.remove(player)
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(players, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        players = gsonBuilder.fromJson(jsonString, listType)
    }

    private fun logAll() {
        players.forEach { Timber.i("$it") }
    }
}

class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}