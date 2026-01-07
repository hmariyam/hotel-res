package com.leen.hotelres_app.presentation.model.entities

class Chambre(
    val id : Int,
    val numero: String ="101",
    val nombreLits: Int = 2,
    val nombrePersonnes: Int,
    val inclusions: String,
    val description: String,
    val images: Array<String> = arrayOf(
        "https://picsum.photos/800/400?image=10",
        "https://picsum.photos/800/400?image=20",
        "https://picsum.photos/800/400?image=30",
        "https://picsum.photos/800/400?image=40",
        "https://picsum.photos/800/400?image=50"
    ),
    val suite: String,
    val prix: Float
){

}