package com.example.gitrepofinder

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val search = intent.getStringExtra("searchItem")
        //println(search)
        val call = object : Callback<SearchResults> {
            override fun onResponse(call: Call<SearchResults>, response: Response<SearchResults>) {
                val sRes = response.body()
                if(sRes != null) {
                    for (repo in sRes.items){
                        println(repo.full_name)
                    }
                    val rec = findViewById<ListView>(R.id.listView)
                    rec.setOnItemClickListener { adapterView, view, i, l ->
                        val select = sRes.items[i]
                        //  <= open URL =>
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(select.html_url))
                        Log.d("HTML-URL", "clicked on item $intent")
                        startActivity(intent)
                    }
                    val adap = SearchAdapter(this@SearchActivity, android.R.layout.simple_list_item_1, sRes.items)
                    rec.adapter = adap
                }
            }
            override fun onFailure(call: Call<SearchResults>, t: Throwable) {

            }
        }
        val ret = APIRetriever()
        ret.getRepos(call, search!!)
    }
}

class SearchAdapter(context: Context, resource: Int, objects: List<Repo>) : ArrayAdapter<Repo> (context, resource, objects) {

    override fun getCount(): Int {
        return super.getCount()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        //return super.getView(position, convertView, parent)
        val i = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = i.inflate(R.layout.repo_list_layout, parent, false)

        val tView = view.findViewById<TextView>(R.id.textView)
        val iView = view.findViewById<ImageView>(R.id.imageView2)
        val item = getItem(position)
        Picasso.with(context).load(Uri.parse(item?.owner?.avatar_url)).into(iView)

        tView.text = item!!.full_name
        return view
    }
}