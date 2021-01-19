package dev.msfjarvis.login

data class Username(val username: String) {
    fun validate(): List<ValidationError> {
        return if (username.isEmpty()) {
            listOf(ValidationError.InvalidUsername)
        } else {
            emptyList()
        }
    }
    companion object {
        val BLANK = Username("")
    }
}
