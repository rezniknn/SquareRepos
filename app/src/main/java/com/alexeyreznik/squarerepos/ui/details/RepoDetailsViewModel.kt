package com.alexeyreznik.squarerepos.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexeyreznik.squarerepos.data.model.Repo
import com.alexeyreznik.squarerepos.data.repository.BookmarksRepository

class RepoDetailsViewModel(
    private val repo: Repo,
    private val bookmarksRepository: BookmarksRepository
) : ViewModel() {

    val name = MutableLiveData<String>()
    val starsCount = MutableLiveData<String>()
    val addBookmarkButtonEnabled = MutableLiveData<Boolean>()
    val removeBookmarkButtonEnabled = MutableLiveData<Boolean>()

    init {
        name.postValue(repo.name)
        starsCount.postValue(repo.stars.toString())
        updateButtonsVisibility(bookmarksRepository.isBookmarked(repo.id))
    }

    fun addBookmark() {
        bookmarksRepository.addBookmark(repo.id)
        updateButtonsVisibility(true)
    }

    fun removeBookmark() {
        bookmarksRepository.removeBookmark(repo.id)
        updateButtonsVisibility(false)
    }

    private fun updateButtonsVisibility(isBookmarked: Boolean) {
        addBookmarkButtonEnabled.postValue(!isBookmarked)
        removeBookmarkButtonEnabled.postValue(isBookmarked)
    }

}