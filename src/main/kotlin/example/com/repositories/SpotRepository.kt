package example.com.repositories

import example.com.models.Spot
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class SpotRepository(private val supabase: SupabaseClient) {
    // Get all spots from the database
    suspend fun getAllSpots(): List<Spot> = withContext(Dispatchers.IO) {
        supabase.from("spots")
            .select()
            .decodeList<Spot>()
    }

    // Save a spot to the database
    suspend fun save(spot: Spot): Int = runBlocking {
        val result = supabase.from("spots")
            .insert(spot)
            .decodeSingle<Spot>()
        return@runBlocking result.id ?: throw IllegalStateException("Failed to retrieve the generated Record")
    }

    // Update a spot in the database
    suspend fun update(spot: Spot): Boolean = runBlocking {
        supabase.from("spots")
            .update(spot) {

            }
        return@runBlocking true
    }

    // Delete a spot from the database
    suspend fun delete(id: Int): Boolean = runBlocking {
        val response = supabase.from("spots")
            .delete {
                    }
        return@runBlocking true
    }
}
