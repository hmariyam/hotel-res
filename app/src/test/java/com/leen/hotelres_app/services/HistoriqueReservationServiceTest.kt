package com.leen.hotelres_app.services

import com.leen.hotelres_app.accesAuxDonnees.SourceDeDonneesHTTP
import com.leen.hotelres_app.presentation.model.entities.Chambre
import com.leen.hotelres_app.presentation.model.entities.Reservation
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HistoriqueReservationServiceTest {

    private val mockSourceHTTP = Mockito.mock(SourceDeDonneesHTTP::class.java)

    private val sampleListeReservation : List<Reservation> = listOf<Reservation>(
        Reservation(
            "Chambre pour 3 personnes",
            0L,
            0L,
            "10 nov. 2025",
            155.0f,
            5
        ),
        Reservation(
            "Chambre pour 2 personnes",
            0L,
            0L,
            "11 nov. 2024",
            200.0f,
            2
        ),
        Reservation(
            "Chambre pour 4 personnes",
            0L,
            0L,
            "25 nov. 2025",
            145.0f,
            4
        )
    )

    @Before
    fun setUp(){
        Mockito.reset(mockSourceHTTP)
    }

    @Test
    fun `Étant donné un HistoriqueReservationService, lorsqu'on recupere l'historique des reservations on reçois une liste de reservations`(){
        Mockito.`when`(mockSourceHTTP.recupererHistoriqueReservation())
            .thenReturn(sampleListeReservation)
        val cobaye = HistoriqueReservationService(mockSourceHTTP)

        val resultat = cobaye.recupererHistoriqueReservation()

        assertTrue(resultat.size == sampleListeReservation.size);
        assertTrue(resultat.first() == sampleListeReservation.first())
        assertTrue(resultat.last() == sampleListeReservation.last())
    }

}