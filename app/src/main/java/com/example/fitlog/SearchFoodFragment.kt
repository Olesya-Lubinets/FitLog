package com.example.fitlog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitlog.data.model.FoodUiState
import com.example.fitlog.data.model.DataSource
import com.example.fitlog.ui.FoodViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFoodFragment : Fragment() {

    val foodViewModel: FoodViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.search_food_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchView = view.findViewById<SearchView>(R.id.searchView)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        val message = view.findViewById<TextView>(R.id.foodMessage)



        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = FoodAdapter(
            { food -> foodViewModel.toggleFood(food) },
            { food ->
                val action =
                    SearchFoodFragmentDirections.actionSearchFoodFragmentToFoodDetailsFragment(
                        foodId = food.food_id
                    )
                findNavController().navigate(action)
            })
        recyclerView.adapter = adapter


        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                foodViewModel.foodUiState.collect { state ->
                    Log.d("AddWorkout Fragment", "State updated: $state")
                    when (state) {
                        is FoodUiState.SuccessState -> {
                            when (state.source) {
                                DataSource.API -> message.text = "Found:"
                                DataSource.FAVORITE -> message.text =
                                    "No internet connection. Favorites shown."
                            }
                            adapter.submitList(state.data)
                        }

                        is FoodUiState.Empty -> Toast.makeText(
                            context,
                            "Sorry: nothing found",
                            Toast.LENGTH_SHORT
                        ).show()

                        is FoodUiState.ErrorSate -> Toast.makeText(
                            context,
                            state.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    foodViewModel.getSearchedFood(query)
                }
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }
}