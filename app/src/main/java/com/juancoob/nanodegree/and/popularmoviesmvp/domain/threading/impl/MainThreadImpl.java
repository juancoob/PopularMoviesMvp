package com.juancoob.nanodegree.and.popularmoviesmvp.domain.threading.impl;

import android.os.Handler;
import android.os.Looper;

import com.juancoob.nanodegree.and.popularmoviesmvp.domain.threading.MainThread;


/**
 * Created by Juan Antonio Cobos Obrero on 22/03/18.
 */

/* This class makes sure that every runnable we provide will be run on the main UI thread */

public class MainThreadImpl implements MainThread {

    private static MainThread sMainThread = new MainThreadImpl();

    private Handler mHandler;

    private MainThreadImpl() {
        mHandler = new Handler(Looper.getMainLooper());
    }

    public static MainThread getInstane() {
        return sMainThread;
    }

    @Override
    public void post(Runnable runnable) {
        mHandler.post(runnable);
    }
}
