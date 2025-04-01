package example.com.repositories

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email

class AuthRepository(private val supabase: SupabaseClient) {
    var userId: String? = null
    suspend fun signUpUser(emailAddress: String, passwordKey: String) {
        supabase.auth.signUpWith(Email) {
            email = emailAddress
            password = passwordKey
        }
    }

    suspend fun signInUser(email: String, password: String) {
        supabase.auth.signInWith(Email) {
            this.email = email
            this.password = password
        }
        userId = supabase.auth.currentUserOrNull()?.id
    }
}
