package uqac.dim.crypturmess.databaseAccess.room.dao.converters;

import androidx.room.TypeConverter;

import java.sql.Date;

public class DateConverter{
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}