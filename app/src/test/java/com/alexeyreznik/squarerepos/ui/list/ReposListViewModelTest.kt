package com.alexeyreznik.squarerepos.ui.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.alexeyreznik.squarerepos.data.model.Repo
import com.alexeyreznik.squarerepos.data.schedulers.TestSchedulers
import com.alexeyreznik.squarerepos.domain.interactor.GetReposWithBookmarksUseCase
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations


class ReposListViewModelTest {

    private lateinit var reposListViewModel: ReposListViewModel

    @Mock
    private lateinit var getReposWithBookmarksUseCase: GetReposWithBookmarksUseCase

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        reposListViewModel = ReposListViewModel(getReposWithBookmarksUseCase, TestSchedulers())
    }

    @Test
    fun testOnResumeUseCaseTriggered() {
        val reposList = listOf(
            RepoListItem(Repo(0L, "repo0", 0L), false),
            RepoListItem(Repo(1L, "repo1", 1L), true)
        )
        `when`(getReposWithBookmarksUseCase.execute()).thenReturn(Observable.just(reposList))
        val lifecycle = LifecycleRegistry(mock(LifecycleOwner::class.java))
        lifecycle.addObserver(reposListViewModel)


        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)


        verify(getReposWithBookmarksUseCase).execute()
        assertEquals(reposListViewModel.reposList.value, reposList)
    }

    @Test
    fun testOnReloadUseCaseTriggered() {
        val reposList = listOf(
            RepoListItem(Repo(0L, "repo0", 0L), false),
            RepoListItem(Repo(1L, "repo1", 1L), true)
        )
        `when`(getReposWithBookmarksUseCase.execute()).thenReturn(Observable.just(reposList))


        reposListViewModel.reload()


        verify(getReposWithBookmarksUseCase).execute()
        assertEquals(reposListViewModel.reposList.value, reposList)
    }

    @Test
    fun testLoadingIndicatorShownHidden() {
        val reposList = listOf(
            RepoListItem(Repo(0L, "repo0", 0L), false),
            RepoListItem(Repo(1L, "repo1", 1L), true)
        )
        val delayer = PublishSubject.create<Boolean>()
        `when`(getReposWithBookmarksUseCase.execute()).thenReturn(Observable.just(reposList).delaySubscription(delayer))


        reposListViewModel.reload()


        assertEquals(true, reposListViewModel.loading.value)
        delayer.onComplete()
        assertEquals(false, reposListViewModel.loading.value)

    }
}