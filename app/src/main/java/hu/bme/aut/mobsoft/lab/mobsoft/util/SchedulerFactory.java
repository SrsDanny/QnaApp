package hu.bme.aut.mobsoft.lab.mobsoft.util;

import io.reactivex.Scheduler;

public interface SchedulerFactory {
    Scheduler mainThread();
    Scheduler io();
    Scheduler computation();
}