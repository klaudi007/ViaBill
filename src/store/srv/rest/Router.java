package srv.rest;


import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONException;

import bsn.Central;


/**
 * @author Musa Aliyev
 */

@Path("/secured")
public class Router {

	
//	*********************************************************************
//    *****  new Router Rest method for controlling all things   *****
//***********************************************************************	

	
	/**
	 *    sa  - select all  items
	 *    sp - select particular
	 *    spf - select particular by FK
	 *    c  - create 
	 *    u  - update 
	 *    d  - delete   
	 *    cc - coctail 
	 *    gc - get Count of particular table records (Element records)  
	 *    gle - get limited element
	 *    st  - statistics
	 *    ud  - Universal Dictionary
	 *    
	 *          INPUT parameters and Methods
	 *    sa   -   non
	 *    sp   -   id  
	 *    spf  -   fk_id
	 *    c    -   requestEntity
	 *    u    -   id and requestEntity 
	 *    d    -   id      
	 *    cc   -   undefined
	 *    gc   -   non
	 *    gle  -   get limited element start position for limit
	 *    st   -   Date range and session data and other requestEntity
	 *             
	 *             
	 *             a - action
	 *             e - element may be id
	 *             d - details
	 *             stp - id here as state 0 mkr ; 1 score = stps - statika personal mkr and score
	 *             pr - personal report
	 * **/
	

	
	@POST
	@Path("/router/{a}/{e}/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Object centralPostRouter(
			@DefaultValue("non") @PathParam("a") String a,
			@DefaultValue("non") @PathParam("e") String e,
			@DefaultValue("99999999999") @PathParam("id") String id,
			Object requestEntity) throws JSONException {
		int intId;
		try{
			intId=Integer.parseInt(id);
		}
		 catch (Exception err) {
	            System.out.println("Exception: "+err);
			return  Response.status(422).build();	
			}

		try{

			if(a.equals("list")){
			
				if(e.equals("company")){
					return Response.ok(Central.getAllCompany()).build();
				}
			}

			else if(a.equals("select")){
//				select for update
				if(e.equals("company")){
					return Response.ok(Central.getParticularCompany(intId)).build();
				}
			}
			
			else if(a.equals("create")){
				
				if(e.equals("company")){
					return Response.ok(Central.createCompany(requestEntity, "by Me")).build();
				}
			}
			else if(a.equals("update")){
				if(e.equals("company")){
					return Response.ok(Central.updateCompany(requestEntity, intId, "by Me")).build();
				}
			}
			else if(a.equals("remove")){
				
				if(e.equals("company")){
					return Response.ok(Central.removeCompany(intId)).build();
				}

			}

			
		} catch (Exception err) {
			System.out.println("Exception: "+err);
			return  Response.status(Response.Status.BAD_REQUEST).build();
			
		}

			return Response.status(Response.Status.OK).build();
	}
	

	
}
