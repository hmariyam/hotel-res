package com.leen.hotelres_app.presentateur

import com.leen.hotelres_app.presentation.model.ModelHotelRes
import com.leen.hotelres_app.presentation.model.entities.Reservation
import com.leen.hotelres_app.presentation.presentateur.ContratFragmentPresentateurFormulaireReservation
import com.leen.hotelres_app.presentation.presentateur.FormulaireReservationsPresentateur
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import java.text.SimpleDateFormat
import java.util.Locale

@RunWith(MockitoJUnitRunner::class)
class FormulaireReservationsPresentateurTest {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() = runTest {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `étant donné un présentateur, lorsqu'on arrive à la page du formulaire de réservation, alors on doit retourner les dates et le nombre de personnes à la vue`() = runTest{

        val mockVue = Mockito.mock(ContratFragmentPresentateurFormulaireReservation.IFragmentFormulaireReservations::class.java)
        val mockModele = Mockito.mock(ModelHotelRes::class.java)
        val presentateur = FormulaireReservationsPresentateur(mockVue, mockModele)

        val sdf = SimpleDateFormat("dd MMM yyyy", Locale.FRENCH)
        val mockReservation = Reservation(
            "Chambre pour 2 personnes",
            sdf.parse("10 nov. 2025")?.time ?: 0L,
            sdf.parse("27 nov. 2025")?.time ?: 0L,
            "10 oct. 2025",
            130.0f,
            3
        )

        Mockito.`when`(mockModele.recupererReservation()).thenReturn(mockReservation)
        presentateur.initialiserVue()
        Thread.sleep(1100)

        Mockito.verify(mockVue).chargerInfoReservation(
            title = mockReservation.numero,
            dateFin = mockReservation.dateFin,
            nbPersonnes = mockReservation.nbPersonnes,
            prix = mockReservation.prix,
            dateDébut = mockReservation.dateDébut,
        )
    }
}