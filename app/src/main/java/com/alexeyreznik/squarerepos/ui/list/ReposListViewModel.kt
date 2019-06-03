package com.alexeyreznik.squarerepos.ui.list

import androidx.lifecycle.*
import com.alexeyreznik.squarerepos.data.schedulers.SchedulersProvider
import com.alexeyreznik.squarerepos.domain.interactor.GetReposWithBookmarksUseCase
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class ReposListViewModel(
    private val getReposWithBookmarksUseCase: GetReposWithBookmarksUseCase,
    private val schedulersProvider: SchedulersProvider
) : ViewModel(), LifecycleObserver {

    val reposList = MutableLiveData<List<RepoListItem>>()
    val loading = MutableLiveData<Boolean>()

    private val disposable = CompositeDisposable()


    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun reload() {
        disposable.add(
            getReposWithBookmarksUseCase.execute()
                .doOnSubscribe { loading.postValue(true) }
                .doFinally { loading.postValue(false) }
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.mainThread())
                .subscribe(
                    { list -> reposList.value = list },
                    { error ->
                        Timber.e(error)
                        reposList.value = emptyList()
                    })
        )
    }
}