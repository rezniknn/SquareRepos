package com.alexeyreznik.squarerepos.domain.interactor

import com.alexeyreznik.squarerepos.data.api.GithubService
import com.alexeyreznik.squarerepos.data.model.Repo
import com.alexeyreznik.squarerepos.data.repository.BookmarksRepository
import com.alexeyreznik.squarerepos.ui.list.RepoListItem
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class GetReposWithBookmarksUseCaseImplTest {

    private lateinit var getReposWithBookmarksUseCase: GetReposWithBookmarksUseCaseImpl

    @Mock
    private lateinit var githubService: GithubService

    @Mock
    private lateinit var bookmarksRepository: BookmarksRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getReposWithBookmarksUseCase = GetReposWithBookmarksUseCaseImpl(githubService, bookmarksRepository)
    }

    @Test
    fun testExecuteEmptyBookmarks() {
        val repo0 = Repo(0L, "repo0", 0L)
        val repo1 = Repo(1L, "repo1", 1L)
        val repos = listOf(repo0, repo1)
        `when`(githubService.getSquareRepos()).thenReturn(Single.just(repos))
        `when`(bookmarksRepository.getBookmarkedIds()).thenReturn(listOf())


        val testObserver = getReposWithBookmarksUseCase.execute().test()


        testObserver.assertNoErrors()
        testObserver.assertValue(listOf(RepoListItem(repo0, false), RepoListItem(repo1, false)))
    }

    @Test
    fun testExecuteBookmarkedRepo() {
        val repo0 = Repo(0L, "repo0", 0L)
        val repo1 = Repo(1L, "repo1", 1L)
        val repos = listOf(repo0, repo1)
        `when`(githubService.getSquareRepos()).thenReturn(Single.just(repos))
        `when`(bookmarksRepository.getBookmarkedIds()).thenReturn(listOf(1L))


        val testObserver = getReposWithBookmarksUseCase.execute().test()


        testObserver.assertNoErrors()
        testObserver.assertValue(listOf(RepoListItem(repo0, false), RepoListItem(repo1, true)))
    }
}