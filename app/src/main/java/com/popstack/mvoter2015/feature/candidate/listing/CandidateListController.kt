package com.popstack.mvoter2015.feature.candidate.listing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.bluelinelabs.conductor.RouterTransaction
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.core.mvp.MvvmController
import com.popstack.mvoter2015.databinding.ControllerCandidateListBinding
import com.popstack.mvoter2015.feature.HasRouter
import com.popstack.mvoter2015.feature.candidate.search.CandidateSearchController
import com.popstack.mvoter2015.feature.home.BottomNavigationHostViewModelStore
import com.popstack.mvoter2015.feature.location.LocationUpdateController
import com.popstack.mvoter2015.helper.conductor.requireActivity
import com.popstack.mvoter2015.helper.conductor.requireContext
import com.popstack.mvoter2015.helper.conductor.setSupportActionBar
import com.popstack.mvoter2015.helper.conductor.supportActionBar
import com.popstack.mvoter2015.logging.HasTag

class CandidateListController :
  MvvmController<ControllerCandidateListBinding>(), HasTag {

  override val tag: String = CONTROLLER_TAG

  companion object {
    const val CONTROLLER_TAG = "CandidateListController"
  }

  private val viewModel: CandidateListViewModel by viewModels(
    store = viewModelStore
  )

  private val pagerAdapter by lazy {
    CandidateListHousePagerAdapter(this)
  }

  override val bindingInflater: (LayoutInflater) -> ControllerCandidateListBinding =
    ControllerCandidateListBinding::inflate

  override fun onBindView(savedViewState: Bundle?) {
    super.onBindView(savedViewState)
    viewModel.viewEventLiveData.observe(this, Observer(::observeViewEvent))

    setSupportActionBar(binding.toolBar)
    supportActionBar()?.title = requireContext().getString(R.string.title_candidates)

    setHasOptionsMenu(R.menu.menu_candidate) { menuItem ->
      when (menuItem.itemId) {
        R.id.action_change_location -> {
          if (requireActivity() is HasRouter) {
            (requireActivity() as HasRouter).router()
              .pushController(RouterTransaction.with(LocationUpdateController()))
          }
          true
        }
        R.id.action_search -> {
          router.pushController(RouterTransaction.with(CandidateSearchController()))
          true
        }
      }
      false
    }

    binding.viewPager.offscreenPageLimit = 3
    binding.viewPager.adapter = pagerAdapter
    binding.tabLayout.setupWithViewPager(binding.viewPager)

    binding.btnChoose.setOnClickListener {
      if (requireActivity() is HasRouter) {
        (requireActivity() as HasRouter).router()
          .pushController(RouterTransaction.with(LocationUpdateController()))
      }
    }

    CandidateListPagerParentRouter.setParentRouter(router)

    viewModel.houseViewItemListLiveData.observe(lifecycleOwner, Observer(::observeHouseViewItem))
    viewModel.loadHouses()
  }

  private fun observeViewEvent(viewEvent: CandidateListViewModel.ViewEvent) {
    if (viewEvent is CandidateListViewModel.ViewEvent.RequestUserLocation) {
      binding.tabLayout.isVisible = false
      binding.groupChooseCandidateComponent.isVisible = true
    }
  }

  private fun observeHouseViewItem(houseViewItemList: List<CandidateListHouseViewItem>) {
    binding.tabLayout.isVisible = true
    binding.groupChooseCandidateComponent.isVisible = false
    pagerAdapter.setItems(houseViewItemList)
  }

  override fun onDestroyView(view: View) {
    binding.viewPager.adapter = null
    binding.tabLayout.setupWithViewPager(null)
    super.onDestroyView(view)
  }

  override fun onDestroy() {
    CandidateListPagerParentRouter.destroy()
    super.onDestroy()
  }

}