package com.sumit1334.listview.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatCheckBox;
import com.google.appinventor.components.runtime.ComponentContainer;

public class MyLayout extends LinearLayout implements View.OnClickListener, View.OnLongClickListener {

    public final LinearLayout layout;
    public final CircleImage image;
    public final TextView title;
    public final TextView subTitle;
    public final TextView secondary;
    public final AppCompatCheckBox checkBox;
    public final CircleImage extraImage;
    private ClickListener listener;

    public MyLayout(Context context, ComponentContainer container) {
        super(context);
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setOrientation(VERTICAL);
        layout = new LinearLayout(context);
        addView(layout, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        layout.setOrientation(HORIZONTAL);

        // image View

        image = new CircleImage(container);
        LayoutParams imageParam = new LayoutParams(50, 50);
        imageParam.setMargins(20, 5, 20, 5);
        image.getView().setLayoutParams(imageParam);
        image.Width(60);
        image.Height(60);
        layout.addView(image.getView());

        // title and subtitle container vertical

        LinearLayout titleContainer = new LinearLayout(context);
        LayoutParams titleParam = new LinearLayout.LayoutParams(
                0, // width
                ViewGroup.LayoutParams.WRAP_CONTENT, // height
                1
        );
        titleContainer.setGravity(Gravity.LEFT);
        titleContainer.setLayoutParams(titleParam);
        titleContainer.setOrientation(VERTICAL);

        // title and subtitle text view

        title = new TextView(context);
        subTitle = new TextView(context);
        titleContainer.addView(title);
        titleContainer.addView(subTitle);

        LayoutParams params1 = (LayoutParams) title.getLayoutParams();
        params1.leftMargin = 10;
        title.setLayoutParams(params1);

        LayoutParams params2 = (LayoutParams) subTitle.getLayoutParams();
        params2.leftMargin = 10;
        params2.topMargin = 5;
        subTitle.setLayoutParams(params2);

        layout.addView(titleContainer);

        // third extra layout for containing sec, checkbox, image

        LinearLayout extraContainer = new LinearLayout(context);
        LayoutParams extraContainerParams = new LayoutParams(-2, -2);
        extraContainer.setLayoutParams(extraContainerParams);
        extraContainer.setGravity(Gravity.CENTER);
        extraContainer.setOrientation(VERTICAL);
        layout.addView(extraContainer);

        // icon container layout for extra image

        LinearLayout iconContainer = new LinearLayout(context);
        iconContainer.setHorizontalGravity(5);
        LayoutParams iconParams = new LayoutParams(-2, -2);
        iconContainer.setLayoutParams(iconParams);

        // secondary text view

        LinearLayout.LayoutParams secondaryTextViewParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        secondary = new TextView(context);
        secondary.setMaxLines(1);
        secondary.setLayoutParams(secondaryTextViewParams);
        extraContainer.addView(secondary);

        // checkbox

        checkBox = new AppCompatCheckBox(context);
        LinearLayout.LayoutParams checkBoxParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        checkBox.setLayoutParams(checkBoxParams);
        checkBox.setText("");
        iconContainer.addView(checkBox);

        // extra icon

        extraImage = new CircleImage(container);
        iconContainer.addView(extraImage.getView());
        extraContainer.addView(iconContainer);
        extraImage.getView().setId(3);
        extraImage.getView().setOnClickListener(this);
        extraImage.Visible(false);

        // click listeners

        layout.setOnClickListener(this);
        layout.setOnLongClickListener(this);
        layout.setId(0);
        checkBox.setId(1);
        checkBox.setOnClickListener(this);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                listener.checkBoxChecked(b);
            }
        });
        image.getView().setId(2);
        image.getView().setOnClickListener(this);
        image.getView().setOnLongClickListener(this);
    }

    public void setClickListener(ClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        final int id = view.getId();
        switch (id) {
            case 0:
                listener.clicked();
                break;
            case 1:
                listener.checkboxClicked();
                break;
            case 2:
                listener.iconClicked();
                break;
            case 3:
                listener.extraImageClicked();
                break;
        }
    }

    @Override
    public boolean onLongClick(View view) {
        final int id = view.getId();
        switch (id) {
            case 0:
                listener.longClicked();
                break;
            case 2:
                listener.iconLongClicked();
                break;
        }
        return true;
    }

    public interface ClickListener {
        void clicked();

        void longClicked();

        void iconClicked();

        void iconLongClicked();

        void extraImageClicked();

        void checkboxClicked();

        void checkBoxChecked(boolean checked);
    }
}
