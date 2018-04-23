package com.kubiakpatryk.safely.utils.rx;

import io.reactivex.Scheduler;

public interface SchedulerProviderHelper {

    Scheduler ui();

    Scheduler io();
}
