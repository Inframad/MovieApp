package com.example.movieapp.di

import android.content.Context
import com.example.movieapp.di.data.network.NetworkModule
import com.example.movieapp.di.ui.UIBindsModule
import com.example.movieapp.ui.MainActivity
import com.example.movieapp.ui.MoviesFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        UIBindsModule::class,
    ]
)
interface AppComponent {

    @Singleton
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(activity: MainActivity)
    fun inject(fragment: MoviesFragment)
}