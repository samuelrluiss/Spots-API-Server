package example.com.repositories

import example.com.models.Spot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.sql.Connection

class SpotRepository(private val connection: Connection) {
    companion object {
        private const val SELECT_ALL_SPOTS = "SELECT * FROM spots"
        private const val INSERT_SPOT = "INSERT INTO spots (userId, latitude, longitude, address, leavingAt, createdAt, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)"
        private const val UPDATE_SPOT = "UPDATE spots SET userId = ?, latitude = ?, longitude = ?, address = ?, leavingAt = ?, createdAt = ?, status = ? WHERE id = ?"
        private const val DELETE_SPOT = "DELETE FROM spots WHERE id = ?"
    }

    // Get all spots from the database
    suspend fun getAllSpots(): List<Spot> = withContext(Dispatchers.IO) {
        val statement = connection.prepareStatement(SELECT_ALL_SPOTS)
        val resultSet = statement.executeQuery()

        val spots = mutableListOf<Spot>()
        while (resultSet.next()) {
            spots.add(
                Spot(
                    id = resultSet.getString("id"),
                    userId = resultSet.getString("userId"),
                    latitude = resultSet.getString("latitude"),
                    longitude = resultSet.getString("longitude"),
                    address = resultSet.getString("address"),
                    leavingAt = resultSet.getString("leavingAt"),
                    createdAt = resultSet.getString("createdAt"),
                    status = resultSet.getString("status")
                )
            )
        }
        return@withContext spots
    }

    // Save a spot to the database
    suspend fun save(spot: Spot): Int = withContext(Dispatchers.IO) {
        val statement = connection.prepareStatement(INSERT_SPOT, java.sql.Statement.RETURN_GENERATED_KEYS)
        statement.setString(1, spot.userId)
        statement.setString(2, spot.latitude)
        statement.setString(3, spot.longitude)
        statement.setString(4, spot.address)
        statement.setString(5, spot.leavingAt)
        statement.setString(6, spot.createdAt)
        statement.setString(7, spot.status)
        statement.executeUpdate()

        val generatedKeys = statement.generatedKeys
        if (generatedKeys.next()) {
            return@withContext generatedKeys.getInt(1)
        } else {
            throw Exception("Failed to insert spot")
        }
    }

    // Update a spot in the database
    suspend fun update(spot: Spot): Boolean = withContext(Dispatchers.IO) {
        val statement = connection.prepareStatement(UPDATE_SPOT)
        statement.setInt(1, spot.id.toInt())
        statement.setString(1, spot.userId)
        statement.setString(2, spot.latitude)
        statement.setString(3, spot.longitude)
        statement.setString(4, spot.address)
        statement.setString(5, spot.leavingAt)
        statement.setString(6, spot.createdAt)
        statement.setString(7, spot.status)
        statement.setString(8, spot.id)

        val rowsAffected = statement.executeUpdate()
        return@withContext rowsAffected > 0
    }

    // Delete a spot from the database
    suspend fun delete(id: Int): Boolean = withContext(Dispatchers.IO) {
        val statement = connection.prepareStatement(DELETE_SPOT)
        statement.setInt(1, id)
        val rowsAffected = statement.executeUpdate()
        return@withContext rowsAffected > 0
    }
}
