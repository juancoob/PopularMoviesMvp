package com.juancoob.nanodegree.and.popularmoviesmvp.presentation.presenters;

import com.juancoob.nanodegree.and.popularmoviesmvp.domain.executor.Executor;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.threading.MainThread;

/**
 * Created by Juan Antonio Cobos Obrero on 22/03/18.
 */

/* This base class for presenters which are communicating with use cases holds a reference to the Executor and
*  MainThread objects that are needed
*/

public abstract class AbstractPresenter {

    protected Executor mExecutor;
    protected MainThread mMainThread;

    public AbstractPresenter(Executor executor, MainThread mainThread) {
        mExecutor = executor;
        mMainThread = mainThread;
    }
}
