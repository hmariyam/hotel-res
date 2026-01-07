package com.leen.hotelres_app.presentation.presentateur

import com.leen.hotelres_app.presentation.presentateur.ContratFragmentPresentateurFormulaireReservation.IFragmentFormulaireReservations
import com.leen.hotelres_app.presentation.presentateur.ContratFragmentPresentateurFormulaireReservation.IPresentateurFormulaireReservations
import com.leen.hotelres_app.accesAuxDonnees.SourceDeDonneesException
import com.leen.hotelres_app.presentation.model.ModelHotelRes
import com.leen.hotelres_app.presentation.model.entities.AjustementReservation
import com.leen.hotelres_app.presentation.vue.FormulaireReservationsFragment
import kotlinx.coroutines.*

class FormulaireReservationsPresentateur(
    var vue : IFragmentFormulaireReservations = FormulaireReservationsFragment(),
    var modele : ModelHotelRes = ModelHotelRes
) : IPresentateurFormulaireReservations {

    override fun initialiserVue() {
        val reservation = modele.recupererReservation()
        vue.chargerInfoReservation(
            reservation.numero,
            reservation.dateDébut,
            reservation.dateFin,
            reservation.nbPersonnes,
            reservation.prix
        )
    }

    override fun enregistrerReservation(){
        CoroutineScope(Dispatchers.IO ).launch{
            try {
                modele.enregistrerReservationEnCours()
                CoroutineScope(Dispatchers.Main).launch{
                    val message = "L'enregistrement de votre réservation à été exécuté avec succès!"
                    vue.afficherDialogue(message, true)
                }
            }
            catch (exception: SourceDeDonneesException){
                CoroutineScope(Dispatchers.Main).launch{
                    vue.afficherDialogue(exception.message!!, false)
                }
            }

        }
    }

    override fun ajusterReservation() {
        val ajustement: AjustementReservation = vue.recupererAjustementsReservation()
        modele.ajusterReservation(ajustement)
    }
}