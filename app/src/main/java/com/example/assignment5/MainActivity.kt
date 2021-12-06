package com.example.assignment5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var  editTextEmail:EditText
    private lateinit var  editTextPassword:EditText
    private lateinit var   editTextRepeatPassword:EditText
    private lateinit var   buttonSubmit: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        registerListeners()
    }


    private fun init(){
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        editTextRepeatPassword = findViewById(R.id.editTextRepeatPassword)
        buttonSubmit = findViewById(R.id.buttonSubmit)
    }

    private fun registerListeners(){
        buttonSubmit.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            val repeatpassword = editTextRepeatPassword.text.toString()
            val passwordDoesntContainDigits =    !(password.contains("0")) && !(password.contains("1")) && !(password.contains("2")) && !(password.contains("3"))
                                              && !(password.contains("4")) && !(password.contains("5")) && !(password.contains("6"))
                                              && !(password.contains("7")) && !(password.contains("8")) && !(password.contains("9"))

            if(!(email.contains("@")) || !(email.contains("."))){
                editTextEmail.error = "Enter a Valid Email !"
                return@setOnClickListener
            }else if( password.length < 9 ){
                editTextPassword.error = "Password's length must be at least 9 !"
                return@setOnClickListener
            } else if(password.isDigitsOnly() || passwordDoesntContainDigits){
                editTextPassword.error = "Password must contain Symbols and Digits !"
                return@setOnClickListener
            } else if(password != repeatpassword){
                editTextRepeatPassword.error = "Passwords must be equal !"
                return@setOnClickListener
            }
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        Toast.makeText(this, "You have successfully registered !", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        Toast.makeText(this, "There was an Error !", Toast.LENGTH_SHORT).show()
                    }
                }

        }
    }
}