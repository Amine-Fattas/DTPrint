package com.impression.dtprint.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.impression.dtprint.MainActivity
import com.impression.dtprint.R
import com.impression.dtprint.dao.ConnectionDB
import com.impression.dtprint.models.Client
import com.impression.dtprint.models.CurrentClient
import models.User

class LoginFragment : Fragment() {

    val clientsRef = ConnectionDB.db.collection("Client")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_login, container, false)

        val usernameField = view.findViewById<EditText>(R.id.input_login_username)
        val passwordField = view.findViewById<EditText>(R.id.input_login_password)

        val btnLogin = view.findViewById<Button>(R.id.login_btn)



        btnLogin.setOnClickListener {
            val username = usernameField.text.toString()
            val password = passwordField.text.toString()

            if (CurrentClient.loggedIn){
                Toast.makeText(activity, "Already logged in", Toast.LENGTH_LONG).show()
            }
            else if(username.trim() == "" && password.trim() == ""){
                Toast.makeText(activity, "Error : Empty Field", Toast.LENGTH_LONG).show()
            }
            else {
                val result =
                    clientsRef.whereEqualTo("username", username).whereEqualTo("password", password)
                        .get()
                        .addOnSuccessListener {
                            val list = it.toObjects(Client::class.java)
                            if(list.count() > 0){
                                CurrentClient.login(list.get(0))
                                Toast.makeText(activity, "Login successful : " + CurrentClient.user!!.username, Toast.LENGTH_LONG).show()
                                startActivity(Intent(activity!!, MainActivity::class.java))
                            }
                            else{
                                CurrentClient.loggedIn = false
                                Toast.makeText(activity, "Incorrect", Toast.LENGTH_LONG).show()
                            }
                        }
                        .addOnFailureListener {
                            println(it.message)
                            Toast.makeText(MainActivity(), "Error", Toast.LENGTH_LONG).show()

                        }

            }


        }

        return view
    }


}
