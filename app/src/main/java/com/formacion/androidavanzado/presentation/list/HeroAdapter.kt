package com.formacion.androidavanzado.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.formacion.androidavanzado.databinding.ItemHeroBinding

class HeroAdapter: RecyclerView.Adapter<HeroAdapter.HeroViewHolder>() {

    private var list = mutableListOf<HeroListViewModel.Hero>()

    class HeroViewHolder(val binding: ItemHeroBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(hero: HeroListViewModel.Hero, position: Int) {
            with(binding) {
                name.text = hero.nombre
                Glide.with(this.root)
                    .load(hero.imageUrl)
                    .into(image)

                root.setOnClickListener {
                    Toast.makeText(root.context, "Se ha pulsado sobre $hero", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroViewHolder {
        val binding = ItemHeroBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HeroViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

    fun ponerListaHeroes(listHero : List<HeroListViewModel.Hero>) {
        list = listHero.toMutableList()
        notifyDataSetChanged()
    }

    fun borrarTodo() {
        list.clear()
        notifyDataSetChanged()
    }
}