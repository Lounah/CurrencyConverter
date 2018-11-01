package com.lounah.currencyconverter.core.executor;

import android.os.Process;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorSupplier {

    private static final int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();

    private final ThreadPoolExecutor backgroundTasksExecutor;

    private final Executor mMainThreadExecutor;

    private static ExecutorSupplier instance;

    public static ExecutorSupplier getInstance() {
        if (instance == null) {
            synchronized (ExecutorSupplier.class) {
                instance = new ExecutorSupplier();
            }
        }
        return instance;
    }

    private ExecutorSupplier() {

        ThreadFactory backgroundPriorityThreadFactory = new
                PriorityThreadFactory(Process.THREAD_PRIORITY_BACKGROUND);

        backgroundTasksExecutor = new ThreadPoolExecutor(
                NUMBER_OF_CORES * 2,
                NUMBER_OF_CORES * 2,
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),
                backgroundPriorityThreadFactory
        );

        mMainThreadExecutor = new MainThreadExecutor();
    }

    public ThreadPoolExecutor forBackgroundTasks() {
        return backgroundTasksExecutor;
    }

    public Executor forMainThreadTasks() {
        return mMainThreadExecutor;
    }
}