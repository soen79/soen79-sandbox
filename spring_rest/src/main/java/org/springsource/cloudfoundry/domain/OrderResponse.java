package org.springsource.cloudfoundry.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "orderresponse")
public class OrderResponse {

	String status;
	String statusDescription;
	
	public OrderResponse(){}
	
	public OrderResponse(String status, String statusDescription) {
		super();
		this.status = status;
		this.statusDescription = statusDescription;
	}

	public String getStatus() {
		return status;
	}
	
	@XmlElement
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusDescription() {
		return statusDescription;
	}
	
	@XmlElement
	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}
	
	
}
