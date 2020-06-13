package com.vspl.myapplication

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vspl.freeelance.API.RetrofitResponse
import com.vspl.myapplication.api.ApiClient
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var adapter: MoviesAdpter
    var movieList:ArrayList<MovieModel> =ArrayList<MovieModel>()
    var arrMovie = ArrayList<MovieModel>()
    var serchText:String=""
    var timer:Timer?=null
    var  _conn: ConnectionDetector? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        _conn = ConnectionDetector(this)

          getMovies()

        etsearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                timer = Timer()
                timer!!.schedule(
                    object : TimerTask() {
                        override fun run() {
                            if(serchText.length>0) {
                                runOnUiThread {
                                    searchData()
                                }

                            }
                            else{
                                arrMovie.clear()
                                arrMovie.addAll(movieList)
                            }
                        }
                    },
                    1000
                )

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                serchText= s.toString()
                if(timer!=null)
                {
                    timer!!.cancel()
                }
            }
        })

        rretry.setOnClickListener { getMovies() }

    }

    fun getMovies() {
        val pd = ProgressDialog(this)
        pd.setMessage("Loading...")
        pd.setCancelable(false)
       pd.show()
            val call=ApiClient.get(this).movieslist;
            ApiClient.response(call, this@MainActivity,  object : RetrofitResponse {
                override fun response(response: String?) {
                    pd.dismiss()
                    if(response!=null) {
                        var js = JSONObject(response)
                        movieList = Gson().fromJson<ArrayList<MovieModel>>(
                            js.getString("data"),
                            object : TypeToken<ArrayList<MovieModel>>() {}.type
                        ) as ArrayList<MovieModel>
                        arrMovie.addAll(movieList)
                        setAdpter()
                    }
                    else{
                        if(!_conn!!.isConnectingToInternet)
                        {
                            norcord.visibility=View.VISIBLE
                            searchview.visibility=View.GONE
                            Toast.makeText(this@MainActivity,"Please Check your Internet Connection!",Toast.LENGTH_LONG).show()
                        }
                    }

                }
            })



    }


    fun setAdpter() {
        movieslist.layoutManager = LinearLayoutManager(this@MainActivity)
        LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        adapter = MoviesAdpter( this@MainActivity,arrMovie)
        movieslist.adapter = adapter
        movieslist.layoutManager = StaggeredGridLayoutManager( 2,StaggeredGridLayoutManager.VERTICAL)

    }


    fun searchData(){
        arrMovie.clear()
        movieList.filter {
            it.genre!!.toLowerCase().contains(serchText.toLowerCase()) ||  it.title!!.toLowerCase().contains(serchText.toLowerCase())  }.forEach {
            arrMovie.add(it)
        }
        adapter.notifyDataSetChanged()
    }

}


