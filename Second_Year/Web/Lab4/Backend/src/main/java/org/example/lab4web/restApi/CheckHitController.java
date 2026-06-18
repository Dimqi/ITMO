package org.example.lab4web.restApi;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.example.lab4web.ejb.CheckHitBean;

import java.math.BigDecimal;
import java.util.List;

@Path("/hit")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CheckHitController {
    @Inject
    CheckHitBean checkHitBean;


    public static class CheckHitRequest{
        public BigDecimal x;
        public BigDecimal y;
        public BigDecimal r;
    }


    public static class CheckHitResponse{
        public BigDecimal x;
        public BigDecimal y;
        public BigDecimal r;
        public boolean hit;
        public CheckHitResponse(BigDecimal x, BigDecimal y, BigDecimal r, boolean hit){
            this.x = x;
            this.y = y;
            this.r = r;
            this.hit = hit;
        }
    }

    @POST
    @Path("/checkHit")
    public Response checkHitRequest(CheckHitRequest  request, @Context SecurityContext securityContext){
        String username = securityContext.getUserPrincipal().getName();
        boolean hit = checkHitBean.checkHit(request.x, request.y, request.r, username);
        return Response.ok(new CheckHitResponse(request.x, request.y, request.r, hit)).build();
    }


    @GET
    @Path("/history")
    public Response getHitHistory(@Context SecurityContext securityContext) {
        String username = securityContext.getUserPrincipal().getName();
        List<CheckHitResponse> history = checkHitBean.getUserResults(username);
        return Response.ok(history).build();
    }
}
