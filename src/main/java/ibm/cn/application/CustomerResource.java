package ibm.cn.application;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;
import org.jose4j.json.internal.json_simple.JSONArray;
import org.jose4j.json.internal.json_simple.JSONObject;

import ibm.cn.application.couchdb.client.CouchDBClientService;
import ibm.cn.application.model.Customer;

@Path("/micro/customer")
public class CustomerResource {
	
	private static final Logger LOG = Logger.getLogger(CustomerResource.class);
	
	@Inject
	@RestClient
	CouchDBClientService cdb;
	
	@Inject
    JsonWebToken jwt;

    // get the customer data from the id_token which is in jwt token format
    @SuppressWarnings("unchecked")
	@GET()
    @Produces(MediaType.APPLICATION_JSON)
//    @RolesAllowed({"user","admin"})
    public Customer getCustomerByJWT() throws Exception{
    	LOG.info("Get customer by jwt");
        try {
            Customer cust = new Customer();
            LOG.info("printing the jwt token");
            LOG.info(jwt.toString());
            jwt.getName();
            cust.setEmail(jwt.getClaim("email"));
            cust.setLastName(jwt.getClaim("family_name"));
            cust.setFirstName(jwt.getClaim("given_name"));
            cust.setUsername(jwt.getClaim("preferred_username"));
            return cust;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.toString());
        }
    }

    // Get the customer data from the CouchDB database
//     @SuppressWarnings("unchecked")
// 	@GET
//     @Produces(MediaType.APPLICATION_JSON)
// //    @RolesAllowed({"user","admin"})
//     public Response getCustomerByUsername() throws Exception{
//     	LOG.info("Get customer by username");
//         try {
//             JSONObject body = new JSONObject();
//             JSONObject selector = new JSONObject();
            
//             selector.put("username", jwt.getName());
            
//             body.put("selector", selector);
//             JSONArray fields = new JSONArray();
//             body.put("fields", fields);
//             body.put("limit", 1);
//             Response r = cdb.getUsername(body);
//             r.getHeaders().remove("Transfer-Encoding");
//             return r;

//         }
//         catch (Exception e){
//             e.printStackTrace();
//             System.out.println(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getLocalizedMessage()).build());
//             throw new Exception(e.toString());
//         }
//     }
}