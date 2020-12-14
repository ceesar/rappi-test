package com.rappi.testapp.core.viewmodel.event

import com.rappi.testapp.core.data.models.api.Resource

sealed class BaseViewEvent {
    object OnValidationSuccess : BaseViewEvent()
    data class OnRequestEvent(val resource: Resource<*>) : BaseViewEvent()
}