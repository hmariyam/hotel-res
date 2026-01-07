package com.leen.hotelres_app.accesAuxDonnees

import com.leen.hotelres_app.presentation.model.entities.Chambre
import com.leen.hotelres_app.presentation.model.entities.ChambreFiltre
import com.leen.hotelres_app.presentation.model.entities.Reservation
import java.io.IOException

interface ISourceDeDonnees {
    fun getHistoriqueReservations(): List<Reservation>
    //fun getReservation(): Reservation
    fun getListeChambres(): ArrayList<Chambre>

    fun getChambreParId(id: Int): Chambre

    fun getListeChambresFiltre(filtre: ChambreFiltre): ArrayList<Chambre>

    fun enregistrerReservation(reservation: Reservation)
}