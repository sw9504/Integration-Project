package com.utn.primerparciallauria.database

import androidx.room.Dao
import androidx.room.Query
import com.utn.primerparciallauria.entities.User
@Dao
public interface userDao {
    @Query("SELECT * FROM users ORDER BY userId")
    fun loadAllUsers(): MutableList<User?>?

    @Query("INSERT INTO users (name, email, password, imgAvatar) VALUES (:name, :email, :password, :imgAvatar)")
    fun addUser(name : String, email : String, password : String, imgAvatar : String)

    @Query("SELECT * FROM users WHERE userId = :userId")
    fun getUser(userId : Int) : User?

    @Query("SELECT userId FROM users WHERE email = :email")
    fun getUserId(email : String) : Int

    @Query("SELECT password FROM users WHERE email = :email")
    fun getUserPass(email : String) : String
}