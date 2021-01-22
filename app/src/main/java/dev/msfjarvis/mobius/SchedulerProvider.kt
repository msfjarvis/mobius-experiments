package dev.msfjarvis.mobius

import io.reactivex.Scheduler

interface SchedulerProvider {

    val main: Scheduler

    val io: Scheduler
}
