package com.popstack.mvoter2015.data.cache.di

import com.popstack.mvoter2015.data.cache.appupdate.AppUpdateCacheSourceImpl
import com.popstack.mvoter2015.data.cache.source.CandidateCacheSourceImpl
import com.popstack.mvoter2015.data.cache.source.FaqCacheSourceImpl
import com.popstack.mvoter2015.data.cache.source.LocationCacheSourceImpl
import com.popstack.mvoter2015.data.cache.source.NewsCacheSourceImpl
import com.popstack.mvoter2015.data.cache.source.PartyCacheSourceImpl
import com.popstack.mvoter2015.data.common.appupdate.AppUpdateCacheSource
import com.popstack.mvoter2015.data.common.candidate.CandidateCacheSource
import com.popstack.mvoter2015.data.common.faq.FaqCacheSource
import com.popstack.mvoter2015.data.common.location.LocationCacheSource
import com.popstack.mvoter2015.data.common.news.NewsCacheSource
import com.popstack.mvoter2015.data.common.party.PartyCacheSource
import dagger.Binds
import dagger.Module

@Module(includes = [SqlDelightModule::class])
abstract class CacheModule {

  @Binds
  abstract fun appUpdateCacheSource(appUpdateCacheSource: AppUpdateCacheSourceImpl): AppUpdateCacheSource

  @Binds
  abstract fun partyCacheSource(partyCacheSource: PartyCacheSourceImpl): PartyCacheSource

  @Binds
  abstract fun faqCacheSource(faqCacheSource: FaqCacheSourceImpl): FaqCacheSource

  @Binds
  abstract fun newsCacheSource(newsCacheSource: NewsCacheSourceImpl): NewsCacheSource

  @Binds
  abstract fun candidateCacheSource(candidateCacheSourceImpl: CandidateCacheSourceImpl): CandidateCacheSource

  @Binds
  abstract fun locationCacheSource(locationCacheSource: LocationCacheSourceImpl): LocationCacheSource
}