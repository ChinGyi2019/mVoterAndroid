package com.popstack.mvoter2015.feature.party.search

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bluelinelabs.conductor.RouterTransaction
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.core.mvp.MvvmController
import com.popstack.mvoter2015.databinding.ControllerPartySearchBinding
import com.popstack.mvoter2015.domain.party.model.PartyId
import com.popstack.mvoter2015.feature.party.detail.PartyDetailController
import com.popstack.mvoter2015.helper.RecyclerViewMarginDecoration
import com.popstack.mvoter2015.helper.conductor.requireActivityAsAppCompatActivity
import com.popstack.mvoter2015.helper.conductor.requireContext
import com.popstack.mvoter2015.helper.extensions.showKeyboard
import com.popstack.mvoter2015.logging.HasTag
import com.popstack.mvoter2015.paging.CommonLoadStateAdapter
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PartySearchController : MvvmController<ControllerPartySearchBinding>(), HasTag {

  override val tag: String = "PartySearchController"

  private val viewModel: PartySearchViewModel by viewModels()

  override val bindingInflater: (LayoutInflater) -> ControllerPartySearchBinding =
    ControllerPartySearchBinding::inflate

  private val searchPagingAdapter = PartySearchPagingAdapter(this::onItemClick)

  private val placeholderAdapter = PartySearchPlaceholderRecyclerViewAdapter()

  private var searchJob: Job? = null

  @OptIn(ExperimentalPagingApi::class)
  override fun onBindView(savedViewState: Bundle?) {
    super.onBindView(savedViewState)
    requireActivityAsAppCompatActivity().setSupportActionBar(binding.toolBar)
    requireActivityAsAppCompatActivity().supportActionBar?.setDisplayHomeAsUpEnabled(true)

    binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
      override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null && query.isNotEmpty()) {
          search(query)
        }
        return true
      }

      override fun onQueryTextChange(newText: String?): Boolean {
        return true
      }

    })

    binding.rvPlaceholder.apply {
      adapter = placeholderAdapter
      layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
      val dimen =
        context.resources.getDimensionPixelSize(R.dimen.recycler_view_item_margin)
      addItemDecoration(RecyclerViewMarginDecoration(dimen, 1))
    }

    binding.rvParty.apply {
      adapter = searchPagingAdapter.withLoadStateHeaderAndFooter(
        header = CommonLoadStateAdapter(searchPagingAdapter::retry),
        footer = CommonLoadStateAdapter(searchPagingAdapter::retry)
      )
      layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
      val dimen =
        context.resources.getDimensionPixelSize(R.dimen.recycler_view_item_margin)
      addItemDecoration(RecyclerViewMarginDecoration(dimen, 0))
    }

    binding.btnRetry.setOnClickListener {
      searchPagingAdapter.retry()
    }

    searchPagingAdapter.addLoadStateListener { loadStates ->
      val refreshLoadState = loadStates.refresh
      binding.rvParty.isVisible = refreshLoadState is LoadState.NotLoading
      binding.progressBar.isVisible = refreshLoadState is LoadState.Loading
      binding.tvErrorMessage.isVisible = refreshLoadState is LoadState.Error
      binding.btnRetry.isVisible = refreshLoadState is LoadState.Error
      if (viewModel.currentQueryValue != null) {
        binding.tvInstruction.isVisible = false
      }

      if (refreshLoadState is LoadState.Error) {
        binding.tvErrorMessage.text = refreshLoadState.error.message
      }
    }

    lifecycleScope.launch {
      delay(500)
      binding.searchView.showKeyboard()
    }

    lifecycleScope.launch {
      searchPagingAdapter.dataRefreshFlow.collect { isEmpty ->
        binding.tvEmpty.isVisible = isEmpty
        binding.tvEmpty.text = requireContext().getString(R.string.empty_list_search_party, viewModel.currentQueryValue
          ?: "")
      }
    }
  }

  private fun search(query: String) {
    // Make sure we cancel the previous job before creating a new one
    searchJob?.cancel()
    searchJob = lifecycleScope.launch {
      viewModel.search(query).collectLatest { pagingData ->
        searchPagingAdapter.submitData(pagingData)
      }
    }
  }

  private fun onItemClick(partyId: PartyId) {
    val partyDetailController = PartyDetailController.newInstance(partyId)
    router.pushController(RouterTransaction.with(partyDetailController))
  }

}