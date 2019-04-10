package com.example.intento6login

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var txtUser: EditText
    private lateinit var txtPassword: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        txtUser = findViewById(R.id.txtUser)
        txtPassword = findViewById(R.id.txtPassword)

        progressBar = findViewById(R.id.progressBar1)

        auth = FirebaseAuth.getInstance()

        val showButtonRegister = findViewById<Button>(R.id.buttonRegis)
        showButtonRegister.setOnClickListener{val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)}
    }

    fun olvidoPassword(view: View) {
        startActivity(Intent(this, ForgotPasswordActivity::class.java))
    }
    fun registro(view: View) {
        startActivity(Intent(this, RegisterActivity::class.java))

    }
    fun loging(view: View) {
        loginUser()
    }

    private fun loginUser() {
        val user: String = txtUser.text.toString()
        val password: String = txtPassword.text.toString()

        if(!TextUtils.isEmpty(user) && !TextUtils.isEmpty(password)){
                progressBar.visibility = View.VISIBLE
            auth.signInWithEmailAndPassword(user, password)
                .addOnCompleteListener(this) {
                    task ->
                    if (task.isSuccessful) {
                        action()
                    } else {
                        Toast.makeText(this, "Error en la autenticacion", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }

    private fun action() {
        startActivity(Intent(this, MainActivity::class.java))
    }



}
