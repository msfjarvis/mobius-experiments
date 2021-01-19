package dev.msfjarvis.login

data class Password(val password: String) {
    fun validate(): List<ValidationError> {
        return if (password.isEmpty()) {
            listOf(ValidationError.InvalidPassword)
        } else {
            emptyList()
        }
    }
    companion object {
        val BLANK = Password("")
    }
}
