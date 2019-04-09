package com.example.intento6login

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var txtCorreo: EditText
    private lateinit var auth: FirebaseAuth
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        txtCorreo = findViewById(R.id.txtCorreo)
        auth = FirebaseAuth.getInstance()

        progressBar = findViewById(R.id.progressBar2)
    }

    fun send(view: View) {
        val correo = txtCorreo.text.toString()

        if (!TextUtils.isEmpty(correo)) {
            auth.sendPasswordResetEmail(correo)
                .addOnCompleteListener(this) { task ->

                    if (task.isSuccessful) {
                            progressBar.visibility = View.VISIBLE
                        startActivity(Intent(this, MainActivity::class.java))
                    } else {
                        Toast.makeText(this, "Error al enviar el correo", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
}
