package com.example.jorge.gasolinator.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jorge on 4/09/17.
 */

public class OpeningHours {

    private Boolean open_now;

    private List<Object> weekday_text = new ArrayList<Object>();

    public OpeningHours() {
    }

    public Boolean getOpen_now() {
        return open_now;
    }

    public void setOpen_now(Boolean open_now) {
        this.open_now = open_now;
    }

    public List<Object> getWeekday_text() {
        return weekday_text;
    }

    public void setWeekday_text(List<Object> weekday_text) {
        this.weekday_text = weekday_text;
    }
}
