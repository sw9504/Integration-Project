package com.utn.primerparciallauria.entities

import androidx.room.*

@Entity(tableName = "users")
class User(userId : Int, name : String, email : String, password : String, bio : String, imgAvatar : String) {
    @PrimaryKey
    @ColumnInfo(name = "userId")
    var userId : Int

    @ColumnInfo(name = "name")
    var name : String

    @ColumnInfo(name = "email")
    var email : String

    @ColumnInfo(name = "password")
    var password : String

    @ColumnInfo(name = "bio")
    var bio : String

    @ColumnInfo(name = "imgAvatar")
    var imgAvatar : String

    init {
        this.userId = userId
        this.name = name
        this.email = email
        this.password = password
        this.bio = bio
        this.imgAvatar = imgAvatar
    }
}