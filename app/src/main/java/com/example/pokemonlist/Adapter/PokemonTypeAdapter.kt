package com.example.pokemonlist.Adapter

import com.example.pokemonlist.Common.Common
import com.example.pokemonlist.Interface.IItemClickListener
import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonlist.R
import com.google.android.material.chip.Chip

class PokemonTypeAdapter (internal var context: Context, internal var typeList:List<String>):
RecyclerView.Adapter<PokemonTypeAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.chip_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.chip.text = typeList[position]
        holder.chip.chipBackgroundColor = ColorStateList.valueOf(Common.getColorByType(typeList[position]))

    }

    override fun getItemCount(): Int {
        return typeList.size
    }


    inner class MyViewHolder (itemView:View):RecyclerView.ViewHolder(itemView){
        internal lateinit var chip: Chip
        internal var iItemClickListener: IItemClickListener?=null

        init{
            chip = itemView.findViewById(R.id.chip_item) as Chip
            chip.setOnCloseIconClickListener { Toast.makeText(context,"Clicked",Toast.LENGTH_SHORT).show() }

        }

    }

}