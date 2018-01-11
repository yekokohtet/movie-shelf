package com.padcmyanmar.movieshelf.data.persistent;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yekokohtet on 12/20/17.
 */

public class MovieDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "movies.db";

    private static final String SQL_CREATE_POPULAR_MOVIE_TABLE = "CREATE TABLE " + MovieContract.PopularMovieEntry.TABLE_NAME + " (" +
            MovieContract.PopularMovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MovieContract.PopularMovieEntry.COLUMN_VOTE_COUNT + " INTEGER, " +
            MovieContract.PopularMovieEntry.COLUMN_POPULAR_MOVIE_ID + " VARCHAR (256), " +
            MovieContract.PopularMovieEntry.COLUMN_VIDEO + " INTEGER DEFAULT 0, " +
            MovieContract.PopularMovieEntry.COLUMN_VOTE_AVERAGE + " REAL, " +
            MovieContract.PopularMovieEntry.COLUMN_TITLE + " TEXT, " +
            MovieContract.PopularMovieEntry.COLUMN_POPULARITY + " REAL, " +
            MovieContract.PopularMovieEntry.COLUMN_POSTER_PATH + " TEXT, " +
            MovieContract.PopularMovieEntry.COLUMN_ORIGINAL_LANGUAGE + " TEXT, " +
            MovieContract.PopularMovieEntry.COLUMN_ORIGINAL_TITLE + " TEXT, " +
            MovieContract.PopularMovieEntry.COLUMN_BACKDROP_PATH + " TEXT, " +
            MovieContract.PopularMovieEntry.COLUMN_ADULT + " INTEGER DEFAULT 0, " +
            MovieContract.PopularMovieEntry.COLUMN_OVERVIEW + " TEXT, " +
            MovieContract.PopularMovieEntry.COLUMN_RELEASE_DATE + " TEXT, " +

            " UNIQUE (" + MovieContract.PopularMovieEntry.COLUMN_TITLE + ") ON CONFLICT REPLACE" +
            " );";


    private static final String SQL_CREATE_GENRE_ID_TABLE = "CREATE TABLE " + MovieContract.GenreIdEntry.TABLE_NAME + " (" +
            MovieContract.GenreIdEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MovieContract.GenreIdEntry.COLUMN_GENRE_ID + " VARCHAR (256), " +

            " UNIQUE (" + MovieContract.GenreIdEntry.COLUMN_GENRE_ID + ") ON CONFLICT REPLACE" +
            " );";

    private static final String SQL_CREATE_POPLAR_MOVIE_GENRE_ID_TABLE = "CREATE TABLE " + MovieContract.PopularMovieGenreIdEntry.TABLE_NAME + " (" +
            MovieContract.PopularMovieGenreIdEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MovieContract.PopularMovieGenreIdEntry.COLUMN_POPULAR_MOVIE_ID + " VARCHAR (256), " +
            MovieContract.PopularMovieGenreIdEntry.COLUMN_GENRE_ID + " INTEGER, " +

            " UNIQUE (" + MovieContract.PopularMovieGenreIdEntry.COLUMN_GENRE_ID + ", " +
            MovieContract.PopularMovieGenreIdEntry.COLUMN_POPULAR_MOVIE_ID + ") ON CONFLICT REPLACE" +
            " );";

    public MovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_POPULAR_MOVIE_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_GENRE_ID_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_POPLAR_MOVIE_GENRE_ID_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContract.PopularMovieEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContract.GenreIdEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContract.PopularMovieGenreIdEntry.TABLE_NAME);

        onCreate(sqLiteDatabase);
    }
}
