package com.utn.primerparciallauria.entities

import androidx.room.*

@Entity(tableName = "users")
class User(id : Int, name : String, email : String, password : String, imgAvatar : String) {
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id : Int

    @ColumnInfo(name = "name")
    var name : String

    @ColumnInfo(name = "email")
    var email : String

    @ColumnInfo(name = "password")
    var password : String

    @ColumnInfo(name = "imgAvatar")
    var imgAvatar : String

    init {
        this.id = id
        this.name = name
        this.email = email
        this.password = password
        this.imgAvatar = imgAvatar
    }
}
