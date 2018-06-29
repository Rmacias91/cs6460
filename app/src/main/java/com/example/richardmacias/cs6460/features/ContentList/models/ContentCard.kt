package com.example.richardmacias.cs6460.features.ContentList.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "contents")
class ContentCard(@PrimaryKey(autoGenerate = true) var id:Long?,
                  var title:String,
                  var category:String,
                  var description:String,
                  var isArticle:Boolean){

    constructor(title:String, category:String, description:String, isArticle:Boolean) :
            this(null,title,category,description,isArticle)
}

