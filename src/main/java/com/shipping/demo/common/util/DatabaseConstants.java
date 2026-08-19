package com.shipping.demo.common.util;

public class DatabaseConstants {

    public static final class SchemaName {
        public static final String SHIPPING = "shipping";
    }

    public static final class TableName {
        public static final String CLIENT = "clients";
        public static final String EVENT = "events";
        public static final String PARCEL = "parcels";
        public static final String TRACKING = "trackings";
    }

    public static final class FieldName {
        public static final String ID = "id";

        public static final class Client {
            public static final String CLIENT_NAME = "client_name";
            public static final String ADDRESS = "address";
            public static final String ZIPCODE = "zipcode";
            public static final String CITY = "city";
            public static final String PHONE = "phone";
            public static final String EMAIL = "email";
        }

        public static final class Event {
            public static final String EVENT_NAME = "event_name";
        }

        public static final class Parcel {
            public static final String SENDER_ID = "sender_id";
            public static final String RECEIVER_ID = "receiver_id";
            public static final String PARCEL_NO = "parcel_no";
        }

        public static final class Tracking {
            public static final String PARCEL_ID = "parcel_id";
            public static final String EVENT_DATE = "event_date";
            public static final String EVENT_ID = "event_id";
        }
    }

}
