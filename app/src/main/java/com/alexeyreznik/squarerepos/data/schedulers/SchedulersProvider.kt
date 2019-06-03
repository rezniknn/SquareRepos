package com.alexeyreznik.squarerepos.data.schedulers

import io.reactivex.Scheduler

interface SchedulersProvider {
    fun mainThread(): Scheduler
    fun io(): Scheduler
}

