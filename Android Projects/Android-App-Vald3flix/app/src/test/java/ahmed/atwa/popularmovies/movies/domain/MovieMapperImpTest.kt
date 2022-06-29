package ahmed.atwa.popularmovies.movies.domain

import ahmed.atwa.popularmovies.movies.data.Movie
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.MockitoAnnotations

class MovieMapperImpTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var movieMapper: MovieMapper
    private val mockMovieEntity = MovieEntity(1, true, "", 3.4, 12, true, 2.3, "test", "", "", "")
    private val mockMovieLocal = MovieLocal(1, 1, "", 3.4, 12, true, 2.3, "test", "", "", "")
    private val mockMovieRemote = Movie(12, 1, true, 2.3, "test", 3.4, "", "", "", "", false, "", "")


    @Before
    @Throws(Exception::class)
    fun setup() {
        MockitoAnnotations.initMocks(this)
        movieMapper = MovieMapperImp()
    }


    @Test
    fun test_mapFromLocal(){
        val expected = mockMovieEntity
        val actual = movieMapper.mapFromLocalToEntity(mockMovieLocal)
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun test_mapFromRemoteToLocal(){
        val expected = mockMovieLocal
        val actual = movieMapper.mapFromRemoteToLocal(mockMovieRemote,1)
        Assert.assertEquals(expected, actual)
    }


}