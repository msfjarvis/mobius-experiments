package dev.msfjarvis.login

interface LoginApi {
    /**
     * Given a [username] and [password], calls into the login API and returns a
     * [OAuthToken].
     */
    fun login(username: Username, password: Password): OAuthToken
}
