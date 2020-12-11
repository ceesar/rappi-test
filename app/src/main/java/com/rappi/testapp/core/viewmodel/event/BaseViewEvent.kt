package com.rappi.testapp.core.viewmodel.event

import com.rappi.testapp.core.data.api.models.Resource

open class BaseViewEvent {
    object OnValidationSuccess : BaseViewEvent()
    data class OnRequestEvent(val resource: Resource<*>) : BaseViewEvent()
}