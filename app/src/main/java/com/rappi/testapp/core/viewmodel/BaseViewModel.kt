package com.rappi.testapp.core.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rappi.testapp.core.viewmodel.event.BaseViewEvent
import com.rappi.testapp.core.viewmodel.event.Event

open class BaseViewModel @ViewModelInject constructor() : ViewModel() {

    val viewEvent: LiveData<Event<BaseViewEvent>>
        get() = innerViewEvent

    private val innerViewEvent = MutableLiveData<Event<BaseViewEvent>>()

    fun launchEvent(state: BaseViewEvent) {
        innerViewEvent.postValue(Event(state))
    }
}