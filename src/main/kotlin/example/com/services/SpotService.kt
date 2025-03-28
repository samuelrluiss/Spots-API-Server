package example.com.services

import example.com.models.Spot
import example.com.repositories.SpotRepository

class SpotService(private val spotRepository: SpotRepository) {

    suspend fun getAllSpots(): List<Spot> {
        return spotRepository.getAllSpots()
    }

    suspend fun createSpot(spot: Spot): Int {
        return spotRepository.save(spot)
    }

    suspend fun updateSpot(spot: Spot): Boolean {
        return spotRepository.update(spot)
    }

    suspend fun deleteSpot(id: Int): Boolean {
        return spotRepository.delete(id)
    }
}
