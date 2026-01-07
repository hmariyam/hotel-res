package com.leen.hotelres_app.presentation.presentateur

import com.leen.hotelres_app.presentation.model.entities.AjustementReservation


interface ContratFragmentPresentateurFormulaireReservation {

    interface IFragmentFormulaireReservations{
        fun chargerInfoReservation(
            title: String,
            dateDébut: Long,
            dateFin: Long,
            nbPersonnes : Int,
            prix: Float
        )

        fun recupererAjustementsReservation(): AjustementReservation

        fun afficherDialogue(message: String, isSaved: Boolean)
    }

    interface IPresentateurFormulaireReservations{
        fun initialiserVue()
        fun enregistrerReservation()
        fun ajusterReservation()
    }
}