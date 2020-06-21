package com.example.whatsup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var mAuth : FirebaseAuth
    private lateinit var refUsers: DatabaseReference
    private var firebaseUserID: String = ""



    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

     mAuth = FirebaseAuth.getInstance()
        button()

    }
    private fun button(){
        btnRegister.setOnClickListener{
            registerUser2()
        }
    }

    private fun registerUser2(){
      if (etPassword.text.toString().isNotBlank() and etEmail.text.toString().isNotBlank() and etUsername.text.toString().isNotBlank()) {

          mAuth.createUserWithEmailAndPassword(etEmail.text.toString(),etPassword.text.toString())
              .addOnCompleteListener{ task ->
                  if (task.isSuccessful){
                      firebaseUserID = mAuth.currentUser!!.uid
                      refUsers = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUserID)
                      val userHashMap = HashMap<String, Any>()
                      userHashMap["uid"] = firebaseUserID
                      userHashMap["username"] = etUsername.toString()
                      userHashMap["status"] = "offline"
                      userHashMap["profile"] = "https://firebasestorage.googleapis.com/v0/b/whatsup-enes.appspot.com/o/profile.png?alt=media&token=35b0e1a9-1cde-4615-b5c3-b2a91f9c07e5"
                      userHashMap["search"] = etUsername.toString().toLowerCase()
                      refUsers.updateChildren(userHashMap)

                          .addOnCompleteListener { task ->
                              if (task.isSuccessful){
                                  val intent  = Intent(this@RegisterActivity, MainActivity::class.java)
                                  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                  startActivity(intent)
                                  finish()
                              }
                          }

                  }
                  else{
                      Toast.makeText(this@RegisterActivity,"Error message:"+task.exception!!.message.toString(), Toast.LENGTH_LONG)

                  }
              }
      } else {
          Toast.makeText(this@RegisterActivity, "email wel invoeren man", Toast.LENGTH_LONG).show()
      }
    }

    private fun registerUser() {
        val email: String = itEmail.toString()
        val password: String = itPassword.toString()
        val username: String = itUsername.toString()

        if (email == "") {
            Toast.makeText(this@RegisterActivity, "email wel invoeren man", Toast.LENGTH_LONG).show()
        } else if (password == "") {
            Toast.makeText(this@RegisterActivity, "password wel invoeren man", Toast.LENGTH_LONG).show()
        } else if (username == "") {
            Toast.makeText(this@RegisterActivity, "password wel invoeren man", Toast.LENGTH_LONG).show()
        }else{
            mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener{ task ->
                    if (task.isSuccessful){
                        firebaseUserID = mAuth.currentUser!!.uid
                        refUsers = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUserID)
                        val userHashMap = HashMap<String, Any>()
                        userHashMap["uid"] = firebaseUserID
                        userHashMap["username"] = username
                        userHashMap["status"] = "offline"
                        userHashMap["profile"] = "https://firebasestorage.googleapis.com/v0/b/whatsup-enes.appspot.com/o/profile.png?alt=media&token=35b0e1a9-1cde-4615-b5c3-b2a91f9c07e5"
                        userHashMap["search"] = username.toLowerCase()
                        refUsers.updateChildren(userHashMap)

                            .addOnCompleteListener { task ->
                                if (task.isSuccessful){
                                    val intent  = Intent(this@RegisterActivity, MainActivity::class.java)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                    startActivity(intent)
                                    finish()
                                }
                            }

                    }

                    else{
                        Toast.makeText(this@RegisterActivity,"Error message:"+task.exception!!.message.toString(), Toast.LENGTH_LONG)

                    }
                }


        }
    }

}