package com.popstack.mvoter2015.feature.party.listing

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import coil.load
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.core.recyclerview.ViewBindingViewHolder
import com.popstack.mvoter2015.databinding.ItemPartyBinding
import com.popstack.mvoter2015.feature.party.listing.PartyListViewItemRecyclerViewAdapter.PartyListViewItemViewHolder
import com.popstack.mvoter2015.helper.diff.diffCallBackWith
import com.popstack.mvoter2015.helper.extensions.inflater
import com.popstack.mvoter2015.helper.extensions.withSafeAdapterPosition

internal class PartyListViewItemRecyclerViewAdapter constructor(
  private val itemClick: (PartyListViewItem) -> Unit
) : PagingDataAdapter<PartyListViewItem, PartyListViewItemViewHolder>(
  diffCallBackWith(
    areItemTheSame = { item1, item2 ->
      item1.partyId == item2.partyId
    },
    areContentsTheSame = { item1, item2 ->
      item1 == item2
    }
  )
) {

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): PartyListViewItemViewHolder {
    val binding = ItemPartyBinding.inflate(parent.inflater(), parent, false)
    val viewHolder = PartyListViewItemViewHolder(binding)
    viewHolder.apply {
      itemView.setOnClickListener {
        this.binding.tvPartyName.transitionName = this.itemView.context.getString(R.string.transition_name_party_name)
        this.binding.ivPartySeal.transitionName = this.itemView.context.getString(R.string.transition_name_party_seal)
        withSafeAdapterPosition { position ->
          getItem(position)?.let { itemAtIndex ->
            itemClick.invoke(itemAtIndex)
          }
        }
      }
    }
    return viewHolder
  }

  override fun onBindViewHolder(
    holder: PartyListViewItemViewHolder,
    position: Int
  ) {
    getItem(position)?.let { itemAtIndex ->
      holder.binding.apply {
        ivPartySeal.load(itemAtIndex.sealImage) {
          placeholder(R.drawable.placeholder_rect)
          error(R.drawable.placeholder_rect)
          crossfade(true)
        }

        tvPartyName.text = itemAtIndex.name
        tvPartyRegion.text = itemAtIndex.region
      }
    }
  }

  class PartyListViewItemViewHolder(
    binding: ItemPartyBinding
  ) : ViewBindingViewHolder<ItemPartyBinding>(binding)
}