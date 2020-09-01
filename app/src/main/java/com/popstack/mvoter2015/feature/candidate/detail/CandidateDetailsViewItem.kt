package com.popstack.mvoter2015.feature.candidate.detail

import com.popstack.mvoter2015.domain.candidate.model.Candidate
import com.popstack.mvoter2015.domain.constituency.model.HouseType
import com.popstack.mvoter2015.feature.candidate.listing.CandidateListViewItem

data class CandidateDetailsViewItem(
  val candidateInfo: CandidateInfoViewItem,
  val rivals: List<CandidateListViewItem.SmallCandidateViewItem>
)

data class CandidateInfoViewItem(
  val photo: String,
  val name: String,
  val partyName: String,
  val partyLogo: String,
  val houseType: String,
  val constituencyName: String,
  val age: String,
  val birthday: String,
  val education: String,
  val job: String,
  val religion: String,
  val motherName: String,
  val motherReligion: String,
  val fatherName: String,
  val fatherReligion: String
)

fun Candidate.toCandidateInfoViewItem() = CandidateInfoViewItem(
  photo = photoUrl,
  name = name,
  partyName = party.nameBurmese,
  partyLogo = party.flagImage,
  // TODO: Distinguish state region
  houseType = when (constituency.house) {
    HouseType.UPPER_HOUSE -> "အမျိုးသားလွှတ်တော်"
    HouseType.LOWER_HOUSE -> "ပြည့်သူလွှတ်တော်"
    HouseType.REGIONAL_HOUSE -> "ပြည်နယ်လွှတ်တော်"
  },
  constituencyName = constituency.name,
  age = age?.toString()?.toMMInt().orEmpty(),
  birthday = birthDate.toString().toMMInt(),
  education = education,
  job = occupation,
  religion = "$ethnicity $religion",
  motherName = mother?.name.orEmpty(),
  motherReligion = mother?.ethnicity.orEmpty() + " " + mother?.religion.orEmpty(),
  fatherName = father?.name.orEmpty(),
  fatherReligion = father?.ethnicity.orEmpty() + " " + father?.religion.orEmpty()
)

private fun String.toMMInt(): String {
  val enInt = arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")
  val mmInt = arrayOf("၀", "၁", "၂", "၃", "၄", "၅", "၆", "၇", "၈", "၉")

  var temp = this
  enInt.forEachIndexed { index, s ->
    temp = temp.replace(s, mmInt[index])
    println(temp)
  }
  return temp
}