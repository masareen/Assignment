package com.example.demo.model;

import java.util.HashMap;
import java.util.Map;

public class Audio {
  private String artistName;
  private String trackTitle;
  private String albumTitle;
  private int trackNumber;
  private int year;
  private int numberOfReviews;
  private int numberOfCopiesSold;


  public Audio() {}

  public Audio(String artistName, String trackTitle, String albumTitle, int trackNumber, int year, int numberOfReviews, int numberOfCopiesSold) {
    this.artistName = artistName;
    this.trackTitle = trackTitle;
    this.albumTitle = albumTitle;
    this.trackNumber = trackNumber;
    this.year = year;
    this.numberOfReviews = numberOfReviews;
    this.numberOfCopiesSold = numberOfCopiesSold;

  }
  

  public String getArtistName() {
    return artistName;
  }

  public void setArtistName(String artistName) {
    this.artistName = artistName;
  }

  public String getTrackTitle() {
    return trackTitle;
  }

  public void setTrackTitle(String trackTitle) {
    this.trackTitle = trackTitle;
  }

  public String getAlbumTitle() {
    return albumTitle;
  }

  public void setAlbumTitle(String albumTitle) {
    this.albumTitle = albumTitle;
  }

  public int getTrackNumber() {
    return trackNumber;
  }

  public void setTrackNumber(int trackNumber) {
    this.trackNumber = trackNumber;
  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public int getNumberOfReviews() {
    return numberOfReviews;
  }

  public void setNumberOfReviews(int numberOfReviews) {
    this.numberOfReviews = numberOfReviews;
  }

  public int getNumberOfCopiesSold() {
    return numberOfCopiesSold;
  }

  public void setNumberOfCopiesSold(int numberOfCopiesSold) {
    this.numberOfCopiesSold = numberOfCopiesSold;
  }
}

