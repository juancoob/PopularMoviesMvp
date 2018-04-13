package com.juancoob.nanodegree.and.popularmoviesmvp.domain.executor.impl;

import com.juancoob.nanodegree.and.popularmoviesmvp.domain.executor.Executor;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.usecase.base.AbstractUseCase;
import com.juancoob.nanodegree.and.popularmoviesmvp.util.Constants;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Juan Antonio Cobos Obrero on 20/03/18.
 */

/* This singleton class will make sure that every use case operation gets a background thread */

public class ThreadExecutor implements Executor {

    private static final ThreadExecutor sThreadExecutor = new ThreadExecutor();
    private final ThreadPoolExecutor mThreadPoolExecutor;

    public static ThreadExecutor getInstance() {
        return sThreadExecutor;
    }

    private ThreadExecutor() {
        mThreadPoolExecutor = new ThreadPoolExecutor(
                Constants.CORE_POOL_SIZE,
                Constants.MAXIMUN_POOL_SIZE,
                Constants.KEEP_ALIVE_TIME,
                Constants.TIME_UNIT,
                Constants.WORK_QUEUE);
    }

    @Override
    public void execute(final AbstractUseCase useCase) {
        mThreadPoolExecutor.submit(new Runnable() {
            @Override
            public void run() {
                // Run the main logic
                useCase.run();

                // Mark it as finished
                useCase.onFinished();
            }
        });
    }
}
