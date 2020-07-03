package com.example.meucats.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meucats.R
import com.example.meucats.RequestApiService
import com.example.meucats.RequestData
import com.example.meucats.adapter.Adapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private var mUrls: MutableList<String> = ArrayList()
    private var mAdapter: Adapter?= null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        root.findViewById<RecyclerView>(R.id.recyclerviewCats).layoutManager =
            GridLayoutManager(context, 3)
        mAdapter=Adapter(mUrls)
        root.findViewById<RecyclerView>(R.id.recyclerviewCats).adapter = mAdapter
        var floatingButton: ImageButton= root.findViewById(R.id.floatingActionButton)

        val apiService = RequestApiService.create()
        for (i in 0..9) {
            apiService.getImageUrl("0817c13b-91f9-44b5-b05a-60d6595e8c85")
                .enqueue(object : Callback<MutableList<RequestData>> {
                    override fun onFailure(call: Call<MutableList<RequestData>>, t: Throwable) {
                        Log.d("Tag", "error with http")
                    }

                    override fun onResponse(
                        call: Call<MutableList<RequestData>>,
                        response: Response<MutableList<RequestData>>
                    ) {
                        val rd: MutableList<RequestData> = response.body()!!
                        mUrls.add(rd[0].url)
                        mAdapter!!.notifyDataSetChanged()
                    }
                })
        }
        floatingButton.setOnClickListener {
            for (i in 0..9) {
                apiService.getImageUrl("0817c13b-91f9-44b5-b05a-60d6595e8c85")
                    .enqueue(object : Callback<MutableList<RequestData>> {
                        override fun onFailure(call: Call<MutableList<RequestData>>, t: Throwable) {
                            Log.d("Tag", "error with http")
                        }

                        override fun onResponse(
                            call: Call<MutableList<RequestData>>,
                            response: Response<MutableList<RequestData>>
                        ) {
                            val rd: MutableList<RequestData> = response.body()!!
                            mUrls.add(rd[0].url)
                            mAdapter!!.notifyDataSetChanged()
                        }
                    })
            }
        }

        return root
    }
}
