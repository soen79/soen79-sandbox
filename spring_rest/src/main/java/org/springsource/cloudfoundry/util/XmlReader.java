package org.springsource.cloudfoundry.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

public class XmlReader {

	static Logger log = Logger.getLogger(XmlReader.class);
			
	public static String readXML(String filePath) {

		StringBuilder sb = new StringBuilder();
		try {

			InputStream is = XmlReader.class.getClassLoader().getResourceAsStream(filePath);
			System.out.println("Working Directory = "+ System.getProperty("user.dir"));
			//BufferedReader br = new BufferedReader(new FileReader(new File(filePath)));

			//BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			String line;

			while ((line = br.readLine()) != null) {
				sb.append(line.trim());
			}

			log.debug("Extracted XML string: "+ sb.toString());
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return sb.toString();
	}
	
}
