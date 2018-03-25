package com.juancoob.nanodegree.and.popularmoviesmvp.util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * Created by Juan Antonio Cobos Obrero on 20/02/18.
 */

public class Constants {
    public static final String MOVIE = "movie";
    public static final String MOVIE_OPTION = "movieOption";
    public static final String MOVIE_LIST = "movieList";
    public static final String MOVIE_DETAIL = "movieDetail";
    public static final String MOVIE_BD_URL = "https://api.themoviedb.org/3/";
    public static final String MOVIE_DB_BASE_IMAGE_URL = "http://image.tmdb.org/t/p/";
    public static final String POPULAR = "popular";
    public static final String TOP = "top";
    public static final int CORE_POOL_SIZE = 3;
    public static final int MAXIMUN_POOL_SIZE = 5;
    public static final long KEEP_ALIVE_TIME = 120L;
    public static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
    public static final BlockingQueue<Runnable> WORK_QUEUE = new LinkedBlockingDeque<>();

}
