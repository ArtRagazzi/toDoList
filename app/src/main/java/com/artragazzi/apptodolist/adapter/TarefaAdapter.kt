package com.artragazzi.apptodolist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.artragazzi.apptodolist.databinding.ItemTarefaBinding
import com.artragazzi.apptodolist.model.Tarefa

class TarefaAdapter(val onClickExcluir:(Int) -> Unit, val onClickAtualizar:(Tarefa) -> Unit) : RecyclerView.Adapter<TarefaAdapter.TarefaViewHolder>() {

    private var listaTarefas: List<Tarefa> = emptyList()
    fun adicionarLista(lista: List<Tarefa>){
        this.listaTarefas = lista
        notifyDataSetChanged()
    }



    inner class TarefaViewHolder(itemBinding: ItemTarefaBinding):RecyclerView.ViewHolder(itemBinding.root){
        private val binding: ItemTarefaBinding
        init {
            binding = itemBinding
        }
        fun bind(tarefa: Tarefa){

            binding.txtTarefaNome.text = tarefa.titulo
            binding.txtTarefaData.text = tarefa.data
            binding.btnDelete.setOnClickListener { onClickExcluir(tarefa.idTarefa) }
            binding.btnEdit.setOnClickListener { onClickAtualizar(tarefa) }

        }
    }



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TarefaAdapter.TarefaViewHolder {
        val itemTarefaBinding = ItemTarefaBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TarefaViewHolder(itemTarefaBinding)
    }

    override fun onBindViewHolder(holder: TarefaAdapter.TarefaViewHolder, position: Int) {
        val tarefa = listaTarefas[position]
        holder.bind(tarefa)
    }

    override fun getItemCount(): Int {
        return listaTarefas.size
    }


}