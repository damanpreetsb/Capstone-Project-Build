package com.example.daman.capstone.data;

import ckm.simple.sql_provider.annotation.SimpleSQLColumn;
import ckm.simple.sql_provider.annotation.SimpleSQLTable;

/**
 * Created by daman on 11/12/16.
 */

@SimpleSQLTable(
        table = "Favourites",
        provider = "FavouritesProvider")

public class FavDBHelper {

    @SimpleSQLColumn(value = FavContract.COLUMN_URL, primary = true)
    public String url;

    @SimpleSQLColumn(FavContract.COLUMN_TITLE)
    public String title;

    @SimpleSQLColumn(FavContract.COLUMN_DESCRIPTION)
    public String description;

    @SimpleSQLColumn(FavContract.COLUMN_AUTHOR)
    public String author;

    @SimpleSQLColumn(FavContract.COLUMN_IMAGE)
    public String image;

    @SimpleSQLColumn(FavContract.COLUMN_DATE)
    public String date;

}