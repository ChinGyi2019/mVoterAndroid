package com.popstack.mvoter2015.feature.candidate.listing

import com.popstack.mvoter2015.core.mvp.Viewable

internal interface CandidateListView : Viewable {

  abstract fun setUpHouse(viewItems: List<CandidateListHouseViewItem>)

}