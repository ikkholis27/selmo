package com.example.selmo.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.selmo.MainActivity
import com.example.selmo.R
import com.example.selmo.apiclient.ApiMain
import com.example.selmo.model.Login
import com.example.selmo.preferance.SharedPreferance
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        edt_email
        if(SharedPreferance().getLoggedInStatus(applicationContext)){
            Log.d("nana ", "adnan :"+SharedPreferance().getLoggedInStatus(applicationContext).toString())
            val intent = Intent(this@LoginActivity, HomeActivity::class.java)
            startActivity(intent)
        }
        btn_login.setOnClickListener {

            if (edt_email.text.toString() == "") {
                edt_email.error = "Email tidak boleh kosong"
            } else if (edt_password.text.toString() == "") {
                edt_password.error = "Password tidak boleh kosong"
            } else {
                getLogin(edt_email.text.toString(),edt_password.text.toString());
            }
        }

    }


    private fun getLogin(username : String ,password: String) {
        ApiMain().services.getAllTeam(username,password).enqueue(object :
            Callback<Login> {
            override fun onResponse(call: Call<Login>, response: Response<Login>) { 
                if(response.code() == 200) {

                    Toast.makeText(applicationContext,"Login Berhasil", Toast.LENGTH_LONG).show()
                    SharedPreferance().setLoggedInUser(baseContext,response.body()?.username,response.body()?.apiToken)
                    SharedPreferance().setLoggedInStatus(baseContext,true)
                    val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(applicationContext,response.code().toString(), Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<Login>, t: Throwable){
                //Tulis code jika response fail
            }
        })
    }

}