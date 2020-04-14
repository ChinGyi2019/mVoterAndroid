package com.popstack.mvoter2015.feature.candidate.listing.upperhouse

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import coil.api.load
import coil.transform.CircleCropTransformation
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.core.recyclerview.ViewBindingViewHolder
import com.popstack.mvoter2015.databinding.ItemCandidateBinding
import com.popstack.mvoter2015.feature.candidate.listing.upperhouse.UpperHouseCandidateListRecyclerViewAdapter.UpperHouseCandidateListViewHolder
import com.popstack.mvoter2015.helper.diff.diffCallBackWith
import com.popstack.mvoter2015.helper.extensions.inflater

class UpperHouseCandidateListRecyclerViewAdapter : ListAdapter<UpperHouseCandidateListViewItem, UpperHouseCandidateListViewHolder>(
  diffCallBackWith(
    areItemTheSame = { item1, item2 ->
      item1.candidateId == item2.candidateId
    },
    areContentsTheSame = { item1, item2 ->
      item1 == item2
    }
  )
) {

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): UpperHouseCandidateListViewHolder {
    val binding = ItemCandidateBinding.inflate(parent.inflater(), parent, false)
    return UpperHouseCandidateListViewHolder(binding)
  }

  override fun onBindViewHolder(
    holder: UpperHouseCandidateListViewHolder,
    position: Int
  ) {
    val itemAtIndex = getItem(position)
    holder.binding.apply {
      ivCandidate.load(itemAtIndex.candidateImage) {
        placeholder(R.drawable.placeholder_oval)
        crossfade(true)
        transformations(CircleCropTransformation())
      }

      ivCandidatePartyFlag.load(itemAtIndex.candidatePartyFlagImage) {
        placeholder(R.drawable.placeholder_oval)
        crossfade(true)
        transformations(CircleCropTransformation())
      }

      tvCandidateName.text = itemAtIndex.name
      tvCandidatePartyName.text = itemAtIndex.candidatePartyName
    }
  }

  class UpperHouseCandidateListViewHolder(binding: ItemCandidateBinding) : ViewBindingViewHolder<ItemCandidateBinding>(
    binding
  )
}