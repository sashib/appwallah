package com.appwallah.filmlocations;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by sbommakanty on 7/8/17.
 */

public class FilmLocation {

    @SerializedName("actor_1")
    public String actor1;

    @SerializedName("actor_2")
    public String actor2;

    @SerializedName("actor_3")
    public String actor3;

    public String director;
    public String locations;
    public ArrayList<String> allLocations;

    @SerializedName("production_company")
    public String productionCompany;

    @SerializedName("release_year")
    public String releaseYear;

    public String writer;
    public String title;

    @SerializedName("fun_facts")
    public String funFacts;

    public String distributor;

    public String getActor1() {
        return actor1;
    }

    public void setActor1(String actor1) {
        this.actor1 = actor1;
    }

    public String getActor2() {
        return actor2;
    }

    public void setActor2(String actor2) {
        this.actor2 = actor2;
    }

    public String getActor3() {
        return actor3;
    }

    public void setActor3(String actor3) {
        this.actor3 = actor3;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getLocations() {
        return locations;
    }

    public void setLocations(String locations) {
        this.locations = locations;
    }

    public String getProductionCompany() {
        return productionCompany;
    }

    public void setProductionCompany(String productionCompany) {
        this.productionCompany = productionCompany;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getAllLocations() {
        return allLocations;
    }

    public void setAllLocations(ArrayList<String> allLocations) {
        this.allLocations = allLocations;
    }

    public String getFunFacts() {
        return funFacts;
    }

    public void setFunFacts(String funFacts) {
        this.funFacts = funFacts;
    }

    public String getDistributor() {
        return distributor;
    }

    public void setDistributor(String distributor) {
        this.distributor = distributor;
    }

    public String getCast() {
        String cast  = "";
        cast = getActor1() == null ? cast : cast + getActor1();
        cast = getActor2() == null ? cast : cast + ", " + getActor2();
        cast = getActor3() == null ? cast : cast + ", " + getActor3();

        return cast;
    }


}
