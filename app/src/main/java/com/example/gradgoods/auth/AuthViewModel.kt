package com.example.gradgoods.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _user = MutableStateFlow<FirebaseUser?>(auth.currentUser)
    val user: StateFlow<FirebaseUser?> = _user

    init{
        auth.signOut()
        _user.value = null
    }

    // SignIn method for logging in users
    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password).await()
                _user.value = auth.currentUser
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Updated signUp method that accepts a callback for handling navigation
    // When sign-up is successful, it calls the provided onSuccess callback
    fun signUp(email: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                auth.createUserWithEmailAndPassword(email, password).await()
                _user.value = auth.currentUser
                onSuccess() // Call the callback once sign-up is successful
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // SignOut method to log out users
    fun signOut() {
        auth.signOut()
        _user.value = null
    }
}