package com.leen.hotelres_app.services

import com.leen.hotelres_app.accesAuxDonnees.SourceDeDonneesHTTP
import com.leen.hotelres_app.presentation.model.entities.Reservation

class HistoriqueReservationService(private val httpSource: SourceDeDonneesHTTP = SourceDeDonneesHTTP()) {

    fun recupererHistoriqueReservation(): List<Reservation>{
        return httpSource.recupererHistoriqueReservation()
    }
}