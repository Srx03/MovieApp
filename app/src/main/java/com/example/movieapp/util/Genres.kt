package com.example.movieapp.util

import com.example.movieapp.models.genres.Genre
import com.example.movieapp.models.genres.GenreX

class Genres {

    object Genres {

//    fun getGenresText(ids: List<Int>?): String? {
//        if (ids == null) {
//            return null
//        }
//        return getGenreListFromIds(ids).joinToString(" • ") { it.name }
//    }

        fun getMovieGenreListFromIds(ids: List<Int>?): List<GenreX> {
            if (ids == null) {
                return emptyList()
            }
            val results = mutableListOf<GenreX>()
            ids.forEach {
                moviesGenresMap.containsKey(it) && results.add(GenreX(it, moviesGenresMap[it]!!))
            }
            return results
        }

        fun getTvGenreListFromIds(ids: List<Int>?): List<GenreX> {
            if (ids == null) {
                return emptyList()
            }
            val results = mutableListOf<GenreX>()
            ids.forEach {
                tvShowsGenresMap.containsKey(it) && results.add(GenreX(it, tvShowsGenresMap[it]!!))
            }
            return results
        }

        fun getAllMovieGenreList() = arrayListOf(
            GenreX(28, "Action"),
            GenreX(12, "Adventure"),
            GenreX(16, "Animation"),
            GenreX(35, "Comedy"),
            GenreX(80, "Crime"),
            GenreX(99, "Documentary"),
            GenreX(18, "Drama"),
            GenreX(10751, "Family"),
            GenreX(14, "Fantasy"),
            GenreX(36, "History"),
            GenreX(27, "Horror"),
            GenreX(10402, "Music"),
            GenreX(9648, "Mystery"),
            GenreX(10749, "Romance"),
            GenreX(878, "Science Fiction"),
            GenreX(10770, "TV Movie"),
            GenreX(53, "Thriller"),
            GenreX(10752, "War"),
            GenreX(37, "Western"),
        )

        fun getAllTvGenreList() = arrayListOf(
            GenreX(10759, "Action & Adventure"),
            GenreX(16, "Animation"),
            GenreX(35, "Comedy"),
            GenreX(80, "Crime"),
            GenreX(99, "Documentary"),
            GenreX(18, "Drama"),
            GenreX(10751, "Family"),
            GenreX(10762, "Kids"),
            GenreX(9648, "Mystery"),
            GenreX(10763, "News"),
            GenreX(10764, "Reality"),
            GenreX(10765, "Sci-Fi & Fantasy"),
            GenreX(10766, "Soap"),
            GenreX(10767, "Talk"),
            GenreX(10768, "War & Politics"),
            GenreX(37, "Western")
        )

        private
        val moviesGenresMap: HashMap<Int, String> = hashMapOf(
            28 to "Action",
            12 to "Adventure",
            16 to "Animation",
            35 to "Comedy",
            80 to "Crime",
            99 to "Documentary",
            18 to "Drama",
            10751 to "Family",
            14 to "Fantasy",
            36 to "History",
            27 to "Horror",
            10402 to "Music",
            9648 to "Mystery",
            10749 to "Romance",
            878 to "Science Fiction",
            10770 to "TV Movie",
            53 to "Thriller",
            10752 to "War",
            37 to "Western",
        )

        private
        val tvShowsGenresMap: HashMap<Int, String> = hashMapOf(
            10759 to "Action & Adventure",
            16 to "Animation",
            35 to "Comedy",
            80 to "Crime",
            99 to "Documentary",
            18 to "Drama",
            10751 to "Family",
            10762 to "Kids",
            9648 to "Mystery",
            10763 to "News",
            10764 to "Reality",
            10765 to "Sci-Fi & Fantasy",
            10766 to "Soap",
            10767 to "Talk",
            10768 to "War & Politics",
            37 to "Western"
        )
    }

}