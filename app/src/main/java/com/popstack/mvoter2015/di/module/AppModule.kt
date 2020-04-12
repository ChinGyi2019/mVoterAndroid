package com.popstack.mvoter2015.di.module

import android.app.Application
import android.content.Context
import coil.ImageLoader
import coil.ImageLoaderBuilder
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.di.viewmodel.ViewModelFactoryModule
import com.popstack.mvoter2015.domain.DispatcherProvider
import com.popstack.mvoter2015.feature.home.HomeFeatureModule
import com.popstack.mvoter2015.helper.AndroidDispatcherProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(
    includes = [
      HomeFeatureModule::class,
      ViewModelFactoryModule::class
    ]
)
abstract class AppModule {

  @Binds
  abstract fun dispatcherProvider(dispatcherProvider: AndroidDispatcherProvider): DispatcherProvider

  companion object Provider {

    @Provides
    fun context(application: Application): Context {
      return application.applicationContext
    }

    @Provides @Singleton
    fun imageLoader(context: Context): ImageLoader {
      return ImageLoaderBuilder(context)
          .placeholder(R.drawable.placeholder_rect)
          .build()
    }
  }

}