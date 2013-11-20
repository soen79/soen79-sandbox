package org.springsource.cloudfoundry.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class UserDAOFileImpl implements UserDAO{

	private final String DB_FILE = "/db/db.txt";
	
	static Logger log = Logger.getLogger(UserDAOFileImpl.class);
	
	public UserDAOFileImpl(){
		
//		List<String> userList = new ArrayList<String>();
//		InputStream is = UserDAOFileImpl.class.getClassLoader().getResourceAsStream(DB_FILE);
//		System.out.println("Working Directory = "+ System.getProperty("user.dir"));
//		//BufferedReader br = new BufferedReader(new FileReader(new File(filePath)));
//
//		//BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
//		BufferedReader br = new BufferedReader(new InputStreamReader(is));
//		
//		String line;
//
//		while ((line = br.readLine()) != null) {
//			sb.append(line.trim());
//		}

	}
	
	
	/**
	 * simply append
	 * 
	 */
	@Override
	public void insertUser(String userId) {
		
		insertUserList(Arrays.asList(userId));
	}
	
	
	
	public void insertUserList(List<String> userIdList) {
		
		BufferedWriter bw = null;
		try{
			URL url = UserDAOFileImpl.class.getResource(DB_FILE);
			log.debug(url.getPath());
			FileWriter fw = new FileWriter(url.getFile(), true);
			bw = new BufferedWriter(fw);
			log.debug("Writing to Database....");
			
			for (String user : userIdList) {
				bw.write(user);
				bw.newLine();
			}
			
			log.debug("Successful Writing to Database.");
		}catch(Exception ex){
			log.error(ex);
		}finally{
			try {
				if(bw != null){
					bw.close();
				}
			} catch (IOException e) {
				log.debug(e);
			}
		}
		
	}

	/**
	 * read, remove the entry, delete all records, and re-enter them
	 */
	@Override
	public void deleteUser(String userId) {
		
		
		//Read the user list first
		Map<String,String> users = readFile();
		//Remove from list of users
		users.remove(userId);
		
		//
		PrintWriter pw =  null;
		URL url = UserDAOFileImpl.class.getResource(DB_FILE);
		try {
			
			File file = new File(url.getPath());
			File tempFile = new File(file.getAbsolutePath()+ ".txt");
			pw = new PrintWriter(new FileWriter(tempFile));
			 
    		 for(Map.Entry<String, String> entry: users.entrySet()){
				 pw.println(entry.getValue());
				 pw.flush();
			 }
    		 
    		//Delete the original file
    		  if (!file.delete()) {
    		    System.out.println("Could not delete file");
    		    return;
    		  }
    		  
    		//Rename the new file to the filename the original file had.
    		  if (!tempFile.renameTo(file)){
    		    System.out.println("Could not rename file");
    		  }


		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			pw.close();
		}
	}

	
	public Map readFile(){
		
		Map<String,String> users = new HashMap<String, String>();
		BufferedReader br = null;
		try{
			URL url = UserDAOFileImpl.class.getResource(DB_FILE);
			log.debug(url.getPath());
			FileInputStream fstream = new FileInputStream(url.getPath());
			
		    DataInputStream in = new DataInputStream(fstream);
			br = new BufferedReader(new InputStreamReader(in));
			log.debug("Reading from Database....");
			
			String strLine;
			
			while ((strLine = br.readLine()) != null)   {
				  users.put(strLine, strLine);
				  System.out.println (strLine);
			}
			
			log.debug("Successful Reading of Database.");
		}catch(Exception ex){
			log.error(ex);
		}finally{
			try {
				if(br != null){
					br.close();
				}
			} catch (IOException e) {
				log.debug(e);
			}
		}
		
		return users;
	}
	
}
