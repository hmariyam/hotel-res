package com.leen.hotelres_app.services

import com.leen.hotelres_app.accesAuxDonnees.SourceDeDonneesHTTP
import com.leen.hotelres_app.presentation.model.entities.Chambre
import com.leen.hotelres_app.presentation.model.entities.Reservation

class FormulaireReservationService(private val httpSource: SourceDeDonneesHTTP = SourceDeDonneesHTTP()) {

    fun enregistrerReservation(reservation: Reservation, chambre : Chambre) : Boolean{
        return httpSource.creerReservation(reservation, chambre)
    }

}