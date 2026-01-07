package com.leen.hotelres_app.presentateur
import com.leen.hotelres_app.presentation.model.ModelHotelRes
import com.leen.hotelres_app.presentation.model.entities.Chambre
import com.leen.hotelres_app.presentation.model.entities.ChambreFiltre
import com.leen.hotelres_app.presentation.presentateur.ContratFragmentPresentateurChambres
import com.leen.hotelres_app.presentation.presentateur.PresentateurChambres
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import kotlinx.coroutines.newSingleThreadContext
import org.junit.After
import org.junit.Test
import org.mockito.junit.MockitoJUnitRunner
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito
import org.mockito.kotlin.any
import java.lang.System
import java.util.Calendar
import kotlin.Long
import kotlin.time.Duration.Companion.days
import kotlin.time.DurationUnit

@RunWith(MockitoJUnitRunner::class)
class PresentateurChambresTest {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private val sampleArrayOfChambres = arrayListOf<Chambre>(
        Chambre(
            id =1,
            numero = "101",
            nombreLits = 1,
            prix = 480F,
            suite = "vip",
            inclusions = "Wifi, service personalisé",
            description = "Vue sur la mer, accès Handicapé, insonorisé",
            nombrePersonnes = 2
        ),
        Chambre(
            id = 2,
            numero = "102",
            nombreLits = 2,
            prix = 480F,
            suite = "vip",
            inclusions = "Wifi, service à la porte",
            description = "accès Handicapé, Vue sur la mer",
            nombrePersonnes = 2
        ),
    )

    private val sampleChambreFiltre = ChambreFiltre(
        System.currentTimeMillis(),
        System.currentTimeMillis()  + 7.days.toLong(DurationUnit.MILLISECONDS),
        prix = 500F,
        suite = "VIP"
    )

    private val mockVue = Mockito.mock(ContratFragmentPresentateurChambres.IVueChambres::class.java)
    private val mockModel  = Mockito.mock(ModelHotelRes::class.java)

    @Before
    fun setUp() = runTest {
        Mockito.reset(mockVue, mockModel)
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test fun `Étant donné un PresentateurChambre, lorsqu'on essaie d'appliquer des filtres, il récupère les filtres et fait et vas chercher les données des chambres` () {
        Mockito.`when`(mockVue.obtenirFiltres())
            .thenReturn(sampleChambreFiltre)

        Mockito.`when`(mockModel.recupererListeChambres(sampleChambreFiltre) )
            .thenReturn(sampleArrayOfChambres)

        val cobaye = PresentateurChambres(
            vue = mockVue,
            model = mockModel
        )

        cobaye.traiterFiltrer()


        Mockito.verify(mockVue).obtenirFiltres()
        Mockito.verify(mockModel).recupererListeChambres(sampleChambreFiltre)

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Étant donné un PresentateurChambre, lorsqu'on tente de naviguer vers les détails d'une chambre exitante, il recupère la chambre concernée et fait naviguer la vue vers les détails`() = runTest {

        val unconfinedDispatcher = UnconfinedTestDispatcher(testScheduler)

        Mockito.`when`(mockVue.obtenirFiltres())
            .thenReturn(sampleChambreFiltre)

        Mockito.`when`(mockModel.recupererListeChambres(sampleChambreFiltre) )
            .thenReturn(sampleArrayOfChambres as List<Chambre>)
        Mockito.`when`(mockModel.enregristrerFiltre(sampleChambreFiltre))
            .thenReturn(true)
        val chambre = sampleArrayOfChambres[0]

        val cobaye = PresentateurChambres(
            vue = mockVue,
            model = mockModel,
            mainDispatcher = unconfinedDispatcher,
            secondaryDispatcher = unconfinedDispatcher
        )

        cobaye.traiterFiltrer()

        cobaye.traiterNaviguerVersDetails(chambre.numero)

        Mockito.verify(mockModel).selectionnerChambre(chambre)
        Mockito.verify(mockVue).naviguerVersDetails()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Étant donné un PresentateurChambre, lorsqu'on tente de naviguer vers les détails d'une chambre inexistante, il fait afficher une erreur`() = runTest {

        val unconfinedDispatcher = UnconfinedTestDispatcher(testScheduler)
        
        val chambre = sampleArrayOfChambres[0]

        val cobaye = PresentateurChambres(
            vue = mockVue,
            model = mockModel,
            mainDispatcher = unconfinedDispatcher,
            secondaryDispatcher = unconfinedDispatcher
        )

        cobaye.traiterNaviguerVersDetails(chambre.numero)

        Mockito.verify(mockVue).afficherAlerte(Mockito.anyString(), Mockito.anyString())
    }
}