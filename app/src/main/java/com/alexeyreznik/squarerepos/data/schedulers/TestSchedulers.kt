package com.alexeyreznik.squarerepos.data.schedulers

import io.reactivex.schedulers.Schedulers

class TestSchedulers : SchedulersProvider {

    override fun mainThread() = Schedulers.trampoline()

    override fun io() = Schedulers.trampoline()
}