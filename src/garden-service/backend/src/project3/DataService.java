package project3;

import java.util.Date;
import java.util.LinkedList;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.WorkerExecutor;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.TimeoutHandler;

public class DataService extends AbstractVerticle {

	private int port;
	private static final int MAX_SIZE = 10;
	private LinkedList<DataPoint> values;
	SerialCommChannel channel = new SerialCommChannel("/dev/cu.usbmodem14101", 9600);
	
	public DataService(int port) throws Exception {
		values = new LinkedList<>();		
		this.port = port;
	}
	
	@Override
	public void start() {		
		Router router = Router.router(vertx);
        router.route().handler(io.vertx.ext.web.handler.CorsHandler.create(".*.")
                .allowedMethod(io.vertx.core.http.HttpMethod.GET)
                .allowedMethod(io.vertx.core.http.HttpMethod.POST)
                .allowedMethod(io.vertx.core.http.HttpMethod.OPTIONS)
                .allowedMethod(HttpMethod.PUT)
                .allowedHeader("Access-Control-Request-Method")
                .allowedHeader("Access-Control-Allow-Origin")
                .allowedHeader("Access-Control-Allow-Headers")
                .allowedHeader("Content-Type"));
        router.route().handler(BodyHandler.create());
        router.errorHandler(500, rc -> {
            System.err.println("Handling failure");
            Throwable failure = rc.failure();
            if (failure != null) {
                failure.printStackTrace();
            }
        });
		router.post("/api/data").handler(this::handleAddNewData);
		router.get("/api/data").handler(this::handleGetData);		
		vertx
			.createHttpServer()
			.requestHandler(router)
			.listen(port);

		log("Service ready.");
	}
	
	private void handleAddNewData(RoutingContext routingContext) {
		String msg = "";
        if(channel.isMsgAvailable()){
            try{
                msg = channel.receiveMsg();
                System.out.println(msg);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
		HttpServerResponse response = routingContext.response();
		// log("new msg "+routingContext.getBodyAsString());
		JsonObject res = routingContext.getBodyAsJson();
		if (res == null) {
			sendError(400, response);
		} else {            
			int tempLvl = res.getInteger("tempLivello");
			int fotoresLvl = res.getInteger("fotoresLivello");
			String stato = res.getString("stato");
			long time = System.currentTimeMillis();
            

            String manMode = "";
            if(msg.length() != 0 && msg.equals("MANUALE")) {
                manMode = "on";
            } else {
                manMode = "off";
            }

            values.addFirst(new DataPoint(tempLvl, fotoresLvl, time, stato, manMode));

            if (values.size() > MAX_SIZE) {
                values.removeLast();
            }

            log("Temp Livello: " + tempLvl + " Stato: " + stato + " Time: " + new Date(time) + " FotoResLevel: " + fotoresLvl + " Modalita manuale: "+ manMode);
            response.setStatusCode(200).end();

            vertx.executeBlocking(
                    promise -> {
                        channel.sendMsg("t" + tempLvl + " " + "s" + stato + " " + "f" + fotoresLvl);
                        promise.complete();
                    });
        }
	}
	
	private void handleGetData(RoutingContext routingContext) {
		JsonArray arr = new JsonArray();
        for (DataPoint p: values) {
            JsonObject data = new JsonObject();
            data.put("time", p.getTime());
            data.put("tempLvl", p.getTempLevel());
            data.put("stato", p.getStato());
            data.put("fotoResLvl", p.getFotoResLevel());
            data.put("manMode", p.getManMode());
            
            arr.add(data);
        }
        routingContext.response()
                .putHeader("content-type", "application/json")
                .end(arr.encodePrettily());

	}
	
	private void sendError(int statusCode, HttpServerResponse response) {
		response.setStatusCode(statusCode).end();
	}

	private void log(String msg) {
		System.out.println("[DATA SERVICE] "+msg);
	}

}