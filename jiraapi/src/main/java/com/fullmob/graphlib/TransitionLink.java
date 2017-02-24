package com.fullmob.graphlib;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransitionLink {
    @SerializedName("from")
    @Expose
    public String from;
    @SerializedName("via")
    @Expose
    public String via;
    @SerializedName("to")
    @Expose
    public String to;

    public TransitionLink(String from, String via, String to) {
        this.from = from;
        this.via = via;
        this.to = to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TransitionLink that = (TransitionLink) o;

        return this.from.equals(that.from)
            && this.via.equals(that.via)
            && this.to.equals(that.to);
    }

    @Override
    public int hashCode() {
        return (from + to + via).hashCode();
    }
}