package com.utn.primerparciallauria.database

import androidx.room.*
import com.utn.primerparciallauria.entities.Character

@Dao
public interface characterDao {

    @Query("SELECT * FROM characters WHERE userId = :userId ORDER BY characterId")
    fun loadAllCharacters(userId : Int): MutableList<Character?>?

    @Query("INSERT INTO characters (userId, name, imgAvatar) VALUES (:userId, :name, :imgAvatar)")
    fun addCharacter(userId : Int, name : String, imgAvatar : String)

    @Query("SELECT * FROM characters WHERE characterId = :characterId")
    fun getCharacter(characterId : Int) : Character?

    @Query("DELETE FROM characters WHERE characterId = :characterId")
    fun deleteCharacter(characterId : Int)

    @Query("UPDATE characters SET name = :name, imgAvatar = :imgAvatar WHERE characterId = :characterId")
    fun updateCharacter(characterId : Int, name : String, imgAvatar : String)
}