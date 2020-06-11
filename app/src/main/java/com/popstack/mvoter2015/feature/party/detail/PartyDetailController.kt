package com.popstack.mvoter2015.feature.party.detail

import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.os.bundleOf
import com.popstack.mvoter2015.core.mvp.MvvmController
import com.popstack.mvoter2015.databinding.ControllerPartyDetailBinding
import com.popstack.mvoter2015.domain.party.model.PartyId

class PartyDetailController(
  private val bundle: Bundle
) : MvvmController<ControllerPartyDetailBinding>(bundle) {

  /***
   * Since we dont have factory yet
   * https://github.com/bluelinelabs/Conductor/pull/594
   */
  companion object {
    private const val ARG_PARTY_ID = "party_id"

    fun newInstance(partyId: PartyId): PartyDetailController {
      return PartyDetailController(
        bundleOf(
          ARG_PARTY_ID to partyId.value
        )
      )
    }
  }

  private val viewModel: PartyDetailViewModel by viewModels()

  override val bindingInflater: (LayoutInflater) -> ControllerPartyDetailBinding =
    ControllerPartyDetailBinding::inflate

  override fun onBindView() {
    super.onBindView()
  }
}