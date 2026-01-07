package com.leen.hotelres_app.presentateur

import com.leen.hotelres_app.presentation.model.ModelHotelRes
import com.leen.hotelres_app.presentation.model.entities.Chambre
import com.leen.hotelres_app.presentation.presentateur.ContratFragmentPresentateurDetailsChambre
import com.leen.hotelres_app.presentation.presentateur.PresentateurDetailsChambre
import org.junit.Test
import org.mockito.Mockito
class DetailsChambrePresentateurTest {

    @Test
    fun `étant donné un PresentateurDetailsChambre, lorsqu'on charge les informations de la chambre, alors on doit retourner les informations de la chambre à la vue`(){

        val mockVue = Mockito.mock(ContratFragmentPresentateurDetailsChambre.IFragmentDetailsChambre::class.java)
        val mockModel = Mockito.mock(ModelHotelRes::class.java)

        val chambre = Chambre(
            id = 1,
            numero = "B260",
            nombreLits = 2,
            prix = 125f,
            inclusions = "Micro-onde, cafetière, mini-frigo, douche et bain séparé ainsi qu'une vue sur le paysage montagneux.",
            description = "Une chambre parfaite pour un couple avec des enfants ou des amis en vacances!",
            nombrePersonnes = 2,
            suite = "VIP"
        )

        Mockito.`when`(mockModel.recupererChambreSelectionnee()).thenReturn(chambre)

        val presentateur = PresentateurDetailsChambre(
            vue = mockVue,
            modele = mockModel)
        presentateur.initialiserVue()

        Mockito.verify(mockVue).chargerInfosChambre(
            chambre.numero,
            chambre.nombreLits,
            chambre.description,
            chambre.inclusions,
            chambre.nombrePersonnes,
            String.format("%.2f", chambre.prix),
            chambre.images
        )
    }
}