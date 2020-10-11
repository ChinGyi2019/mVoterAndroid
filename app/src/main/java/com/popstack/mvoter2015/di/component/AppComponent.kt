package com.popstack.mvoter2015.di.component

import android.app.Application
import com.popstack.mvoter2015.MVoterApp
import com.popstack.mvoter2015.di.module.AppModule
import com.popstack.mvoter2015.feature.about.AboutFeatureModule
import com.popstack.mvoter2015.feature.analytics.location.SelectedLocationAnalyticsModule
import com.popstack.mvoter2015.feature.candidate.CandidateFeatureModule
import com.popstack.mvoter2015.feature.faq.FaqFeatureModule
import com.popstack.mvoter2015.feature.location.LocationUpdateFeatureModule
import com.popstack.mvoter2015.feature.news.NewsFeatureModule
import com.popstack.mvoter2015.feature.party.PartyFeatureModule
import com.popstack.mvoter2015.feature.settings.SettingsFeatureModule
import com.popstack.mvoter2015.feature.voterlist.VoterListFeatureModule
import com.popstack.mvoter2015.feature.votingguide.VotingGuideFeatureModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
  modules = [
    AppModule::class,
    AndroidInjectionModule::class,
    AndroidSupportInjectionModule::class,
    CandidateFeatureModule::class,
    PartyFeatureModule::class,
    LocationUpdateFeatureModule::class,
    FaqFeatureModule::class,
    VotingGuideFeatureModule::class,
    NewsFeatureModule::class,
    SettingsFeatureModule::class,
    AboutFeatureModule::class,
    VoterListFeatureModule::class,
    SelectedLocationAnalyticsModule::class
  ]
)
interface AppComponent {

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun application(application: Application): Builder

    fun build(): AppComponent
  }

  fun inject(application: MVoterApp)

}