package com.artragazzi.apptodolist.database

import com.artragazzi.apptodolist.model.Tarefa

interface ITarefaDAO {

    fun salvar(tarefa: Tarefa):Boolean
    fun atualizar(tarefa: Tarefa):Boolean
    fun remover(idTarefa:Int):Boolean
    fun listar():List<Tarefa>
}