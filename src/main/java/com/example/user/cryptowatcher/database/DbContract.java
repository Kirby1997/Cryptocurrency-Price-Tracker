package com.example.jacob.cryptowatcher.database;

import android.provider.BaseColumns;

public class DbContract {

    public static final class CoinEntry implements BaseColumns{
        // All of the columns in the database
        public static final String TABLE_NAME = "coins";
        public static final String IDD = "idd";
        public static final String COLUMN_ID = "id"; // eg. bitcoin
        public static final String COLUMN_NAME = "name"; // eg. Bitcoin
        public static final String COLUMN_SYMBOL = "symbol"; // eg. BTC
        public static final String COLUMN_RANK = "rank"; // eg. 1
        public static final String COLUMN_PRICEUSD = "price_usd"; // eg. 6000
        public static final String COLUMN_PRICEBTC = "price_btc"; // eg. 1
        public static final String COLUMN_VOLUME = "volume"; // eg. 3900000000
        public static final String COLUMN_MARKETCAP = "market_cap_usd"; // eg. 116621100148
        public static final String COLUMN_AVAILABLESUPPLY = "available_supply"; // eg. 16962625
        public static final String COLUMN_TOTALSUPPLY = "total_supply"; // eg. 1692625
        public static final String COLUMN_MAXSUPPLY = "max_supply"; // eg. 21000000
        public static final String COLUMN_CHANGE1H = "percent_change_1h"; // eg. 0.25
        public static final String COLUMN_CHANGE24H = "percent_change_24h"; // eg. 2.34
        public static final String COLUMN_CHANGE7D = "percent_change_7d"; // eg. -2.04
        public static final String COLUMN_UPDATED = "last_updated"; // eg. 1523081668 unix time stamp
        public static final String COLUMN_ENABLED = "enabled"; // Whether it syncs from CoinMarketCap



    }
}
