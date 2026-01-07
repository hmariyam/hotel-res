package com.leen.hotelres_app.presentation.model

import com.leen.hotelres_app.presentation.model.entities.AjustementReservation
import com.leen.hotelres_app.presentation.model.entities.Chambre
import com.leen.hotelres_app.presentation.model.entities.ChambreFiltre
import com.leen.hotelres_app.presentation.model.entities.Reservation
import com.leen.hotelres_app.services.ChambresService
import com.leen.hotelres_app.services.FormulaireReservationService
import com.leen.hotelres_app.services.HistoriqueReservationService
import org.koin.java.KoinJavaComponent.inject
import java.time.LocalDate

object ModelHotelRes {
    private val historiqueService = HistoriqueReservationService()
    private val formulaireReservationService = FormulaireReservationService()
    private val chambreService : ChambresService by inject(ChambresService::class.java)

    private lateinit var chambreSelectionnee: Chambre
    private lateinit var reservationEnCours: Reservation
    private var dateDebut: Long = 0L
    private var dateFin: Long = 0L


    fun recupererReservation(): Reservation {
        return reservationEnCours
    }

    fun recupererHistoriqueReservation(): List<Reservation>{
        return historiqueService.recupererHistoriqueReservation()
    }

    fun recupererListeChambres(filtre: ChambreFiltre): List<Chambre>{
        return chambreService.recupérerChambres(filtre)
    }

    fun selectionnerChambre(chambre: Chambre){
        this.chambreSelectionnee = chambre
    }

    fun preparerReservation(){

        val reservation = Reservation(
            numero = chambreSelectionnee.numero,
            dateDébut = this.dateDebut,
            dateFin = this.dateFin,
            dateReservation = LocalDate.now().toString(),
            prix = chambreSelectionnee.prix,
            nbPersonnes = chambreSelectionnee.nombrePersonnes
        )
        this.reservationEnCours = reservation
    }
    fun recupererChambreSelectionnee(): Chambre{
        return chambreSelectionnee
    }

    fun enregistrerReservationEnCours(){
        formulaireReservationService.enregistrerReservation(this.reservationEnCours, this.chambreSelectionnee)
    }

    fun enregristrerFiltre(filtre: ChambreFiltre): Boolean{
        this.dateDebut = filtre.dateDebut
        this.dateFin = filtre.dateFin
        return chambreService.enregristrerFiltre(filtre)
    }

    fun recupererFiltre(): ChambreFiltre{
        return chambreService.recupererFiltre()
    }

    fun ajusterReservation(ajustement: AjustementReservation){
        this.reservationEnCours.prix = ajustement.prix
        this.reservationEnCours.dateDébut = ajustement.dateDebut
        this.reservationEnCours.dateFin = ajustement.dateFin
        this.reservationEnCours.nbPersonnes = ajustement.nbrePersonnes
    }

}