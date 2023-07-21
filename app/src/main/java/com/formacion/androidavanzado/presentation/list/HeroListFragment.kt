package com.formacion.androidavanzado.presentation.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.formacion.androidavanzado.R
import com.formacion.androidavanzado.databinding.FragmentListBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HeroListFragment : Fragment() {
    val viewModel: HeroListViewModel by viewModels()
    private lateinit var binding: FragmentListBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(layoutInflater)
        val adapter = HeroAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.loguear()
            val state = viewModel.descargarListaHerores()
            when (state) {
                is HeroListViewModel.HeroListState.OnSuccess -> {
                    withContext(Dispatchers.Main) {
                        adapter.ponerListaHeroes(state.list) }
                }
                is HeroListViewModel.HeroListState.OnError ->
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
                    }
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}