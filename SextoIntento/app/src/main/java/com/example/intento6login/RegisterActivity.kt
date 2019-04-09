package com.example.intento6login

import android.app.Application
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {

    private lateinit var txtNombre: EditText
    private lateinit var txtApellido: EditText
    private lateinit var txtCorreo: EditText
    private lateinit var txtPassword: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var database: FirebaseFirestore
    private lateinit var auth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        txtNombre = findViewById(R.id.txtNombre)
        txtApellido = findViewById(R.id.txtApellido)
        txtCorreo = findViewById(R.id.txtCorreo)
        txtPassword = findViewById(R.id.txtPassword)

        progressBar = findViewById(R.id.progressBar)

        database = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
    }

    fun registro(view: View) {
        createNewAccount()
    }

    private fun createNewAccount() {
        val nombre: String = txtNombre.text.toString()
        val apellido: String = txtApellido.text.toString()
        val correo: String = txtCorreo.text.toString()
        val password: String = txtPassword.text.toString()

        if(!TextUtils.isEmpty(nombre) && !TextUtils.isEmpty(apellido) && !TextUtils.isEmpty(correo) && !TextUtils.isEmpty(password)) {
                progressBar.visibility = View.VISIBLE

            auth.createUserWithEmailAndPassword(correo,password)
                .addOnCompleteListener(this) {
                    task ->
                    if(task.isComplete) {
                        val user: FirebaseUser? = auth.currentUser
                        verifyEmail(user)

                        val InUser = Usuario(user!!.uid, nombre, correo).toMap()
                        database!!.collection("usuarios")
                            .add(InUser)
                            .addOnSuccessListener {
                                documentReference ->
                                Toast.makeText(applicationContext, "El usuario se ha creado", Toast.LENGTH_SHORT).show()
                            }.addOnFailureListener{
                                    e ->
                                Toast.makeText(applicationContext, "Error al crear usuario", Toast.LENGTH_SHORT).show()
                            }
                        action()
                }
            }
        }
    }


    private fun action() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun verifyEmail(user: FirebaseUser?) {
        user?.sendEmailVerification()
            ?.addOnCompleteListener(this) {
                task ->
                if(task.isComplete) {
                    Toast.makeText(this, "Correo Enviado", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Error al enviar el correo", Toast.LENGTH_LONG).show()
                }


        }
    }
}
