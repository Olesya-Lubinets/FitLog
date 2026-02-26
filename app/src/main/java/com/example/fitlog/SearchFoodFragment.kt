package com.example.fitlog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitlog.ui.FoodViewModel

class SearchFoodFragment:Fragment() {

    val foodViewModel: FoodViewModel by viewModels {
        (requireActivity() as MainActivity).viewModelFactory
    }

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

        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = FoodAdapter {
            food ->
            val action = SearchFoodFragmentDirections.actionSearchFoodFragmentToFoodDetailsFragment(foodId = food.food_id)
            findNavController().navigate(action)
        }
        recyclerView.adapter = adapter


        foodViewModel.foundFood.observe(viewLifecycleOwner) { foundFood ->
            Log.d("MainActivity", "LiveData updated: $foundFood")
            val foodList = foundFood.foods.food
            adapter.submitList(foodList)
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