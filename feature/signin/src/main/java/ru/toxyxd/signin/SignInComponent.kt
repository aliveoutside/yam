package ru.toxyxd.signin

interface SignInComponent {
    fun onTokenReceived(token: String, expiresIn: Long)
}