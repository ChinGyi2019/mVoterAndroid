package com.popstack.mvoter2015.domain.constituency.model

data class Constituency(
  val id: String,
  val name: String,
  val house: HouseType,
  val township: String?,
  val stateRegion: String?
)