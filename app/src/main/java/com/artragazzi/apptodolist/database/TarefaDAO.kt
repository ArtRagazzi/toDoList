package com.artragazzi.apptodolist.database

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.artragazzi.apptodolist.model.Tarefa
import java.sql.SQLException

class TarefaDAO(context : Context):ITarefaDAO {

    private val escrita = DatabaseHelper(context).writableDatabase
    private val leitura = DatabaseHelper(context).readableDatabase




    override fun salvar(tarefa: Tarefa): Boolean {
        val valores = ContentValues()
        valores.put("${DatabaseHelper.TITULO}", tarefa.titulo)
        valores.put("${DatabaseHelper.DATA_TAREFA}", tarefa.data)


        try {
            escrita.insert(
                DatabaseHelper.NOME_TABELA_TAREFAS,
                null,
                valores
            )
            Log.i("info_db", "Dado inserido")
        }catch (e: SQLException){
            Log.i("info_db", "${e.printStackTrace()}")
            return false
        }
        return true
    }

    override fun atualizar(tarefa: Tarefa): Boolean {
        val args = arrayOf(tarefa.idTarefa.toString())
        val conteudo = ContentValues()
        conteudo.put("${DatabaseHelper.TITULO}",tarefa.titulo)

        try {
            escrita.update(
                DatabaseHelper.NOME_TABELA_TAREFAS,
                conteudo,
                "${DatabaseHelper.ID_TAREFAS} = ?",
                args
            )
            Log.i("info_db", "Dado Atualizado")
        }catch (e: SQLException){
            Log.i("info_db", "${e.printStackTrace()}")
            return false
        }
        return true
    }

    override fun remover(idTarefa: Int): Boolean {
        val args = arrayOf(idTarefa.toString())
        try {
            escrita.delete(
                DatabaseHelper.NOME_TABELA_TAREFAS,
                "${DatabaseHelper.ID_TAREFAS} = ?",
                args
            )
            Log.i("info_db", "Dado Removido")
        }catch (e: SQLException){
            Log.i("info_db", "${e.printStackTrace()}")
            return false
        }
        return true

    }

    override fun listar(): List<Tarefa> {
        val taskList = mutableListOf<Tarefa>()
        val sql = "SELECT ${DatabaseHelper.ID_TAREFAS}, ${DatabaseHelper.TITULO}, ${DatabaseHelper.DATA_TAREFA} FROM ${DatabaseHelper.NOME_TABELA_TAREFAS}"

        val cursor = leitura.rawQuery(sql,null)

        val indiceId = cursor.getColumnIndex(DatabaseHelper.ID_TAREFAS)
        val indiceTitulo = cursor.getColumnIndex(DatabaseHelper.TITULO)
        val indiceData = cursor.getColumnIndex(DatabaseHelper.DATA_TAREFA)

            while (cursor.moveToNext()) {
                val idTarefa = cursor.getInt(indiceId)
                val titulo = cursor.getString(indiceTitulo)
                val data = cursor.getString(indiceData)
                taskList.add(Tarefa(idTarefa, titulo, data))
            }
        return taskList
    }
}