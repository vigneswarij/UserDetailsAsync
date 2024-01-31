package com.demo.customer.data;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.demo.customer.dto.UserEmailDetails;
import com.demo.customer.dto.Userdetails;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataLoad {
	
	public static List<Userdetails> userDetails = getUserName();
	public static List<UserEmailDetails> emaildet = getEmailDetail();
	
	
	
	
	 public static List<Userdetails> getUserName()
	 {
		 ObjectMapper mapper = new ObjectMapper();
		 try
		 {
			 return mapper.readValue(new File("UserNameDetail.json"), new TypeReference<List<Userdetails>>() {			 
			 });
		 } catch(Exception e)
		 {
			 e.printStackTrace();
		 }
	  return null; 
	 }
	 
	 public static List<UserEmailDetails> getEmailDetail()
	 {
		 ObjectMapper mapper = new ObjectMapper();
		 try
		 {
			 return mapper.readValue(new File("UserEmaildetail.json"), new TypeReference<List<UserEmailDetails>>() {			 
			 });
		 } catch(Exception e)
		 {
			 e.printStackTrace();
		 }
	  return null; 
	 }
	 

}
