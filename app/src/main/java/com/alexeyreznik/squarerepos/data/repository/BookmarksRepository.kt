package com.alexeyreznik.squarerepos.data.repository

import android.content.SharedPreferences

interface BookmarksRepository {
    fun getBookmarkedIds(): List<Long>
    fun isBookmarked(id: Long): Boolean
    fun addBookmark(id: Long)
    fun removeBookmark(id: Long)
}

class BookmarksRepositorySharedPreferencesImpl(private val sharedPreferences: SharedPreferences) : BookmarksRepository {

    override fun getBookmarkedIds(): List<Long> {
        return sharedPreferences.getStringSet(KEY_BOOKMARKS, null)
            ?.map { id -> id.toLong() }
            ?: emptyList()
    }

    override fun isBookmarked(id: Long): Boolean {
        return sharedPreferences.getStringSet(KEY_BOOKMARKS, null)
            ?.contains(id.toString()) == true
    }

    override fun addBookmark(id: Long) {
        val bookmarks = sharedPreferences.getStringSet(KEY_BOOKMARKS, null) ?: emptySet()
        val bookmarksMutable = bookmarks.toMutableSet()
        bookmarksMutable.add(id.toString())
        sharedPreferences.edit().putStringSet(KEY_BOOKMARKS, bookmarksMutable).apply()

    }

    override fun removeBookmark(id: Long) {
        val bookmarks = sharedPreferences.getStringSet(KEY_BOOKMARKS, null) ?: emptySet()
        val bookmarksMutable = bookmarks.toMutableSet()
        bookmarksMutable.remove(id.toString())
        sharedPreferences.edit().putStringSet(KEY_BOOKMARKS, bookmarksMutable).apply()
    }

    companion object {
        const val KEY_BOOKMARKS = "bookmarks"
    }
}