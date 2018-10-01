package se.mtm.servicesCatalogDiffTool;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

public class ServicesClientProvider {

	private Client client;
	private WebTarget target;
	private String siteKey;
	
	public ServicesClientProvider(String uri, String siteKey){
		this.client = ClientBuilder.newClient();
		this.siteKey = siteKey;
		this.target = this.client.target(uri);
	}
	
	// Services web target
	public WebTarget buildServicesTarget(String path) {
		return this.target().path(path);
	}

	// Services web target
	public WebTarget buildServicesTargetWithCustomSiteKey(String path) {
		return this.target().path(path).queryParam("sitekey", siteKey);
	}
	
	public WebTarget target() {
		return target;
	}

	WebTarget target(String uri) {
		return this.client.target(uri);
	}

	WebTarget addPath(String path) {
		return target.path(path);
	}
}