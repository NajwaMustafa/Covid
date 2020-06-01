package com.example.covid19.pasien

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.example.covid19.R
import kotlinx.android.synthetic.main.activity_pasien.*
import org.json.JSONObject

class PasienActivity : AppCompatActivity() {

    var arrayList = ArrayList<Pasien>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pasien)

        supportActionBar?.title = "Data Pasien"

        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(this)

        mFloatingActionButton.setOnClickListener {
            startActivity(Intent(this, ManagePasienActivity::class.java))

        }
    }


    override fun onResume() {
        super.onResume()
        loadAllPasien()
    }

    private fun loadAllPasien (){
        val loading = ProgressDialog(this)
        loading.setMessage("Loading")
        loading.show()
        AndroidNetworking.get(PasienApiEndPoint.READ)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    arrayList.clear()
                    val jsonArray = response?.optJSONArray("result")

                    if(jsonArray?.length() == 0){
                        loading.dismiss()
                        Toast.makeText(applicationContext,"Patient data is empty, Add the data first", Toast.LENGTH_SHORT).show()
                    }
                    for(i in 0 until jsonArray?.length()!!){

                        val jsonObject = jsonArray?.optJSONObject(i)
                        arrayList.add(
                            Pasien(
                                jsonObject.getString("id"),
                                jsonObject.getString("nama"),
                                jsonObject.getString("alamat"),
                                jsonObject.getString("gender"),
                                jsonObject.getString("gejala")
                            )
                        )

                        if(jsonArray?.length() - 1 == i){

                            loading.dismiss()
                            val adapter =
                                RVAdapterPasien(applicationContext, arrayList)
                            adapter.notifyDataSetChanged()
                            mRecyclerView.adapter = adapter

                        }

                    }
                }

                override fun onError(anError: ANError?) {
                    loading.dismiss()
                    Log.d("ONERROR",anError?.errorDetail?.toString())
                    Toast.makeText(applicationContext,"Connection Failure", Toast.LENGTH_SHORT).show()
                }
            })
    }
    }
