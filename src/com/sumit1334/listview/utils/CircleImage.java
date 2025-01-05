package com.sumit1334.listview.utils;

import android.view.View;
import com.google.appinventor.components.runtime.AndroidViewComponent;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.ComponentContainer;

public class CircleImage extends AndroidViewComponent implements Component {
    private final CircleImageView circleImageView;
    private String picture = "";

    public CircleImage(ComponentContainer container) {
        super(container);
        this.circleImageView = new CircleImageView(container.$context());
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(final String picture) {
        this.picture = picture;
    }

    public void setBorderColor(int color) {
        circleImageView.setBorderColor(color);
    }

    public void setBorderWidth(int width) {
        circleImageView.setBorderWidth(width);
    }

    public void setRadius(int l, int r, int bl, int br) {
        circleImageView.setCornerRadius(l, r, bl, br);
    }

    @Override
    public View getView() {
        return circleImageView;
    }
}
