# Proguard rules for Jarvis App

# Keep all classes in the app package
-keep class com.jarvis.ai.** { *; }

# Keep Compose classes
-keep class androidx.compose.** { *; }

# Keep Material3 classes
-keep class androidx.material3.** { *; }

# Keep Retrofit classes
-keep class retrofit2.** { *; }
-keep class com.squareup.okhttp3.** { *; }

# Keep Gson classes
-keep class com.google.gson.** { *; }
-keep class * implements com.google.gson.JsonDeserializer
-keep class * implements com.google.gson.JsonSerializer

# Keep Room classes
-keep class androidx.room.** { *; }

# Keep Coroutines
-keep class kotlinx.coroutines.** { *; }

# Keep native methods
-keepclasseswithmembernames class * {
    native <methods>;
}

# Keep enums
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Keep Parcelable implementations
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# Keep R classes
-keepclassmembers class **.R$* {
    public static <fields>;
}

# Remove logging
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
}

# Optimization
-optimizationpasses 5
-dontusemixedcaseclassnames
-verbose

# Remove unused code
-dontshrink
-dontoptimize
