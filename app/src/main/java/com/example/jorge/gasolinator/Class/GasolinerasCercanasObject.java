package com.example.jorge.gasolinator.Class;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jorge on 4/09/17.
 */

public class GasolinerasCercanasObject {

    private List<Object> htmlAttributions = new ArrayList<Object>();
    private String nextPageToken;
    private ArrayList<Result> results;

    public GasolinerasCercanasObject() {
    }

    public List<Object> getHtmlAttributions() {
        return htmlAttributions;
    }

    public void setHtmlAttributions(List<Object> htmlAttributions) {
        this.htmlAttributions = htmlAttributions;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public ArrayList<Result> getResults() {
        return results;
    }

    public void setResults(ArrayList<Result> results) {
        this.results = results;
    }
}

