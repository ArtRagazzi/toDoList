package com.artragazzi.apptodolist

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.artragazzi.apptodolist.database.TarefaDAO
import com.artragazzi.apptodolist.databinding.ActivityAddTarefaBinding
import com.artragazzi.apptodolist.databinding.ActivityMainBinding
import com.artragazzi.apptodolist.model.Tarefa
import java.sql.Date
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

class AddTarefaActivity : AppCompatActivity() {


    private val binding by lazy{
        ActivityAddTarefaBinding.inflate(layoutInflater)
    }

    companion object{
        fun currentDate():String{
            return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        }
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }




        //Recuperar tarefa passada
        var tarefa : Tarefa? = null
        val bundle = intent.extras
        if(bundle != null){
            tarefa = bundle.getSerializable("tarefa") as Tarefa
            binding.editNomeTarefa.setText(tarefa.titulo)
        }

        binding.btnAdicionar.setOnClickListener {
            if(binding.editNomeTarefa.text.isNotEmpty()){
                if(tarefa != null){
                    editar(tarefa)
                }else{
                    salvar()
                }

            }else{
                Toast.makeText(this,"Preencha o nome da tarefa", Toast.LENGTH_LONG).show()
            }

        }
    }

    private fun editar(tarefa:Tarefa) {
        val titulo = binding.editNomeTarefa.text.toString()
        val tarefaAtt = Tarefa(
            tarefa.idTarefa,titulo, tarefa.data
        )
        val tarefaDAO = TarefaDAO(this)
        if(tarefaDAO.atualizar(tarefaAtt)){
            Toast.makeText(this,"Tarefa atualizada com sucesso!", Toast.LENGTH_LONG).show()
            finish()
        }

    }

    private fun salvar() {
        val tarefa = Tarefa(
            -1,binding.editNomeTarefa.text.toString(), currentDate()
        )
        val tarefaDAO = TarefaDAO(this)
        if(tarefaDAO.salvar(tarefa)){
            Toast.makeText(this,"Tarefa inserida com sucesso!", Toast.LENGTH_LONG).show()
            finish()
        }
    }
}