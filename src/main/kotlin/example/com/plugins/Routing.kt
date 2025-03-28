package example.com.plugins

import example.com.models.Spot
import example.com.repositories.SpotRepository
import example.com.services.SpotService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.application.Application
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.sql.Connection

fun Application.configureRouting() {
    val dbConnection: Connection = connectToPostgres(embedded = true)
    val spotRepository = SpotRepository(dbConnection) // Pass connection
    val spotService = SpotService(spotRepository) // Pass repository

    routing {
        get("/spots") {
            try {
                val spots = spotService.getAllSpots()
                call.respond(spots)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "Error retrieving spots: ${e.localizedMessage}")
            }
        }

        post("/spots") {
            try {
                val spot = call.receive<Spot>()
                val spotId = spotService.createSpot(spot)
                call.respond(HttpStatusCode.Created, "Spot created with ID: $spotId")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Error creating spot: ${e.localizedMessage}")
            }
        }

        patch("/spots") {
            try {
                val spot = call.receive<Spot>()
                spotService.updateSpot(spot)
                call.respond(HttpStatusCode.OK, "Spot updated")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "Error updating spot: ${e.localizedMessage}")
            }
        }

        delete("/spots/{id}") {
            try {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid ID")
                    return@delete
                }
                val deleted = spotService.deleteSpot(id)
                if (deleted) {
                    call.respond(HttpStatusCode.OK, "Spot deleted")
                } else {
                    call.respond(HttpStatusCode.NotFound, "Spot not found")
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "Error deleting spot: ${e.localizedMessage}")
            }
        }
    }
}
