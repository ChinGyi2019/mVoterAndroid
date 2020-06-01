package com.popstack.mvoter2015.feature.info

import android.view.LayoutInflater
import android.view.ViewGroup
import com.popstack.mvoter2015.core.mvp.MvpController
import com.popstack.mvoter2015.databinding.ControllerInfoBinding

class InfoController : MvpController<ControllerInfoBinding, InfoView, InfoViewModel>(), InfoView {

  override val viewModel: InfoViewModel by contractedViewModels()

  override val bindingInflater: (LayoutInflater, ViewGroup) -> ControllerInfoBinding
    get() = { layoutInflater, viewGroup ->
      ControllerInfoBinding.inflate(layoutInflater, viewGroup, false)
    }

}