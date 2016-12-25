package com.example.daman.capstone.data;

import ckm.simple.sql_provider.UpgradeScript;
import ckm.simple.sql_provider.annotation.ProviderConfig;
import ckm.simple.sql_provider.annotation.SimpleSQLConfig;

/**
 * Created by daman on 11/12/16.
 */

@SimpleSQLConfig(
        name = "FavouritesProvider",
        authority = "com.example.daman.capstone",
        database = "favourites.db",
        version = 1)

public class FavProvider implements ProviderConfig {

    @Override
    public UpgradeScript[] getUpdateScripts() {
        return new UpgradeScript[0];
    }

}