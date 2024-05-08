package com.artragazzi.apptodolist.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.sql.SQLException

class DatabaseHelper(context: Context): SQLiteOpenHelper(
    context,
    NOME_BANCO_DADOS,
    null,
    VERSAO

){
    companion object{
        const val NOME_BANCO_DADOS = "ListaTarefas.db"
        const val VERSAO = 1
        const val NOME_TABELA_TAREFAS = "tarefas"
        const val ID_TAREFAS = "id_tarefas"
        const val TITULO = "titulo"
        const val DATA_TAREFA = "data_cadastro"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val sql = "CREATE TABLE IF NOT EXISTS ${NOME_TABELA_TAREFAS}(" +
                "${ID_TAREFAS} INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "${TITULO} VARCHAR(70)," +
                "${DATA_TAREFA} DATETIME NoT NULL DEFAULT CURRENT_TIMESTAMP" +
                ");"

        try {
            db?.execSQL(sql)
        }catch (e:SQLException){
            Log.i("info_db", "${e.printStackTrace()}")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

}