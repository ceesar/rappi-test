package com.rappi.testapp.ui.moviedetail.model

import androidx.hilt.lifecycle.ViewModelInject
import com.rappi.testapp.core.data.models.entity.Movie
import com.rappi.testapp.core.viewmodel.BaseViewModel

class MovieDetailViewModel @ViewModelInject constructor() : BaseViewModel() {
    lateinit var movieDetail: Movie
}