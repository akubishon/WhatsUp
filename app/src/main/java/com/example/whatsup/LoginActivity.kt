package com.example.whatsup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.login_activity.*
import kotlinx.android.synthetic.main.login_activity.etEmail
import kotlinx.android.synthetic.main.login_activity.etPassword

import kotlinx.android.synthetic.main.welcome_activity.btnLogin

class LoginActivity : AppCompatActivity() {
    private lateinit var mAuth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)


        mAuth = FirebaseAuth.getInstance()
        btnLogin.setOnClickListener{
            loginUser2()
        }


    }
    private fun loginUser2() {
        if (etPassword.text.toString().isNotBlank() and etEmail.text.toString().isNotBlank()) {
            mAuth.signInWithEmailAndPassword(etEmail.text.toString(), etPassword.text.toString()).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                }else{Toast.makeText(this@LoginActivity,"Error message:"+task.exception!!.message.toString(), Toast.LENGTH_LONG).show()}

            }
        }else {
            Toast.makeText(this@LoginActivity, "email wel invoeren man", Toast.LENGTH_LONG).show()
        }
    }




     fun loginUser() {
        val email: String = etEmail.toString()
        val password: String = etPassword.toString()

        if (email == "") {
            Toast.makeText(this@LoginActivity, "email wel invoeren man", Toast.LENGTH_LONG)
        } else if (password == "") {
            Toast.makeText(this@LoginActivity, "password wel invoeren man", Toast.LENGTH_LONG)
        } else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful){
                        val intent  = Intent(this@LoginActivity, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish()
            }else {
                    Toast.makeText(this@LoginActivity,"Error message:"+task.exception!!.message.toString(), Toast.LENGTH_LONG)

                }

            }
        }
    }
}