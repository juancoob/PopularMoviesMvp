package com.juancoob.nanodegree.and.popularmoviesmvp.domain.executor;

import com.juancoob.nanodegree.and.popularmoviesmvp.domain.usecase.base.AbstractUseCase;

/**
 * Created by Juan Antonio Cobos Obrero on 20/03/18.
 */

public interface Executor {
    /*
    * This method calls to the use case's run method and start it. This should be called on a
    * background thread
    *
    * @param useCase the use case to run
    * */
    void execute(final AbstractUseCase useCase);
}
