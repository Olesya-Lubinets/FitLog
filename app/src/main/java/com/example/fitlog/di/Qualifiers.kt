package com.example.fitlog.di

import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class WorkoutOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FoodOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class WorkoutRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FoodRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthRetrofit



