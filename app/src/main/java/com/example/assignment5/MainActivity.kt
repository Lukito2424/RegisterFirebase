package com.example.assignment5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
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
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()
            val repeatpassword = editTextRepeatPassword.text.toString().trim()


            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                editTextEmail.error = "Enter a Valid Email"
                return@setOnClickListener
            }else if( password.length < 9 ){
                editTextPassword.error = "Password's length must be at least 9"
                return@setOnClickListener
            } else if(!(password.matches(".*[a-zA-Z].*".toRegex())) || !(password.matches(".*[0-9].*".toRegex()))){
                editTextPassword.error = "Password must contain both Symbols and Digits"
                return@setOnClickListener
            } else if(password != repeatpassword){
                editTextRepeatPassword.error = "Passwords must match"
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