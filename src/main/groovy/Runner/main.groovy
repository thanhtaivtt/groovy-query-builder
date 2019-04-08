package Runner

import Core.Query
import groovy.json.JsonOutput
import groovy.sql.Sql
import io.vertx.*
import io.vertx.core.Vertx
import io.vertx.core.json.Json
import io.vertx.ext.web.Router

//import oracle.jdbc.

def connString = "jdbc:oracle:thin:@ad.toidicode.com:1521/VTDB1.adpia.vn";
def username = "vinadream";
def password = "qpxmska1305vt";


query = new Query(connString, username, password);

query.from('TACCOUNT');
//query.where('ACCOUNT_ID', '%thanhtai%', 'LIKE');

//println (query.run());

def PORT = 8080


vertx = Vertx.vertx();

def router = Router.router(vertx)

router.route("/:offset/:limit")
        .handler({
    routingContext ->
        def offset = routingContext.request().getParam('offset');
        def limit = routingContext.request().getParam('limit');


        // This handler will be called for every request
        def response = routingContext.response()
        response.putHeader("content-type", "text/plain")
//        data = query.run(offset.toInteger(), limit.toInteger());
        data = query.first();
//        print data
        def json = JsonOutput.toJson(data).toString()



        // Write to the response and end it
        response.end(json)
})


def httpServer = vertx.createHttpServer()
httpServer.requestHandler(router.&accept).listen(PORT)
print "Product running in ${PORT}"

