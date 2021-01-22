package dev.msfjarvis.login

class FakeLoginApi : LoginApi {
    override fun login(username: Username, password: Password): OAuthToken {
        return OAuthToken("authentication-token")
    }
}
