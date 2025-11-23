package com.example.fitlog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitlog.ui.FoodViewModel

class SearchFoodFragment:Fragment() {
    private lateinit var viewModel: FoodViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.search_food_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel = ViewModelProvider(
//            requireActivity(),
//            (requireActivity() as MainActivity).App
//        )[FoodViewModel::class.java]
//
//        val searchView = view.findViewById<SearchView>(R.id.searchView)
//        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
//
//        recyclerView.layoutManager = LinearLayoutManager(context)
//        val adapter = FoodAdapter()
//
//        viewModel.foundFood.observe(viewLifecycleOwner) { response ->
//            val tempList = response.common+response.branded
//            recyclerView.adapter = FoodAdapter(tempList)
//        }

    }
}