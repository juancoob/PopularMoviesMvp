package com.juancoob.nanodegree.and.popularmoviesmvp.presentation.presenters;

/**
 * Created by Juan Antonio Cobos Obrero on 22/03/18.
 */

public interface BasePresenter {

    /* These methods control the view's lifecycle*/

    void resume();
    void pause();
    void stop();
    void destroy();

    /*This method pass the error message to the UI*/
    void onError(String message);
}
