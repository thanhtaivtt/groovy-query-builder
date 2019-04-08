package Runner

class Main {
    Main () {
        def server = vertx..createHttpServer()

        server.requestHandler({ request ->

            // This handler gets called for each request that arrives on the server
            def response = request.response()
            response.putHeader("content-type", "text/plain")

            // Write to the response and end it
            response.end("Hello World!")
        })

        server.listen(8080)
    }
}
