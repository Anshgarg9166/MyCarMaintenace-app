# Room specific rules
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-dontwarn androidx.room.paging.**

# Keep Room schema
-keep class * extends androidx.room.RoomDatabase {
    public static <fields>;
    public static <methods>;
}

# Keep Room DAOs
-keep class * extends androidx.room.Dao {
    public <methods>;
}

# Keep Room entities
-keep class * extends androidx.room.Entity {
    public <fields>;
    public <methods>;
}

# Keep Room type converters
-keep class * extends androidx.room.TypeConverter {
    public <methods>;
} 