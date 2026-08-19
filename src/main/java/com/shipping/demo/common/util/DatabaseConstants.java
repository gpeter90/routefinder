package com.shipping.demo.common.util;

public class DatabaseConstants {

    public static final class SchemaName {
        public static final String ROUTEFINDER = "routefinder";
    }

    public static final class TableName {
        public static final String CITIES = "cities";
        public static final String ROUTES = "routes";
    }

    public static final class FieldName {
        public static final String ID = "id";

        public static final class City {
            public static final String CITY_NAME = "city_name";
            public static final String COUNTRY = "country";
            public static final String CONTINENT = "continent";
        }

        public static final class Route {
            public static final String DEPARTURE_CITY_ID = "departure_city_id";
            public static final String ARRIVAL_CITY_ID = "arrival_city_id";
            public static final String DISTANCE_KM = "distance_km";
            public static final String TRAVEL_TIME_MIN = "travel_time_min";
        }
    }

}
