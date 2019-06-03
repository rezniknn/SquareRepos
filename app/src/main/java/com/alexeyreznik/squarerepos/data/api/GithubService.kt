package com.alexeyreznik.squarerepos.data.api

import com.alexeyreznik.squarerepos.data.model.Repo
import io.reactivex.Single
import retrofit2.http.GET

interface GithubService {

    @GET("orgs/square/repos")
    fun getSquareRepos(): Single<List<Repo>>

}