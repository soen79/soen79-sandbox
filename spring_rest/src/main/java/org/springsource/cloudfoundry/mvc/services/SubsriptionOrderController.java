package org.springsource.cloudfoundry.mvc.services;

import java.net.URLDecoder;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springsource.cloudfoundry.util.XmlReader;

/**
 * This class handles the Subscription to an application
 * 
 * @author bb
 * 
 */
@Controller
@RequestMapping("/subscriptionOrder")
public class SubsriptionOrderController {

	static Logger log = Logger.getLogger(SubsriptionOrderController.class);

	
	/**
	 * Handler method for buying subscription.
	 * @param eventUrl
	 * @return an XML string
	 */
	@ResponseBody
	@RequestMapping(value="/buy", method = {RequestMethod.POST, RequestMethod.GET}, produces="application/xml")
	public String orderSubscription(@RequestParam(value="eventUrl")  String eventUrl){
		
		String xmlResponse = null;	
		log.debug("---------------------------INSIDE orderSubscription CONTROLLER---------------");
		String urlDecoded = null;
		try {
			
			urlDecoded = URLDecoder.decode(eventUrl, "UTF-8");
			log.debug("-->Invoked with eventURL: " + urlDecoded);
			
			//Would've used this to invoke URL in {eventUrl}
			//InternalRestClient client =new InternalRestClient();
			//String resp = client.invokeEmbeddedURL(urlDecoded);
			
			xmlResponse = XmlReader.readXML("successMessage.xml");
			
		}catch (Exception ex){
			log.error(ex);
		}
		
		return xmlResponse;
	}

	/**
	 * Handler method for removing subscription.
	 * 
	 * @param eventUrl
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/remove", method = {RequestMethod.POST, RequestMethod.GET}, produces="application/xml")
	public String removeSubscription(@RequestParam(value="eventUrl")  String eventUrl){
		
		String xmlResponse = null;	
		log.debug("---------------------------INSIDE remove Subription CONTROLLER---------------");
		String urlDecoded = null;
		try {
			
			urlDecoded = URLDecoder.decode(eventUrl, "UTF-8");
			log.debug("-->Invoked with eventURL: " + urlDecoded);
			
			//Would've used this to invoke URL in {eventUrl}
			//InternalRestClient client =new InternalRestClient();
			//String resp = client.invokeEmbeddedURL(urlDecoded);
			
			xmlResponse = XmlReader.readXML("successMessage.xml");
			
		}catch (Exception ex){
			log.error(ex);
		}
		
		return xmlResponse;
	}
	
		
	
	
}
