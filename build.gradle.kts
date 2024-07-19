// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    extra.apply {
        set("nav_version", "2.7.7")
        set("hilt_version", "2.48.1")
        set("room_version", "2.6.1")
        set("retrofit_version", "2.9.0")
        set("room_version", "2.6.0")
    }
}
plugins {
    id("com.android.application") version "8.2.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.20" apply false
    id("com.google.dagger.hilt.android") version "2.48.1" apply false
}