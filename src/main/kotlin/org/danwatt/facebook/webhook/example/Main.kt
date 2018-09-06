package org.danwatt.facebook.webhook.example


import com.fasterxml.jackson.databind.PropertyNamingStrategy
import org.jooby.Kooby
import org.jooby.json.Jackson
import org.jooby.run

class Main : Kooby({
    use(Jackson().doWith { it.propertyNamingStrategy = PropertyNamingStrategy.SNAKE_CASE })

    //Facebook requires a privacy policy page
    get("/privacy") { request, response ->
        response.status(200).type("text/html")
                .send("<p>We dont want your data. We discard it, as this is just a test</p>")
    }
    //Verification
    get("/webhook") { request, response ->
        val mode = request.param("hub.mode").value()
        val challenge = request.param("hub.challenge").value()
        val verify = request.param("hub.verify_token").value()

        response.status(200).type("text/plain").send(challenge)
    }

    post("/webhook") { request, response ->
        println(request.body().value())
        val body = request.body().value()
        //TODO: Decode
        response.status(200)
    }
    get("/test") { request, response ->
        response.status(200).type("application/json").send(mapOf("test" to "ok"))
    }
})

fun main(args: Array<String>) {
    run(::Main, *args)
}
