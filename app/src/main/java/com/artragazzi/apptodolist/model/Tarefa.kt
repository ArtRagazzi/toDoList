package com.artragazzi.apptodolist.model

import java.io.Serializable

data class Tarefa(
    val idTarefa : Int,
    val titulo: String,
    val data: String
) : Serializable
