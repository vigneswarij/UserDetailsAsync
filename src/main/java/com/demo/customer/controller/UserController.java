package com.demo.customer.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.demo.customer.data.DataLoad;
import com.demo.customer.dto.AllUserDetail;
import com.demo.customer.dto.UserEmailDetails;
import com.demo.customer.dto.Userdetails;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class UserController {
	
	 @Autowired
	 RestTemplate restTemplate;
	 
   @GetMapping("/User/getDetails")
   public List<AllUserDetail> getDetails() throws InterruptedException, ExecutionException
   {
	   List<AllUserDetail> users = new ArrayList<>();
	   List<Userdetails> detail = new ArrayList<>();
	   List<UserEmailDetails> detailNew = new ArrayList<>();
	   
	   Executor executor = Executors.newFixedThreadPool(5);
		  HttpHeaders headers = new HttpHeaders();
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	      HttpEntity<Userdetails> entity = new HttpEntity<Userdetails>(headers);
	     	CompletableFuture<List<Userdetails>> nameList = CompletableFuture.supplyAsync(() -> {
			
			for(int i=1;i<=100;i++)
			{
				Userdetails userDet = new Userdetails();
				System.out.println("getName ="+ Thread.currentThread().getName());
				String userName = restTemplate.exchange("http://localhost:8080/User/getDetails/"+i, HttpMethod.GET, entity, String.class).getBody();
				userDet.setUserid(i);
				userDet.setUsername(userName);
				detail.add(userDet);
			}return detail;},executor);
	     	
	     	
	     	CompletableFuture<List<UserEmailDetails>> emailList = CompletableFuture.supplyAsync(() -> {
			for(int j=1;j<=100;j++)
			{
				UserEmailDetails userDetEmail = new UserEmailDetails();
				System.out.println("getEmail ="+ Thread.currentThread().getName());
				String emailId = restTemplate.exchange("http://localhost:8080/User/getEmails/"+j, HttpMethod.GET, entity, String.class).getBody();
				userDetEmail.setUserid(j);
				userDetEmail.setEmailid(emailId);
				detailNew.add(userDetEmail);
			}
			return detailNew;
		}, executor);
	     	
	     	
	     for(int i=1;i<100;i++)
	     {
	    	 AllUserDetail det = new AllUserDetail();
	    		     	 
	    	 det.setUserid(i);
	    	 System.out.println("Name = "+ nameList.get().get(i).getUsername());
	    	 det.setUsername(nameList.get().get(i).getUsername());
	    	 det.setEmailid(emailList.get().get(i).getEmailid());
	    	 users.add(det);
	     } 
	     
	     	return users;
	     
	     	
   }
   	
	
	 
	@GetMapping("/User/getDetails/{id}")
	public String getName(@PathVariable(value = "id", required = true) int userId)
	{
		return DataLoad.getUserName().stream().filter(emp -> emp.getId()==userId).map(name -> name.getUsername()).collect(Collectors.toList()).get(0);
	}
	
	@GetMapping("/User/getEmails/{id}")
	public String getEmailId(@PathVariable(value = "id", required = true) int userId)
	{
		
	  return DataLoad.getEmailDetail().stream().filter(emp -> emp.getId()==userId).map(emailid -> emailid.getEmailid()).collect(Collectors.toList()).get(0);

	}

}
