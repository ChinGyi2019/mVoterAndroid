package com.popstack.mvoter2015.feature.faq.ballot

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.size.Scale
import com.popstack.mvoter2015.R
import com.popstack.mvoter2015.databinding.ItemBallotBinding
import com.popstack.mvoter2015.helper.diff.diffCallBackWith
import com.popstack.mvoter2015.helper.extensions.inflater

class BallotExampleRecyclerViewAdapter :
  ListAdapter<BallotExampleViewItem, BallotExampleRecyclerViewAdapter.BallotExampleViewHolder>(
    diffCallBackWith(areItemTheSame = { item1, item2 ->
      item1.id == item2.id
    }, areContentsTheSame = { item1, item2 ->
      item1 == item2
    })
  ) {

  class BallotExampleViewHolder(val binding: ItemBallotBinding) :
    RecyclerView.ViewHolder(binding.root)

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BallotExampleViewHolder {
    val binding = ItemBallotBinding.inflate(parent.inflater(), parent, false)
    return BallotExampleViewHolder(binding)
  }

  override fun onBindViewHolder(holder: BallotExampleViewHolder, position: Int) {
    val itemAtIndex = getItem(position)
    holder.binding.apply {
      tvValid.text = itemAtIndex.isValid.toString()
      tvReason.text = itemAtIndex.reason
      ivBallot.load(itemAtIndex.image) {
        placeholder(R.drawable.placeholder_rect)
        scale(Scale.FIT)
      }
    }
  }
}