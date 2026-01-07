package com.leen.hotelres_app.services

import com.leen.hotelres_app.accesAuxDonnees.SourceDeDonneesFichier
import com.leen.hotelres_app.accesAuxDonnees.SourceDeDonneesHTTP
import com.leen.hotelres_app.presentation.model.entities.Chambre
import com.leen.hotelres_app.presentation.model.entities.ChambreFiltre
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import kotlin.time.Duration.Companion.days
import kotlin.time.DurationUnit

@RunWith(MockitoJUnitRunner::class)
class ChambresServiceTest {

    private val mockSourceHTTP = Mockito.mock(SourceDeDonneesHTTP::class.java)
    private val mockSourceFichier = Mockito.mock(SourceDeDonneesFichier::class.java)

    private val sampleChambreFiltre = ChambreFiltre(
        System.currentTimeMillis(),
        System.currentTimeMillis()  + 7.days.toLong(DurationUnit.MILLISECONDS),
        prix = 500F,
        suite = "VIP"
    )
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

    @Before
    fun setUp(){
        Mockito.reset(mockSourceFichier, mockSourceHTTP)
    }

    // test pour enregistrer le filtre
    @Test
    fun `Étant donné un ChambreService, lorsqu'on enregistre un filtre on reçois un booléen qui confirme l'enregistrement`(){
        Mockito.`when`(mockSourceFichier.enregistrerFiltres(sampleChambreFiltre))
            .thenReturn(true)
        val cobaye = ChambresService(mockSourceHTTP, mockSourceFichier)

        cobaye.enregristrerFiltre(sampleChambreFiltre)

        Mockito.verify(mockSourceFichier).enregistrerFiltres(sampleChambreFiltre)
    }

    @Test
    fun `Étant donné un ChambreService, lorsqu'on recupère le filtre on le reçois`(){
        Mockito.`when`(mockSourceFichier.getFiltres())
            .thenReturn(sampleChambreFiltre)
        val cobaye = ChambresService(mockSourceHTTP, mockSourceFichier)

        val filtreRecuperé = cobaye.recupererFiltre()

        Mockito.verify(mockSourceFichier).getFiltres()
        assert(filtreRecuperé == sampleChambreFiltre)
    }

    @Test
    fun `Étant donné un ChambreService, lorsqu'on tente de recupérer la liste des chambres avec un filtre, on les reçois`(){
        Mockito.`when`(mockSourceHTTP.recupererChambres(sampleChambreFiltre))
            .thenReturn(sampleArrayOfChambres)
        val cobaye = ChambresService(mockSourceHTTP, mockSourceFichier)

        val chambresRecupérés = cobaye.recupérerChambres(sampleChambreFiltre)

        Mockito.verify(mockSourceHTTP).recupererChambres(sampleChambreFiltre)
    }


}