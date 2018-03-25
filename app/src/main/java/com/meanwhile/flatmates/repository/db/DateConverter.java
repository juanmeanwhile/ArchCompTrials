package com.meanwhile.flatmates.repository.db;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * TypeConverter for Date objects
 */

public class DateConverter {
    @TypeConverter
    public static Long fromDate(Date date) {
        if (date==null) {
            return(null);
        }

        return(date.getTime());
    }

    @TypeConverter
    public static Date toDate(Long millisSinceEpoch) {
        if (millisSinceEpoch==null) {
            return(null);
        }

        return(new Date(millisSinceEpoch));
    }
}
