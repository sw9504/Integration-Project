package com.utn.primerparciallauria.entities

import androidx.room.*

@Entity(tableName = "characters")
class Character(characterId : Int, name : String, imgAvatar : String) {
    @PrimaryKey
    @ColumnInfo(name = "characterId")
    var characterId : Int

    @ColumnInfo(name = "name")
    var name : String

    @ColumnInfo(name = "imgAvatar")
    var imgAvatar : String

    init {
        this.characterId = characterId
        this.name = name
        this.imgAvatar = imgAvatar
    }
}
