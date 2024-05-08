package com.artragazzi.apptodolist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.artragazzi.apptodolist.adapter.TarefaAdapter
import com.artragazzi.apptodolist.database.TarefaDAO
import com.artragazzi.apptodolist.databinding.ActivityMainBinding
import com.artragazzi.apptodolist.model.Tarefa

class MainActivity : AppCompatActivity() {

    private val binding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }
    private var taskList = emptyList<Tarefa>()
    private var tarefaAdapter : TarefaAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        binding.fabAdicionar.setOnClickListener{
            val intent = Intent(this, AddTarefaActivity::class.java)
            startActivity(intent)
        }
        //RecyclerView

        tarefaAdapter = TarefaAdapter(
            {
                id-> confirmarExclusao(id)
            },{
                tarefa -> editar(tarefa)
            }
        )

        binding.rvTarefas.adapter = tarefaAdapter
        binding.rvTarefas.layoutManager = LinearLayoutManager(this)




    }

    private fun editar(tarefa: Tarefa) {
        val intent = Intent(this, AddTarefaActivity::class.java)
        intent.putExtra("tarefa", tarefa)
        startActivity(intent)
    }

    private fun confirmarExclusao(id: Int) {
        val alertBuilder = AlertDialog.Builder(this)
        alertBuilder.setTitle("Confirmar Exclusão")
        alertBuilder.setMessage("Deseja realmente excluir essa tarefa?")
        alertBuilder.setPositiveButton("Sim"){_,_ ->

            val tarefaDAO = TarefaDAO(this)
            if(tarefaDAO.remover(id)) {
                atualizarListaTarefas()
                Toast.makeText(this, "Tarefa removida com sucesso", Toast.LENGTH_SHORT).show()
            }
        }
        alertBuilder.setNegativeButton("Não"){_,_ ->

        }
        alertBuilder.create().show()
    }

    override fun onStart() {
        super.onStart()
        atualizarListaTarefas()
    }

    private fun atualizarListaTarefas(){
        val tarefaDAO = TarefaDAO(this)
        taskList = tarefaDAO.listar()
        tarefaAdapter?.adicionarLista(taskList)

    }
}