package com.alexeyreznik.squarerepos.data.schedulers

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DefaultAndroidSchedulers : SchedulersProvider {

    override fun mainThread(): Scheduler = AndroidSchedulers.mainThread()

    override fun io(): Scheduler = Schedulers.io()

}