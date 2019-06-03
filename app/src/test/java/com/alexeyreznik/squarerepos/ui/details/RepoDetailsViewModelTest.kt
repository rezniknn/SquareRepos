package com.alexeyreznik.squarerepos.ui.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.alexeyreznik.squarerepos.data.model.Repo
import com.alexeyreznik.squarerepos.data.repository.BookmarksRepository
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class RepoDetailsViewModelTest {

    private val repoId = 0L

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var bookmarksRepository: BookmarksRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testAddBookmark() {
        `when`(bookmarksRepository.isBookmarked(repoId)).thenReturn(false)
        val repo = Repo(repoId, "repo0", 0L)
        val repoDetailsViewModel = RepoDetailsViewModel(repo, bookmarksRepository)
        assertEquals(true, repoDetailsViewModel.addBookmarkButtonEnabled.value)
        assertEquals(false, repoDetailsViewModel.removeBookmarkButtonEnabled.value)

        repoDetailsViewModel.addBookmark()


        verify(bookmarksRepository).addBookmark(repoId)
        assertEquals(false, repoDetailsViewModel.addBookmarkButtonEnabled.value)
        assertEquals(true, repoDetailsViewModel.removeBookmarkButtonEnabled.value)
    }

    @Test
    fun testRemoveBookmark() {
        `when`(bookmarksRepository.isBookmarked(repoId)).thenReturn(true)
        val repo = Repo(repoId, "repo0", 0L)
        val repoDetailsViewModel = RepoDetailsViewModel(repo, bookmarksRepository)
        assertEquals(false, repoDetailsViewModel.addBookmarkButtonEnabled.value)
        assertEquals(true, repoDetailsViewModel.removeBookmarkButtonEnabled.value)


        repoDetailsViewModel.removeBookmark()


        verify(bookmarksRepository).removeBookmark(repoId)
        assertEquals(true, repoDetailsViewModel.addBookmarkButtonEnabled.value)
        assertEquals(false, repoDetailsViewModel.removeBookmarkButtonEnabled.value)
    }
}