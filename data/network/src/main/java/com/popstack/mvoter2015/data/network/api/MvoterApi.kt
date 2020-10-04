package com.popstack.mvoter2015.data.network.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MvoterApi {

  @GET("parties")
  fun partyList(
    @Query("page") page: Int,
    @Query("item_per_page") itemsPerPage: Int,
    @Query("query") query: String?
  ): Call<GetPartyListResponse>

  @GET("parties/{party_id}")
  fun party(
    @Path("party_id") partyId: String
  ): Call<GetPartyDetailResponse>

  @GET("faqs")
  fun faqList(
    @Query("page") page: Int,
    @Query("item_per_page") itemsPerPage: Int,
    @Query("category") category: String?,
    @Query("query") query: String?
  ): Call<GetFaqListResponse>

  @GET("ballots")
  fun ballotExampleList(
    @Query("category") category: String
  ): Call<GetBallotExampleListResponse>

  @GET("news")
  fun newsList(
    @Query("page") page: Int,
    @Query("item_per_page") itemPerPage: Int,
    @Query("query") query: String?
  ): Call<GetNewsListResponse>

  @GET("locality/state_regions")
  fun getStateRegionList(): Call<GetStateRegionListResponse>

  @GET("locality/townships")
  fun getTownshipsForStateRegion(
    @Query("candidate_upper_house") stateRegion: String
  ): Call<GetTownshipListResponse>

  @GET("locality/wards")
  fun getWardsForTownship(
    @Query("candidate_upper_house") stateRegion: String,
    @Query("township") township: String
  ): Call<GetWardListResponse>

  @GET("locality/details")
  fun getWardDetails(
    @Query("candidate_upper_house") stateRegion: String,
    @Query("township") township: String,
    @Query("ward") ward: String
  ): Call<WardApiModel>

  @GET("candidates")
  fun candidateList(
    @Query("constituency_id") constituencyId: String
  ): Call<CandidateListApiResponse>

  @GET("candidates/{candidate_id}")
  fun candidate(
    @Path("candidate_id") candidateId: String
  ): Call<CandidateDetailsApiResponse>

  @GET("candidates")
  fun searchCandidates(
    @Query("query") query: String,
    @Query("page") page: Int
  ): Call<CandidateListApiResponse>

}