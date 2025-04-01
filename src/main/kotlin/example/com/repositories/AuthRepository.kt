package example.com.repositories

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email

class AuthRepository(private val supabase: SupabaseClient) {
    suspend fun signUpUser(emailAddress: String, passwordKey: String) {
        supabase.auth.signUpWith(Email) {
            email = emailAddress
            password = passwordKey
        }
    }

    suspend fun signInUser(email: String, password: String) {
        val x = supabase.auth.signInWith(Email) {
            this.email = email
            this.password = password
        }
        println(x)
    }
}
