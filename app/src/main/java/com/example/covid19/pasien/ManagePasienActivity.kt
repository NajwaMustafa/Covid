package com.example.covid19.pasien

import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.Spinner
import android.widget.SpinnerAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.example.covid19.R
import kotlinx.android.synthetic.main.activity_manage_pasien.*
import org.json.JSONObject

class ManagePasienActivity : AppCompatActivity() {

    lateinit var i: Intent
    private var gender = "Laki - Laki"

    private var gejala:String? = ""
    lateinit var demam: CheckBox
    lateinit var batuk: CheckBox
    lateinit var nyeri: CheckBox
    lateinit var sesak: CheckBox


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_pasien)

        i = intent
        if (i.hasExtra("editmode")) {
            if (i.getStringExtra("editmode").equals("1")) {
                onEditMode()
            }
        }

        demam = findViewById(R.id.demam) as CheckBox
        batuk = findViewById(R.id.batuk) as CheckBox
        nyeri = findViewById(R.id.nyeri) as CheckBox
        sesak = findViewById(R.id.sesak) as CheckBox

        demam.setOnClickListener{
            if (demam.isChecked()){
                gejala += "Demam"
            }
        }
        batuk.setOnClickListener{
            if (batuk.isChecked()){
                gejala += "Batuk"
            }
        }
        nyeri.setOnClickListener{
            if (nyeri.isChecked()){
                gejala += "Nyeri Tenggorokan"
            }
        }
        sesak.setOnClickListener{
            if (sesak.isChecked()){
                gejala += "Sesak Napas"
            }
        }

        rgGender.setOnCheckedChangeListener { radioGroup, i ->
            when (i) {
                R.id.radioBoy -> {
                    gender = "Laki - Laki"
                }
                R.id.radioGirl -> {
                    gender = "Perempuan"
                }
            }
        }

        btnCreate.setOnClickListener {
            create()
        }

        btnUpdate.setOnClickListener {
            update()
        }

        btnDelete.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Konfirmasi")
                .setMessage("Hapus data ini ?")
                .setPositiveButton("HAPUS", DialogInterface.OnClickListener { dialogInterface, i ->
                    delete()
                })
                .setNegativeButton("BATAL", DialogInterface.OnClickListener { dialogInterface, i ->
                    dialogInterface.dismiss()
                })
                .show()
        }
    }

    private fun onEditMode() {
        txId.setText(i.getStringExtra("id"))
        txNama.setText(i.getStringExtra("nama"))
        txAlamat.setText(i.getStringExtra("alamat"))
        txId.isEnabled = false

        btnCreate.visibility = View.GONE
        btnUpdate.visibility = View.VISIBLE
        btnDelete.visibility = View.VISIBLE

        gender = i.getStringExtra("gender")

        if (gender.equals("Laki - Laki")) {
            rgGender.check(R.id.radioBoy)
        } else {
            rgGender.check(R.id.radioGirl)
        }
    }

    private fun <T> ArrayAdapter(function: () -> ManagePasienActivity): SpinnerAdapter? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun create() {
        val loading = ProgressDialog(this)
        loading.setMessage("Menambahkan data...")
        loading.show()
        AndroidNetworking.post(PasienApiEndPoint.CREATE)
            .addBodyParameter("id", txId.text.toString())
            .addBodyParameter("nama", txNama.text.toString())
            .addBodyParameter("alamat", txAlamat.text.toString())
            .addBodyParameter("gender", gender)
            .addBodyParameter("gejala", gejala)

            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {

                override fun onResponse(response: JSONObject?) {
                    loading.dismiss()
                    Toast.makeText(applicationContext, response?.getString("message"), Toast.LENGTH_SHORT).show()

                    if (response?.getString("message")?.contains("successfully")!!) {
                        this@ManagePasienActivity.finish()
                    }
                }

                override fun onError(anError: ANError?) {
                    loading.dismiss()
                    Log.d("ONERROR", anError?.errorDetail?.toString())
                    Toast.makeText(applicationContext, "Connection Failure", Toast.LENGTH_SHORT).show()
                }


            })
    }

    private fun update() {
        val loading = ProgressDialog(this)
        loading.setMessage("Mengubah data...")
        loading.show()

        AndroidNetworking.post(PasienApiEndPoint.UPDATE)
            .addBodyParameter("id", txId.text.toString())
            .addBodyParameter("nama", txNama.text.toString())
            .addBodyParameter("alamat", txAlamat.text.toString())
            .addBodyParameter("gender", gender)
            .addBodyParameter("gejala", gejala)

            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {

                override fun onResponse(response: JSONObject?) {

                    loading.dismiss()
                    Toast.makeText(applicationContext, response?.getString("message"), Toast.LENGTH_SHORT).show()

                    if (response?.getString("message")?.contains("successfully")!!) {
                        this@ManagePasienActivity.finish()
                    }

                }

                override fun onError(anError: ANError?) {
                    loading.dismiss()
                    Log.d("ONERROR", anError?.errorDetail?.toString())
                    Toast.makeText(applicationContext, "Connection Failure", Toast.LENGTH_SHORT).show()
                }


            })

    }

    private fun delete() {
        val loading = ProgressDialog(this)
        loading.setMessage("Menghapus data...")
        loading.show()

        AndroidNetworking.get(PasienApiEndPoint.DELETE + "?id=" + txId.text.toString())
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {

                override fun onResponse(response: JSONObject?) {
                    loading.dismiss()
                    Toast.makeText(applicationContext, response?.getString("message"), Toast.LENGTH_SHORT).show()

                    if (response?.getString("message")?.contains("successfully")!!) {
                        this@ManagePasienActivity.finish()
                    }

                }

                override fun onError(anError: ANError?) {
                    loading.dismiss()
                    Log.d("ONERROR", anError?.errorDetail?.toString())
                    Toast.makeText(applicationContext, "Connection Failure", Toast.LENGTH_SHORT).show()
                }


            })
    }
    }
