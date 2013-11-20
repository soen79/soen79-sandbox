package org.springsource.cloudfoundry.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.apache.log4j.Logger;

public class InternalRestClient {

	static Logger log = Logger.getLogger(InternalRestClient.class);
			
	/**
	 * Invoke the embedded URL and collect results in StringBuffer
	 * @param sb
	 * @param urlString
	 */
	public String invokeEmbeddedURL(String urlString) {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		URL url;
		try {
			url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			String line;
			br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			if (sb.length() > 0) {
				log.debug("-->Invoked with eventURL: " + sb.toString());
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					log.error(e);
				}
			}
		}
		return sb.toString();
	}
}
