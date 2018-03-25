package com.juancoob.nanodegree.and.popularmoviesmvp.domain.usecase.base;

import com.juancoob.nanodegree.and.popularmoviesmvp.domain.executor.Executor;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.threading.MainThread;

/**
 * Created by Juan Antonio Cobos Obrero on 21/03/18.
 */

public abstract class AbstractUseCase implements UseCase {

    protected Executor mThreadExecutor;
    protected MainThread mMainThread;

    /* These variables are volatiles because we are going to manage them on different threads */
    protected volatile boolean mIsCanceled;
    protected volatile boolean mIsRunning;

    public AbstractUseCase(Executor executor, MainThread mainThread) {
        mThreadExecutor = executor;
        mMainThread = mainThread;
    }

    public abstract void run();

    public void cancel() {
        mIsCanceled = true;
        mIsRunning = false;
    }

    public boolean isRunning() {
        return mIsRunning;
    }

    public void onFinished() {
        mIsRunning = false;
        mIsCanceled = false;
    }

    @Override
    public void execute() {
        // Mark this use case as running
        mIsRunning = true;

        // Start running this use case in a background thread
        mThreadExecutor.execute(this);
    }
}
