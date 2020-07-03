package com.example.meucats.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meucats.R
import com.example.meucats.adapter.AdapterFavorite
import com.example.meucats.database.CatTable
import com.example.meucats.mDb

class DashboardFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        root.findViewById<RecyclerView>(R.id.recyclerFav).layoutManager =
            GridLayoutManager(context, 3)
        mDb.CatDao().getCats().observe(
            this,
            Observer<List<CatTable>> {
                this.renderMessages(
                    it
                )
            })
        return root
    }
    private fun renderMessages(messages: List<CatTable>?) {

        if (messages != null) {
            activity?.findViewById<RecyclerView>(R.id.recyclerFav)?.adapter =
                AdapterFavorite(messages.toMutableList())
        }
    }
}
