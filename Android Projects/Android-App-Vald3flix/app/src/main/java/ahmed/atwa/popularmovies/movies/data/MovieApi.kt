package ahmed.atwa.popularmovies.movies.data

import ahmed.atwa.popularmovies.utils.commons.AppConstants.Companion.API_KEY_QUERY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

/**
 * Created by Ahmed Atwa on 10/19/18.
 */

@Singleton
interface MovieApi {


    companion object {
        const val POPULAR_MOVIES_QUERY: String = ("discover/movie?sort_by=popularity.desc")
        const val PAGE_QUERY: String = ("page")
    }



    @GET(POPULAR_MOVIES_QUERY)
    suspend fun getMostPopular(@Query(API_KEY_QUERY) apiKey: String,@Query(PAGE_QUERY) page:Int): Response<MovieResponse>




}