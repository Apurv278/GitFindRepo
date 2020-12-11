package com.example.gitrepofinder

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface GitRepoAPI {
    @GET("search/repositories?")
    fun getRepo(@Query("q") q: String) : Call<SearchResults>
}

class SearchResults(val items: List<Repo>)
class Repo(val full_name: String, val owner: User, val html_url: String)
class User(val avatar_url: String)

class APIRetriever {
    val service : GitRepoAPI
    val url = "https://api.github.com/"

    init {
        val retrofit = Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build()
        service = retrofit.create(GitRepoAPI::class.java)
    }

    fun getRepos(callback: Callback<SearchResults>, q: String) {
        var search = q
        if (search == ""){
            search = "Search"
        }

        val call = service.getRepo(search)
        call.enqueue(callback)
    }

}