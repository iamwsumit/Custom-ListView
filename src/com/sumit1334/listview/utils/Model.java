package com.sumit1334.listview.utils;

import com.google.appinventor.components.runtime.util.YailDictionary;

public class Model {

//  This model class holds all the item's data

    private String title;
    private String subTitle;
    private String image;
    private String secondary;
    private boolean checkbox = false;
    private int bgColor = 0;
    private String[] paddings = new String[]{"0", "5", "0", "5"};
    private String[] margins = new String[]{"5", "0", "5", "0"};
    private int checkboxBgColor;
    private int checkboxColor;
    private boolean checkboxVisible = false;
    private int[] titles = new int[]{};
    private int[] subtitles = new int[]{};
    private int[] secondaryTexts = new int[]{};
    private int dividerHeight;
    private int dividerColor;
    private boolean dividerVisibility;
    private int leftMargin;
    private int rightMargin;
    private int[] imageStroke = new int[]{};
    private Object[] decorators = new Object[]{};
    private boolean[] swiping = new boolean[]{};
    private boolean dragging;
    private int[] circleImages = new int[]{0, 0, 0, 0};
    private Object[] extraImageProperties;

    public Model(String title, String subTitle, String image, String secondary) {
        this.title = title;
        this.secondary = secondary;
        this.subTitle = subTitle;
        this.image = image;
    }

    public Object[] getExtraImageProperties() {
        return extraImageProperties;
    }

    public void setExtraImageProperties(Object[] data) {
        extraImageProperties = data;
    }

    public YailDictionary getInfo() {
        YailDictionary data = new YailDictionary();
        data.put("Image", image);
        data.put("Title", title);
        data.put("Subtitle", subTitle);
        data.put("Secondary Text", secondary);
        data.put("Checkbox Checked", checkbox);
        data.put("Background Color", bgColor);
        data.put("Checkbox Background Color", checkboxBgColor);
        data.put("Checkbox Color", checkboxColor);
        data.put("Checkbox Visible", checkboxVisible);
        data.put("Paddings", String.join(",", paddings));
        data.put("Margins", String.join(",", margins));
        data.put("Title Size", titles[0]);
        data.put("Title Color", titles[1]);
        data.put("Subtitle Size", subtitles[0]);
        data.put("Subtitle Color", subtitles[1]);
        data.put("Secondary Text Size", secondaryTexts[0]);
        data.put("Secondary Text Color", secondaryTexts[1]);
        data.put("Divider Height", dividerHeight);
        data.put("Divider Color", dividerColor);
        data.put("Divider Visibility", dividerVisibility);
        data.put("Divider Left Margin", leftMargin);
        data.put("Divider Right Margin", rightMargin);
        data.put("Image Stroke Width", imageStroke[0]);
        data.put("Image Stroke Color", imageStroke[1]);
        data.put("Image Size", imageStroke[2]);
        data.put("Left Swiping Enabled", swiping[0]);
        data.put("Right Swiping Enabled", swiping[1]);
        data.put("Dragging Enabled", dragging);
        data.put("Image Top Left Radius", circleImages[0]);
        data.put("Image Top Right Radius", circleImages[1]);
        data.put("Image Bottom Left Radius", circleImages[2]);
        data.put("Image Bottom Right Radius", circleImages[3]);
        return data;
    }

    public int[] getCircleImages() {
        return circleImages;
    }

    public void setCircleImages(int[] circleImages) {
        this.circleImages = circleImages;
    }

    public boolean isDragging() {
        return dragging;
    }

    public void setDragging(boolean dragging) {
        this.dragging = dragging;
    }

    public boolean[] getSwiping() {
        return swiping;
    }

    public void setSwiping(boolean[] swiping) {
        this.swiping = swiping;
    }

    public Object[] getDecorators() {
        return decorators;
    }

    public void setDecorators(Object[] objects) {
        this.decorators = objects;
    }

    public int[] getImageStroke() {
        return imageStroke;
    }

    public void setImageStroke(int[] image) {
        this.imageStroke = image;
    }

    public int getLeftMargin() {
        return leftMargin;
    }

    public void setLeftMargin(int margin) {
        this.leftMargin = margin;
    }

    public int getRightMargin() {
        return rightMargin;
    }

    public void setRightMargin(int margin) {
        this.rightMargin = margin;
    }

    public void setDividerVisibility(boolean visibility) {
        this.dividerVisibility = visibility;
    }

    public boolean isDividerVisible() {
        return dividerVisibility;
    }

    public int getDividerHeight() {
        return dividerHeight;
    }

    public void setDividerHeight(int height) {
        this.dividerHeight = height;
    }

    public int getDividerColor() {
        return dividerColor;
    }

    public void setDividerColor(int color) {
        dividerColor = color;
    }

    public int[] getTitles() {
        return titles;
    }

    public void setTitles(int[] title) {
        this.titles = title;
    }

    public int[] getSubtitles() {
        return subtitles;
    }

    public void setSubtitles(int[] title) {
        this.subtitles = title;
    }

    public int[] getSecondaryTexts() {
        return secondaryTexts;
    }

    public void setSecondaryTexts(int[] title) {
        this.secondaryTexts = title;
    }

    public void setCheckbox(int bg, int color, boolean visible, boolean checked) {
        checkboxBgColor = bg;
        checkboxColor = color;
        checkboxVisible = visible;
        setCheckbox(checked);
    }

    public boolean isCheckboxVisible() {
        return checkboxVisible;
    }

    public int getCheckboxBgColor() {
        return checkboxBgColor;
    }

    public int getCheckboxColor() {
        return checkboxColor;
    }

    public String[] getPaddings() {
        return paddings;
    }

    public void setPaddings(String[] paddings) {
        this.paddings = paddings;
    }

    public String[] getMargins() {
        return margins;
    }

    public void setMargins(String[] paddings) {
        this.margins = paddings;
    }

    public int getBgColor() {
        return bgColor;
    }

    public void setBgColor(int color) {
        bgColor = color;
    }

    public void setCheckbox(boolean checkbox) {
        this.checkbox = checkbox;
    }

    public boolean isChecked() {
        return checkbox;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subTitle;
    }

    public void setSubtitle(String title) {
        this.subTitle = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String title) {
        this.image = title;
    }

    public String getSecondary() {
        return secondary;
    }

    public void setSecondary(String title) {
        this.secondary = title;
    }

    public boolean isExtraLayoutVisible(){
        return !(isCheckboxVisible() && secondary.isEmpty());
    }
}
