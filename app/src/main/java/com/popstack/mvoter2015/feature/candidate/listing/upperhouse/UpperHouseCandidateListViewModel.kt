package com.popstack.mvoter2015.feature.candidate.listing.upperhouse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.popstack.mvoter2015.domain.candidate.usecase.GetCandidateList
import com.popstack.mvoter2015.domain.constituency.model.ConstituencyId
import com.popstack.mvoter2015.exception.GlobalExceptionHandler
import com.popstack.mvoter2015.feature.candidate.listing.CandidateListViewItem
import com.popstack.mvoter2015.feature.candidate.listing.toSmallCandidateViewItem
import com.popstack.mvoter2015.helper.asyncviewstate.AsyncViewStateLiveData
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class UpperHouseCandidateListViewModel @Inject constructor(
  private val getCandidate: GetCandidateList,
  private val globalExceptionHandler: GlobalExceptionHandler
) : ViewModel() {

  val viewItemLiveData = AsyncViewStateLiveData<CandidateListViewItem>()

  fun loadCandidates(constituencyId: ConstituencyId) {
    viewModelScope.launch {
      viewItemLiveData.postLoading()
      kotlin.runCatching {
        val candidateList = getCandidate.execute(GetCandidateList.Params(constituencyId))
        val smallCandidateList = candidateList
          .sortedBy {
            it.sortingName
          }
          .map {
            it.toSmallCandidateViewItem()
          }
        val candidateListViewItem = CandidateListViewItem(smallCandidateList)
        viewItemLiveData.postSuccess(candidateListViewItem)
      }.exceptionOrNull()?.let { exception ->
        Timber.e(exception)
        viewItemLiveData.postError(exception, globalExceptionHandler.getMessageForUser(exception))
      }
    }
  }

}