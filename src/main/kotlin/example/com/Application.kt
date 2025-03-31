package example.com

import example.com.plugins.*
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.serializer.KotlinXSerializer
import io.ktor.server.application.*
import kotlinx.serialization.json.Json

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureHTTP()
    configureSerialization()

    val supabaseClient = createSupabaseClient("https://wmafluglfxmwvzanrznk.supabase.co", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6IndtYWZsdWdsZnhtd3Z6YW5yem5rIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDM0MTg0NjEsImV4cCI6MjA1ODk5NDQ2MX0.fvwXlLBJkIDU444f-CSnb0R_g0XcPyCV8dYkscRvKiE"){
        defaultSerializer = KotlinXSerializer(Json)
        install(Auth)
        install(Postgrest)
    }

    configureRouting(supabaseClient)
}
