package com.example.covid19.pasien

class PasienApiEndPoint {

    companion object {
        private val SERVER = "https://najwaa.000webhostapp.com/pasien/"
        val CREATE = SERVER+"create.php"
        val READ = SERVER+"read.php"
        val DELETE = SERVER+"delete.php"
        val UPDATE = SERVER+"update.php"

    }
}