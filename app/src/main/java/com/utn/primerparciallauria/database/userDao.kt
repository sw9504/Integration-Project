package com.utn.primerparciallauria.database

import androidx.room.Dao
import androidx.room.Query
import com.utn.primerparciallauria.entities.User
@Dao
public interface userDao {
    @Query("SELECT * FROM users ORDER BY userId")
    fun loadAllUsers(): MutableList<User?>?

    @Query("INSERT INTO users (name, imgAvatar) VALUES (:name, :imgAvatar)")
    fun addUser(name : String, imgAvatar : String)

    @Query("SELECT * FROM users WHERE userId = :userId")
    fun getUser(userId : Int) : User?
}