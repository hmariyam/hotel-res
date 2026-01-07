package com.leen.hotelres_app.services

import com.leen.hotelres_app.accesAuxDonnees.SourceDeDonneesHTTP
import com.leen.hotelres_app.presentation.model.entities.Chambre
import com.leen.hotelres_app.presentation.model.entities.Reservation
import com.leen.hotelres_app.presentation.utils.NoInternetException
import java.io.IOException
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FormulaireReservationServiceTest {

    private val mockSourceHTTP = Mockito.mock(SourceDeDonneesHTTP::class.java)

    private val sampleReservation = Reservation(
        "Chambre pour 3 personnes",
        0L,
        0L,
        "10 nov. 2025",
        155.0f,
        5
    )

    private val sampleChambre = Chambre(
        id = 1,
        numero = "101",
        nombreLits = 1,
        prix = 480F,
        suite = "vip",
        inclusions = "Wifi, service personalisé",
        description = "Vue sur la mer, accès Handicapé, insonorisé",
        nombrePersonnes = 2
    )

    @Before
    fun setUp(){
        Mockito.reset(mockSourceHTTP)
    }

    @Test
    fun `Étant donné un FormulaireReservationService, lorsqu'on enregistre une reservation on reçois un booléen qui confirme l'enregistrement`(){
        Mockito.`when`(mockSourceHTTP.creerReservation(sampleReservation, sampleChambre))
            .thenReturn(true)
        val cobaye = FormulaireReservationService(mockSourceHTTP)

        val resultat = cobaye.enregistrerReservation(sampleReservation, sampleChambre)

        assert(resultat);
    }

}