package com.example.shethpath

import com.parse.ParseClassName
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseUser

@ParseClassName("Post")
class Post: ParseObject() {

    fun getDescription(): String? {
        return getString(KEY_DESCRIPTION)
    }

    fun setDescription( description: String) {
        put(KEY_DESCRIPTION, description)
    }

    fun getImage(): ParseFile? {
        return getParseFile(KEY_IMAGE)
    }

    fun setImage(parseFile: ParseFile) {
        put(KEY_IMAGE, parseFile)
    }

    fun getUser(): ParseUser? {
        return getParseUser(KEY_USER)
    }

    fun setUser(parseUser: ParseUser) {
        put(KEY_USER, parseUser)
    }

    fun getId(): String? {
        return getString(KEY_ID)
    }

    fun getLike(): Int {
        return getInt(KEY_LIKECOUNT)
    }

    fun setLike(likeCount: Int) {
        put(KEY_LIKECOUNT, likeCount)
    }




    companion object {
        const val KEY_DESCRIPTION = "description"
        const val KEY_USER = "user"
        const val KEY_IMAGE = "image"
        const val KEY_ID = "objectId"
        const val KEY_LIKECOUNT = "likeCount"

    }
}