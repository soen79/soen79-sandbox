package org.springsource.cloudfoundry.mvc.services;

import java.net.URLDecoder;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springsource.cloudfoundry.util.XmlReader;

/**
 * This class handles the Subscription to an application
 * 
 * @author bb
 * 
 */
@Controller
@RequestMapping("/login")
public class LoginController {

	static Logger log = Logger.getLogger(LoginController.class);

	
	/**
	 * Handler method for buying subscription.
	 * @param eventUrl
	 * @return an XML string
	 */
	@RequestMapping(value="/app", method = {RequestMethod.GET})
	public ModelAndView  orderSubscription( @RequestParam(value="openid")  String openid, @RequestParam(value="accountIdentifier")  String accountIdentifier){
		
		log.debug("---------------------------INSIDE login CONTROLLER---------------");
		String openIdDecoded = null;
		try {
			
			openIdDecoded = URLDecoder.decode(openid, "UTF-8");
			log.debug("-->Invoked with openId: " + openIdDecoded);
			log.debug("-->AccountIdentifier: " + accountIdentifier);
			
			//Would've used this to invoke URL in {eventUrl}
			//InternalRestClient client =new InternalRestClient();
			//String resp = client.invokeEmbeddedURL(urlDecoded);
			
			//xmlResponse = XmlReader.readXML("buyNotificationResponse.xml");
			
		}catch (Exception ex){
			log.error(ex);
		}
		
		log.debug("Redirecting to : " + openIdDecoded);
		return new ModelAndView("redirect:" + openIdDecoded);
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
		
		return null;
	}
	
		
	
	
}
