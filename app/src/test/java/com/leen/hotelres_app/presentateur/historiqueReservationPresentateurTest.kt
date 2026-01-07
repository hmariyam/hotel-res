package com.leen.hotelres_app.presentateur

import com.leen.hotelres_app.accesAuxDonnees.SourceDeDonneesBidon
import com.leen.hotelres_app.presentation.model.ModelHotelRes
import com.leen.hotelres_app.presentation.model.entities.Reservation
import com.leen.hotelres_app.presentation.presentateur.ContratFragmentPresentateurHistoriqueReservations
import com.leen.hotelres_app.presentation.presentateur.HistoriqueReservationsPresentateur
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.resetMain


@RunWith(MockitoJUnitRunner::class)
class historiqueReservationPresentateurTest {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `étant donné un présentateur, lorsque l'historique de réservation est vide, on appelle erreurDeAffichage sur la vue`() = runBlocking {

        val mockVue = Mockito.mock(ContratFragmentPresentateurHistoriqueReservations.IFragmentHistoriqueReservations::class.java)
        val original = SourceDeDonneesBidon.historiqueReservations.toList()

        try {
            SourceDeDonneesBidon.historiqueReservations = mutableListOf()

            val presentateur = HistoriqueReservationsPresentateur(view = mockVue)
            presentateur.chargerReservations()

            Thread.sleep(1100)

            Mockito.verify(mockVue).erreurDeAffichage("Erreur", "Erreur lors du chargement des réservations.");
        } finally {
            SourceDeDonneesBidon.historiqueReservations = original.toMutableList()
        }
    }

    @Test
    fun `test étant donné un présentateur, lorsqu'il y a une historique des réservations alors la vue affiche la liste complète`() = runBlocking {
        val mockModel = Mockito.mock(ModelHotelRes::class.java)
        val mockVue = Mockito.mock(ContratFragmentPresentateurHistoriqueReservations.IFragmentHistoriqueReservations::class.java)

        val presentateur = HistoriqueReservationsPresentateur(view = mockVue, mockModel)

        Mockito.`when`(mockModel.recupererHistoriqueReservation())
            .thenReturn(SourceDeDonneesBidon.historiqueReservations)

        presentateur.chargerReservations()
        Thread.sleep(1100)

        Mockito.verify(mockVue).afficherReservations(SourceDeDonneesBidon.historiqueReservations)
    }
}