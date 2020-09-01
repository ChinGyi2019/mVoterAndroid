package com.popstack.mvoter2015.data.network.api

import com.popstack.mvoter2015.domain.candidate.model.Candidate
import com.popstack.mvoter2015.domain.candidate.model.CandidateGender
import com.popstack.mvoter2015.domain.candidate.model.CandidateId
import com.popstack.mvoter2015.domain.candidate.model.CandidateParent
import com.popstack.mvoter2015.domain.constituency.model.Constituency
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@JsonClass(generateAdapter = true)
data class CandidateListApiResponse(
  @Json(name = "data") val data: List<CandidateApiModel>
)

@JsonClass(generateAdapter = true)
data class CandidateApiModel(
  @Json(name = "id") val id: String,
  @Json(name = "attributes") val attributes: CandidateApiAttributes
)

@JsonClass(generateAdapter = true)
data class CandidateApiAttributes(
  @Json(name = "name") val name: String,
  @Json(name = "image") val image: String,
  @Json(name = "birthday") val birthday: String,
  @Json(name = "age") val age: Int?,
  @Json(name = "ethnicity") val ethnicity: String,
  @Json(name = "religion") val religion: String,
  @Json(name = "education") val education: String,
  @Json(name = "gender") val gender: String,
  @Json(name = "work") val work: String,
  @Json(name = "father") val father: ParentApiModel?,
  @Json(name = "mother") val mother: ParentApiModel?,
  @Json(name = "party") val party: PartyApiModel,
  @Json(name = "constituency") val constituency: ConstituencyApiResponse
) {
  @JsonClass(generateAdapter = true)
  data class ParentApiModel(
    @Json(name = "name") val name: String,
    @Json(name = "ethnicity") val ethnicity: String,
    @Json(name = "religion") val religion: String
  )
}

fun CandidateApiModel.toCandidateModel(): Candidate {
  val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)

  with(attributes) {
    return Candidate(
      id = CandidateId(id),
      name = name,
      gender = CandidateGender.valueOf(gender),
      occupation = work,
      photoUrl = image,
      education = education,
      religion = religion,
      age = age,
      birthDate = LocalDate.parse(birthday, dateTimeFormatter),
      constituency = Constituency(constituency.id.toString(), constituency.attributes.name),
      ethnicity = ethnicity,
      father = father?.run { CandidateParent(name, religion) },
      mother = mother?.run { CandidateParent(name, religion) },
      party = party.mapToParty()
    )
  }
}