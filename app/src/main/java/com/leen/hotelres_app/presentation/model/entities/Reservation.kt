package com.leen.hotelres_app.presentation.model.entities

data class Reservation(
    val numero: String,
    var dateDébut: Long,
    var dateFin: Long,
    val dateReservation: String,
    var prix: Float,
    var nbPersonnes : Int
)