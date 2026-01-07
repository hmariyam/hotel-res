package com.leen.hotelres_app.presentation.model.entities

import kotlin.time.Duration.Companion.days
import kotlin.time.DurationUnit

class ChambreFiltre(
    public var dateDebut: Long = System.currentTimeMillis(),
    public var dateFin: Long = System.currentTimeMillis()  + 7.days.toLong(DurationUnit.MILLISECONDS),
    public var prix: Float? = null,
    public var suite: String = ""
)