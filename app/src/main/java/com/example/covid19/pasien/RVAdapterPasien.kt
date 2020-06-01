package com.example.covid19.pasien

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.covid19.R
import kotlinx.android.synthetic.main.activity_gejala.view.*
import kotlinx.android.synthetic.main.activity_gejala.view.gejala
import kotlinx.android.synthetic.main.pasien_list.view.*

class RVAdapterPasien(private val context: Context, private val arrayList: ArrayList<Pasien>) :
    RecyclerView.Adapter<RVAdapterPasien.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.pasien_list, parent,
                false))
    }

    override fun getItemCount(): Int = arrayList!!.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.view.tvid.text = arrayList?.get(position)?.id
        holder.view.nama.text = "Nama : " + arrayList?.get(position)?.nama
        holder.view.alamat.text = "Alamat : " + arrayList?.get(position)?.alamat
        holder.view.gender.text = "Gender : " + arrayList?.get(position)?.gender
        holder.view.tvgejala.text = "Gejala : " + arrayList?.get(position)?.gejala

        holder.view.cvList.setOnClickListener {
            val i = Intent(context, ManagePasienActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            i.putExtra("editmode", "1")
            i.putExtra("id", arrayList?.get(position)?.id)
            i.putExtra("nama", arrayList?.get(position)?.nama)
            i.putExtra("alamat", arrayList?.get(position)?.alamat)
            i.putExtra("gender", arrayList?.get(position)?.gender)
            i.putExtra("gejala", arrayList?.get(position)?.gejala)
            context.startActivity(i)
        }
    }

    class Holder(val view: View) : RecyclerView.ViewHolder(view)

    }
