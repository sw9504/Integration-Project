package com.utn.primerparciallauria.database
import androidx.room.*
import com.utn.primerparciallauria.entities.Character

@Dao
public interface characterDao {

    @Query("SELECT * FROM characters ORDER BY id")
    fun loadAllCharacters(): MutableList<Character?>?

    @Query("INSERT INTO characters (name, imgAvatar) VALUES (:name, :imgAvatar)")
    fun addCharacter(name : String, imgAvatar : String)

    @Query("SELECT * FROM characters WHERE id = :id")
    fun getCharacter(id : Int) : Character?

    @Query("DELETE FROM characters WHERE id = :id")
    fun deleteCharacter(id : Int)

    @Query("UPDATE characters SET name = :name, imgAvatar = :imgAvatar WHERE id = :id")
    fun updateCharacter(id : Int, name : String, imgAvatar : String)
}