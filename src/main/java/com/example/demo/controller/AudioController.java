package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Audio;

@RestController
@RequestMapping("/api")
public class AudioController {
	
	// ConcurrentHashMap is done to make the data thread safe which allows the concurrent access by multiple threads.

	private ConcurrentHashMap<String, Audio> audioMap = addToMap();
	
	public ConcurrentHashMap<String, Audio> addToMap() {
		System.out.println("Here");
		ConcurrentHashMap<String, Audio> audioMap = new ConcurrentHashMap<String, Audio>();
		Audio audio1 = new Audio();
		audio1.setYear(2000);
		audio1.setAlbumTitle("Red");
		audio1.setArtistName("Taylor");
		audio1.setNumberOfCopiesSold(1000);
		audio1.setNumberOfReviews(122);
		audio1.setTrackTitle("trouble");
		audio1.setTrackNumber(3);
		Audio audio2 = new Audio();
		audio2.setYear(2001);
		audio2.setAlbumTitle("1989");
		audio2.setArtistName("Selena");
		audio2.setNumberOfCopiesSold(100);
		audio2.setNumberOfReviews(1222);
		audio2.setTrackTitle("Blank Space");
		audio2.setTrackNumber(32);
		audioMap.put(audio1.getArtistName(), audio1);
		audioMap.put(audio2.getArtistName(), audio2);		
		System.out.println(audioMap);
		return audioMap;		
		
	}
	
	private static AtomicLong totalCopiesSold = new AtomicLong(0);
	private static final ExecutorService executorService = Executors.newFixedThreadPool(10);
	
	 @PostMapping("/audio")
	 @ResponseBody
	  public ResponseEntity<String> createAudio(@RequestBody Audio audio) {
	    audioMap.put(audio.getArtistName(), audio);
	    totalCopiesSold.addAndGet(audio.getNumberOfCopiesSold());
	    return ResponseEntity.ok("Audio item successfully stored in the database");
	    
	  }
	
	@GetMapping("/audio")
    public ResponseEntity<List<Audio>> getAllAudio() {
		
        return new ResponseEntity<>(new ArrayList<>(audioMap.values()), HttpStatus.OK);
    }
	
	@GetMapping("/audio/{artistName}")
    public ResponseEntity<Object> getAudioByArtistName(@PathVariable("artistName") String artistName) {
        Audio audio = audioMap.get(artistName);
        if (audio == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(audio, HttpStatus.OK);
    }
    public static long getTotalCopiesSold() {
        return totalCopiesSold.get();
    }

    public static int getActiveThreadCount() {
        return ((ThreadPoolExecutor) executorService).getActiveCount();
    }
}
