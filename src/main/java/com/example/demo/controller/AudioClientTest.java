package com.example.demo.controller;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.Audio;

public class AudioClientTest {
    private final int numClients;
    private final int numGetRequests;
    private final int numPostRequests;

    @Autowired
    private RestTemplate restTemplate;
    
    public AudioClientTest(int numClients, int numGetRequests, int numPostRequests) {
        this.numClients = numClients;
        this.numGetRequests = numGetRequests;
        this.numPostRequests = numPostRequests;
    }

    public void runTest() {
        ExecutorService executor = Executors.newFixedThreadPool(numClients);

        for (int i = 0; i < numClients; i++) {
        	System.out.println("Client: " + (numClients+1));
            Runnable client = new AudioClient(numGetRequests, numPostRequests);
            executor.execute(client);
        }

        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class AudioClient implements Runnable {
        private final int numGetRequests;
        private final int numPostRequests;

        public AudioClient(int numGetRequests, int numPostRequests) {
            this.numGetRequests = numGetRequests;
            this.numPostRequests = numPostRequests;
        }

        @Override
        public void run() {
        	HttpClient client = HttpClientBuilder.create().build();
        	long start = System.currentTimeMillis();
            for (int i = 1; i < numGetRequests+1; i++) {
            	if (i%2==0) {
            			HttpGet request = new HttpGet("http://168.138.70.17:8080/api/audio");
            			HttpResponse response;
            			try {
            				response = client.execute(request);
            				HttpEntity entity = response.getEntity();
            				String responseString = EntityUtils.toString(entity, "UTF-8");
            				System.out.println("All" + responseString);
            				//System.out.println("All " + response.getStatusLine().getStatusCode());
            			} catch (ClientProtocolException e) {
            				// TODO Auto-generated catch block
            				e.printStackTrace();
            			} catch (IOException e) {
            				// TODO Auto-generated catch block
            				e.printStackTrace();
            			}
            	}
            	else {
            		HttpGet request = new HttpGet("http://168.138.70.17::8080/api/audio/Selena");
                    HttpResponse response;
        			try {
        				response = client.execute(request);
        				HttpEntity entity = response.getEntity();
        				String responseString = EntityUtils.toString(entity, "UTF-8");
        				System.out.println("Specifics:" + responseString);
    		
        			} catch (ClientProtocolException e) {
        				// TODO Auto-generated catch block
        				e.printStackTrace();
        			} catch (IOException e) {
        				// TODO Auto-generated catch block
        				e.printStackTrace();
        			}
            		
            	}
            }	
            long end = System.currentTimeMillis();
            for (int i = 0; i < numPostRequests; i++) {
                 HttpPost post = new HttpPost("http://168.138.70.17::8080/api/audio");
                 post.setHeader("Content-type", "application/json");
                 StringEntity entity;
				try {
					entity = new StringEntity("{\"year\":1111,\"albumTitle\":\"Heave\",\"artistName\":\"Billie\",\"numberOfCopiesSold\":200,\"numberOfReviews\":12,\"trackTitle\":\"bad guy\",\"trackNumber\":31}");
					post.setEntity(entity);
					HttpResponse response = client.execute(post);
	                //System.out.println("Posted : " + response.getStatusLine().getStatusCode());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}          
            }
            end = System.currentTimeMillis();
            System.out.println("Total time: " + (end -start)+ " ms for "+ numGetRequests + " requests (GET) and " +  numPostRequests +" requests (POST). ");
     	      
        }
    }
    public static void main(String[] args) {
    	long total_start = System.currentTimeMillis();
    	AudioClientTest test = new AudioClientTest(100, 10, 1);
    	test.runTest();
    	long total_end = System.currentTimeMillis();
    	System.out.println("Total Time in ms: " + (total_end - total_start));
	}
}
