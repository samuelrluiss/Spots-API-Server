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
        val response = supabase.from("spots").update({
            set("userId", spot.userId)
            set("address", spot.address)
            set("latitude", spot.latitude)
            set("longitude", spot.longitude)
            set("leavingAt", spot.leavingAt)
            set("createdAt", spot.createdAt)
            set("status", spot.status)
        }) {
            filter {
                spot.id?.let { eq("id", it) } // Ensure only the correct spot is updated
            }
        }

        return@runBlocking true// Check if update was successful
    }



    // Delete a spot from the database
    suspend fun delete(id: Int): Boolean = runBlocking {
        val response = supabase.from("spots")
            .delete(){
                filter {
                    eq("id", id)
                }
            }
        return@runBlocking true
    }
}
