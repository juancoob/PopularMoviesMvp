package com.juancoob.nanodegree.and.popularmoviesmvp.domain.threading;

/**
 * Created by Juan Antonio Cobos Obrero on 21/03/18.
 */

public interface MainThread {

    /*
    * Make runnable operation run in the main thread.
    *
    * @param runnable The runnable to run
    */
    void post(final Runnable runnable);

}
