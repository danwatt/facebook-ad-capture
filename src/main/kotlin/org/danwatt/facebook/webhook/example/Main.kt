package org.danwatt.facebook.webhook.example


import com.fasterxml.jackson.databind.PropertyNamingStrategy
import org.jooby.Kooby
import org.jooby.json.Jackson
import org.jooby.run

class Main : Kooby({
    use(Jackson().doWith { it.propertyNamingStrategy = PropertyNamingStrategy.SNAKE_CASE })

    /*put("/user-profile/:id") { request ->
        val id = request.param("id").value
        val body = request.body(Profile::class.java)
         service.save(id, body)
    }*/
    get("/webhook") { request, response ->
        val mode = request.param("hub.mode").value()
        val challenge = request.param("hub.challenge").value()
        val verify = request.param("hub.verify_token").value()

        response.status(200).type("text/plain").send(challenge)
    }
    post ("/webhook") {request,response ->
        println(request.body().value())
    }
    get("/test") { request, response ->
        response.status(200).type("application/json").send(mapOf("test" to "ok"))
        // val id = request.param("id").value
        //val found = service.get(id)
        /* when {
             found.isPresent -> response.status(200).type("application/json").send(found.get())
             else -> response.status(404)
         }
         */
    }
})

fun main(args: Array<String>) {
    run(::Main, *args)
}
