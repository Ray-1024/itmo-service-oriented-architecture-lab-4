package ray1024.soa.collectionservice.controller;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ray1024.soa.collectionservice.soap.gen.PingRequest;
import ray1024.soa.collectionservice.soap.gen.PingResponse;

import static ray1024.soa.collectionservice.config.WsConfig.NAMESPACE_URI;

@Endpoint
public class PingController {
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "PingRequest")
    @ResponsePayload
    public PingResponse ping(@RequestPayload PingRequest request) {
        PingResponse response = new PingResponse();
        response.setMessage(request.getMessage());
        return response;
    }
}