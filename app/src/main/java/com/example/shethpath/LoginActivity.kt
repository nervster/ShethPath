package com.example.shethpath

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.parse.ParseUser
import com.parse.RequestPasswordResetCallback

class LoginActivity : AppCompatActivity() {

    lateinit var etUsername: EditText
    lateinit var etPassword: EditText
    lateinit var etButton: Button
    lateinit var etSignupButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        // check if user has been logged in
        if (ParseUser.getCurrentUser() != null) {
            // if logged in, then take to main activity
            goToMainActivity()
        }



        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        etButton = findViewById(R.id.loginButton)
        etButton.setOnClickListener{
            Toast.makeText(this, "Login was clicked", Toast.LENGTH_SHORT).show()
            login()
        }

        etSignupButton = findViewById<Button>(R.id.signupButton)
        etSignupButton.setOnClickListener{
            signup(etUsername.text.toString(), etPassword.text.toString())
        }

    }

    private fun signup(username: String, password: String) {
        val user = ParseUser()

        user.setUsername(username)
        user.setPassword(password)

        user.signUpInBackground {
            e ->
            if (e== null) {
                Log.i(TAG, "Sign Up Successful")
                goToMainActivity()
            } else {
                e.printStackTrace()
            }
        }

    }

    private fun login() {
        val username: String = etUsername.text.toString()
        val password: String = etPassword.text.toString()
        Log.i(TAG, "Username: $username and Password: $password")
        ParseUser.logInInBackground(username, password, ({user, e ->
            if (user != null) {
                Log.i(TAG, "Log in Successful")
                goToMainActivity()

            } else {
                Toast.makeText(this, "Error Logging in", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }))
    }

    private fun goToMainActivity() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)


    }

    companion object {
        const val TAG = "LoginActivity"
    }
}