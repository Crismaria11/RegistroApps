package com.example.intento6login

class Usuario {

    var id: String? = null
    var nombre: String? = null
    var correo: String? = null

    constructor(id: String, nombre: String, correo: String) {
        this.id = id
        this.nombre = nombre
        this.correo = correo
    }

    fun toMap(): Map<String, Any> {
        val mapping = HashMap<String, Any>()
        mapping.put("nombre", nombre!!)
        mapping.put("correo", correo!!)
        return mapping
    }

}