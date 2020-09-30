package com.popstack.mvoter2015.feature.candidate.listing.regionalhouse

import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bluelinelabs.conductor.RouterTransaction
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.core.mvp.MvvmController
import com.popstack.mvoter2015.databinding.ControllerRegionalCandidateListBinding
import com.popstack.mvoter2015.domain.candidate.model.CandidateId
import com.popstack.mvoter2015.domain.candidate.usecase.exception.NoStateRegionConstituencyException
import com.popstack.mvoter2015.feature.candidate.detail.CandidateDetailController
import com.popstack.mvoter2015.feature.candidate.listing.CandidateListPagerParentRouter
import com.popstack.mvoter2015.feature.candidate.listing.CandidateListRecyclerViewAdapter
import com.popstack.mvoter2015.feature.candidate.listing.CandidateListResult
import com.popstack.mvoter2015.feature.home.BottomNavigationHostViewModelStore
import com.popstack.mvoter2015.helper.asyncviewstate.AsyncViewState
import com.popstack.mvoter2015.helper.conductor.requireContext
import com.popstack.mvoter2015.helper.extensions.isLandScape
import com.popstack.mvoter2015.helper.extensions.isTablet
import com.popstack.mvoter2015.helper.recyclerview.StickyHeaderDecoration
import com.popstack.mvoter2015.logging.HasTag

class RegionalHouseCandidateListController() : MvvmController<ControllerRegionalCandidateListBinding>(), HasTag {

  override val tag: String = "RegionalHouseCandidateListController"

  private val viewModel: RegionalHouseCandidateListViewModel by viewModels(
    store = BottomNavigationHostViewModelStore.viewModelStore ?: viewModelStore
  )

  override val bindingInflater: (LayoutInflater) -> ControllerRegionalCandidateListBinding =
    ControllerRegionalCandidateListBinding::inflate

  private val candidateListAdapter by lazy {
    CandidateListRecyclerViewAdapter(onCandidateClicked)
  }

  private val onCandidateClicked: (CandidateId) -> Unit = {
    val candidateDetailsController = CandidateDetailController.newInstance(it)
    CandidateListPagerParentRouter.router?.pushController(
      RouterTransaction.with(candidateDetailsController)
    )
  }

  override fun onBindView(savedViewState: Bundle?) {
    super.onBindView(savedViewState)
    binding.rvCandidate.apply {
      adapter = candidateListAdapter
      layoutManager = if (requireContext().isTablet() && requireContext().isLandScape()) {
        GridLayoutManager(requireContext(), 2).also {
          it.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
              return when (candidateListAdapter.getItemViewType(position)) {
                CandidateListRecyclerViewAdapter.VIEW_TYPE_CANDIDATE_SECTION_TITLE -> 2
                CandidateListRecyclerViewAdapter.VIEW_TYPE_CANDIDATE -> 1
                else -> 1
              }
            }
          }
        }
      } else {
        LinearLayoutManager(requireContext())
      }
      addItemDecoration(StickyHeaderDecoration(candidateListAdapter))
    }

    viewModel.viewItemLiveData.observe(this, Observer(::observeViewItem))

    binding.btnRetry.setOnClickListener {
      loadCandidates()
    }

    if (viewModel.viewItemLiveData.value == null) {
      loadCandidates()
    }
  }

  private fun loadCandidates() {
    viewModel.loadCandidates()
  }

  private fun observeViewItem(viewState: AsyncViewState<CandidateListResult>) = with(binding) {
    progressBar.isVisible = viewState is AsyncViewState.Loading
    rvCandidate.isVisible = viewState is AsyncViewState.Success
    tvErrorMessage.isVisible = viewState is AsyncViewState.Error
    btnRetry.isVisible = viewState is AsyncViewState.Error
    groupRemark.isVisible = false

    when (viewState) {
      is AsyncViewState.Success -> {
        val successValue = viewState.value
        rvCandidate.isVisible = successValue is CandidateListResult.CandidateListViewItem
        groupRemark.isVisible = successValue is CandidateListResult.Remark

        if (successValue is CandidateListResult.Remark) {
          tvRemark.text = successValue.remarkMessage
        } else if (successValue is CandidateListResult.CandidateListViewItem) {
          if (successValue.candidateList.isNotEmpty()) {
            candidateListAdapter.submitList(successValue.candidateList)
          } else {
            tvErrorMessage.isVisible = true
            tvErrorMessage.setText(R.string.error_server_404)
          }
        }
      }
      is AsyncViewState.Error -> {
        if (viewState.exception is NoStateRegionConstituencyException) {
          groupRemark.isVisible = true
          btnRetry.isVisible = false
        } else {
          tvErrorMessage.text = viewState.errorMessage
        }
      }
    }
  }

}