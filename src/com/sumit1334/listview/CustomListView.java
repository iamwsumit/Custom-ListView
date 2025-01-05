package com.sumit1334.listview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.CompoundButtonCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutParams;

import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.AndroidViewComponent;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.ReplForm;
import com.google.appinventor.components.runtime.errors.YailRuntimeError;
import com.google.appinventor.components.runtime.util.MediaUtil;
import com.google.appinventor.components.runtime.util.TextViewUtil;
import com.google.appinventor.components.runtime.util.YailDictionary;
import com.google.appinventor.components.runtime.util.YailList;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.sumit1334.listview.utils.CircleImage;
import com.sumit1334.listview.utils.FlexibleDividerDecoration;
import com.sumit1334.listview.utils.HorizontalDividerItemDecoration;
import com.sumit1334.listview.utils.ListViewVerification;
import com.sumit1334.listview.utils.Model;
import com.sumit1334.listview.utils.MyLayout;
import com.sumit1334.listview.utils.RecyclerViewSwipeDecorator;

import me.everything.android.ui.overscroll.IOverScrollDecor;
import me.everything.android.ui.overscroll.IOverScrollUpdateListener;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

@DesignerComponent(
        version = 5,
        versionName = "2.0",
        description = "This extension allows you to create a Image, Title and subtitle list view with a lot of features.<br> Made By <a href=\"https://sumitkmr.com\" target =\"_blank\">Sumit</a> with Fast.",
        iconName = "icon.png"
)
public final class CustomListView extends AndroidNonvisibleComponent implements Component, ImageLoadingListener {

    private final String TAG = "CustomListView";
    private final Context context;
    private final ComponentContainer container;
    private final LinearLayout recyclerContainer;
    private final RecyclerView recyclerView;
    private final LinearLayoutManager manager;
    private final ListAdapter adapter;
    private final ArrayList<Model> models = new ArrayList<>();
    private final boolean isCompanion;
    private IOverScrollDecor iOverScrollDecor;
    private boolean isCreated = false;
    private int backgroundColor;
    private boolean divider = false;
    private int titleColor;
    private int subTitleColor;
    private String titleFont;
    private String subTitleFont;
    private int titleSize;
    private int subTitleSize;
    private boolean draggable = false;
    private boolean swipeRight = false;
    private boolean swipeLeft = false;
    private String leftIcon;
    private String rightIcon;
    private String leftText;
    private String rightText;
    private int leftBackgroundColor;
    private int rightBackgroundColor;
    private int iconStrokeColor;
    private int iconStrokeWidth;
    private int secondarySize;
    private int secondaryColor;
    private String secondaryFont;
    private String swipeCustomFont;
    private int swipeLeftColor;
    private int swipeRightColor;
    private int iconSize;
    private int dividerColor;
    private int dividerHeight;
    private boolean animation = true;
    private int dragDirs;
    private int swipeDirs;
    private boolean overscroll;
    private boolean html;
    private int touchColor;

    public CustomListView(ComponentContainer container) {
        super(container.$form());
        ListViewVerification.verifyListViewComponent();
        this.container = container;
        this.isCompanion = container.$form() instanceof ReplForm;
        this.context = container.$context();
        this.recyclerContainer = new LinearLayout(context);
        this.recyclerView = new RecyclerView(this.context);
        this.recyclerView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        this.adapter = new ListAdapter();
        this.recyclerView.setAdapter(this.adapter);
        this.manager = new LinearLayoutManager(context);
        this.manager.setOrientation(LinearLayoutManager.VERTICAL);
        this.recyclerView.setLayoutManager(this.manager);
        this.recyclerView.setNestedScrollingEnabled(false);
        this.recyclerContainer.addView(this.recyclerView);
        this.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(1))
                    ReachedBottom(GetScrollOffset());
                else if (GetScrollOffset() == 0)
                    ReachedTop(GetScrollOffset());
                else
                    Scrolled(GetScrollOffset());
            }
        });

        Log.i(TAG, "CustomListView: Calling All Properties with values");
        BackgroundColor(-1);
        TitleColor(Color.BLACK);
        SubTitleColor(Color.BLACK);
        TitleFontTypefaceImport("None");
        SubtitleFontTypefaceImport("None");
        SubTitleSize(12);
        TitleSize(16);
        LeftIcon("None");
        RightIcon("None");
        RightSwipedText("Right Swiped");
        LeftSwipedText("Left Swiped");
        LeftSwipeBackgroundColor(COLOR_GREEN);
        RightSwipeBackgroundColor(COLOR_RED);
        ImageBorderWidth(0);
        ImageBorderColor(-1);
        SecondaryFontTypefaceImport("None");
        SecondaryTextColor(COLOR_LTGRAY);
        SecondaryTextSize(12);
        SwipeLabelFontTypefaceImport("None");
        SwipeLeftLabelColor(-1);
        SwipeRightLabelColor(-1);
        ImageSize(60);
        DividerHeight(1);
        DividerColor(Color.LTGRAY);
        ItemAnimation(true);
        UpdateDivider();
        HTMLFormat(false);
        OverscrollEnabled(true);
        TouchColor(Component.COLOR_LTGRAY);
        Log.i(TAG, "Initialized");
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Set the ripple color of the list view item")
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_COLOR, defaultValue = "&HFFCCCCCC")
    public void TouchColor(int color) {
        touchColor = color;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Enables the HTML format to the list view item")
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN, defaultValue = "False")
    public void HTMLFormat(boolean html) {
        this.html = html;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "If enabled then the list view is allowed to over scroll")
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN, defaultValue = "True")
    public void OverscrollEnabled(boolean enabled) {
        overscroll = enabled;
        enableOverscroll();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "If enabled then list view item will be animated if some changes made into it, like modifying or removing the item")
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN, defaultValue = "True")
    public void ItemAnimation(boolean animation) {
        this.animation = animation;
        if (animation)
            this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        else
            this.recyclerView.setItemAnimator(null);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Set the height of divider that appears between each list view item")
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER, defaultValue = "1")
    public void DividerHeight(int dividerHeight) {
        this.dividerHeight = dividerHeight;
        UpdateDivider();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Set the divider color that appears between each list view item")
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_COLOR, defaultValue = DEFAULT_VALUE_COLOR_LTGRAY)
    public void DividerColor(int color) {
        this.dividerColor = color;
        UpdateDivider();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Set the image size of list view item")
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER, defaultValue = "60")
    public void ImageSize(int size) {
        this.iconSize = size;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Set the custom font typeface of the text that is shown while swiping the item")
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_ASSET)
    public void SwipeLabelFontTypefaceImport(String path) {
        this.swipeCustomFont = path;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Set the color of the text that is shown on left side while swiping the item")
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_COLOR, defaultValue = DEFAULT_VALUE_COLOR_WHITE)
    public void SwipeLeftLabelColor(int color) {
        this.swipeLeftColor = color;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Set the color of the text that is shown on right side while swiping the item")
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_COLOR, defaultValue = DEFAULT_VALUE_COLOR_WHITE)
    public void SwipeRightLabelColor(int color) {
        this.swipeRightColor = color;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Set the color of secondary label of the list view item")
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_COLOR, defaultValue = DEFAULT_VALUE_COLOR_LTGRAY)
    public void SecondaryTextColor(int color) {
        this.secondaryColor = color;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Set the custom font typeface of the secondary label of the list view item")
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_ASSET)
    public void SecondaryFontTypefaceImport(String typeface) {
        this.secondaryFont = typeface;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Set the font size of the secondary label of the list view item")
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER, defaultValue = "10")
    public void SecondaryTextSize(int size) {
        this.secondarySize = size;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Set the border color of the image of the list view item")
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_COLOR, defaultValue = DEFAULT_VALUE_COLOR_WHITE)
    public void ImageBorderColor(int color) {
        this.iconStrokeColor = color;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Set the border width or stroke width of the item's image")
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER, defaultValue = "0")
    public void ImageBorderWidth(int width) {
        this.iconStrokeWidth = width;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Set the icon of the left size image that will be shown while swiping the list view item")
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_ASSET)
    public void LeftIcon(String icon) {
        this.leftIcon = icon;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Set the icon of the right size image that will be shown while swiping the list view item")
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_ASSET)
    public void RightIcon(String icon) {
        this.rightIcon = icon;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Set the background color of the left side area that will be shown while swiping the list view item")
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_COLOR, defaultValue = DEFAULT_VALUE_COLOR_GREEN)
    public void LeftSwipeBackgroundColor(int color) {
        this.leftBackgroundColor = color;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Set the background color of the right side area that will be shown while swiping the list view item")
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_COLOR, defaultValue = DEFAULT_VALUE_COLOR_RED)
    public void RightSwipeBackgroundColor(int color) {
        this.rightBackgroundColor = color;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Text shown when the list view item is left swiped")
    @DesignerProperty(defaultValue = "Left Swiped")
    public void LeftSwipedText(String text) {
        this.leftText = text;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Text shown when the list view item is right swiped")
    @DesignerProperty(defaultValue = "Right Swiped")
    public void RightSwipedText(String text) {
        this.rightText = text;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "If enabled then the list view item is allowed to be dragged")
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN, defaultValue = "False")
    public void Draggable(boolean draggable) {
        this.draggable = draggable;
        this.updateRecyclerView();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "If enabled then the list view item is allowed to be swiped left")
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN, defaultValue = "False")
    public void LeftSwipeAble(boolean swipeLeft) {
        this.swipeLeft = swipeLeft;
        updateRecyclerView();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "If enabled then the list view item is allowed to be swiped right")
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN, defaultValue = "False")
    public void RightSwipeAble(boolean swipe) {
        this.swipeRight = swipe;
        updateRecyclerView();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Set the custom font typeface of the list view title")
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_ASSET)
    public void TitleFontTypefaceImport(String typeface) {
        this.titleFont = typeface;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Set the custom font typeface of the list view subtitle")
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_ASSET)
    public void SubtitleFontTypefaceImport(String typeface) {
        this.subTitleFont = typeface;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Set the font size of the list view title")
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER, defaultValue = "17")
    public void TitleSize(int size) {
        this.titleSize = size;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Set the font size of the list view subtitle")
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER, defaultValue = "12")
    public void SubTitleSize(int size) {
        this.subTitleSize = size;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "If set to true then the list view will be loaded from bottom to top like a chat view")
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN, defaultValue = "False")
    public void ReverseList(boolean reverse) {
        this.manager.setReverseLayout(reverse);
        this.manager.setStackFromEnd(reverse);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Set the text color of the list view title")
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_COLOR, defaultValue = DEFAULT_VALUE_COLOR_BLACK)
    public void TitleColor(int color) {
        this.titleColor = color;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Set the text color of the list view subtitle")
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_COLOR, defaultValue = DEFAULT_VALUE_COLOR_BLACK)
    public void SubTitleColor(int color) {
        this.subTitleColor = color;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Set the background color of the list view")
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_COLOR, defaultValue = DEFAULT_VALUE_COLOR_WHITE)
    public void BackgroundColor(int color) {
        this.backgroundColor = color;
        this.recyclerView.setBackgroundColor(color);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "If enabled then a divider will be shown between each item")
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN, defaultValue = "False")
    public void ShowDivider(boolean div) {
        this.divider = div;
        if (div) {
            removeAllItemDecoration();
            this.recyclerView.addItemDecoration(new DividerItemDecoration(context, 1));
        }
    }

    private void removeAllItemDecoration() {
        while (true) {
            try {
                this.recyclerView.removeItemDecorationAt(0);
            } catch (Exception e) {
                break;
            }
        }
    }

    @SimpleFunction(description = "Initializes the list view in given container. The container should not be a scroll arrangement, it might cause item scrolling problem due to nested components.")
    public void Initialize(AndroidViewComponent in) {
        if (!isCreated) {
            isCreated = true;
            ((LinearLayout) ((ViewGroup) in.getView()).getChildAt(0)).addView(this.recyclerContainer);
            if (animation)
                this.recyclerView.setItemAnimator(new DefaultItemAnimator());
            else
                this.recyclerView.setItemAnimator(null);
            this.ShowDivider(this.divider);
            this.DividerColor(this.dividerColor);
            this.DividerHeight(this.dividerHeight);
            this.LeftSwipeAble(this.swipeLeft);
            this.RightSwipeAble(this.swipeRight);
        } else
            Log.i(TAG, "CreateListView: List View already created");
    }

    @SimpleEvent(description = "This event raises when the list view item is clicked, return the position or index of the item clicked")
    public void Click(int index) {
        EventDispatcher.dispatchEvent(this, "Click", index);
    }

    @SimpleEvent(description = "This event raises when the list view item is long clicked, returns the position or index of the item clicked")
    public void LongClick(int index) {
        EventDispatcher.dispatchEvent(this, "LongClick", index);
    }

    @SimpleEvent(description = "This event raises when the checkbox of the list view item is clicked, returns the position or index of the that item")
    public void CheckboxClick(int index) {
        EventDispatcher.dispatchEvent(this, "CheckboxClick", index);
    }

    @SimpleEvent(description = "This event raises when the checkbox check state is changed, returns the position of that item and the checkbox current state")
    public void CheckboxChanged(int index, boolean checked) {
        EventDispatcher.dispatchEvent(this, "CheckboxChanged", index, checked);
    }

    @SimpleEvent(description = "This event raises when the extra image is clicked, return the index of the list item")
    public void ExtraImageClicked(int index) {
        EventDispatcher.dispatchEvent(this, "ExtraImageClicked", index);
    }

    @SimpleEvent(description = "This event raises when the list view image is clicked, returns the index of the list view item")
    public void ImageClicked(int index) {
        EventDispatcher.dispatchEvent(this, "ImageClicked", index);
    }

    @SimpleEvent(description = "This event raises when the list view image is long clicked, returns the index of the list view item")
    public void ImageLongClicked(int index) {
        EventDispatcher.dispatchEvent(this, "ImageLongClicked", index);
    }

    @SimpleEvent(description = "This event raises when the list view item is dragged from a position to a new position, return the oldPosition and the new position of the list item")
    public void ItemDragged(int fromPosition, int toPosition) {
        EventDispatcher.dispatchEvent(this, "ItemDragged", fromPosition, toPosition);
    }

    @SimpleEvent(description = "This event raises when the list view is scrolled, return the offset of the list view")
    public void Scrolled(int offset) {
        EventDispatcher.dispatchEvent(this, "Scrolled", offset);
    }

    @SimpleEvent(description = "This event raises when the list view is scrolled to the top")
    public void ReachedTop(int offset) {
        EventDispatcher.dispatchEvent(this, "ReachedTop", offset);
    }

    @SimpleEvent(description = "This event raises when the list view is scrolled to the bottom")
    public void ReachedBottom(int offset) {
        EventDispatcher.dispatchEvent(this, "ReachedBottom", offset);
    }

    @SimpleEvent(description = "This event raises when the list view item is swiped in the left direction, return the index or position of the item")
    public void LeftSwiped(int position) {
        EventDispatcher.dispatchEvent(this, "LeftSwiped", position);
    }

    @SimpleEvent(description = "This event raises when the list view is swiped in the right direction, returns the index of position of the item")
    public void RightSwiped(int position) {
        EventDispatcher.dispatchEvent(this, "RightSwiped", position);
    }

    @SimpleEvent(description = "This event raises when the list view is over scrolled, returns the offset. This event only triggers when the overscroll effect is enabled")
    public void OverScrolled(int offset) {
        EventDispatcher.dispatchEvent(this, "OverScrolled", offset);
    }

    @SimpleFunction(description = "Adds an item to the list view")
    public void AddItem(final String image, final String title, String subTitle, String secondaryText) {
        Model model = new Model(title, subTitle, image, secondaryText);
        this.models.add(model);
        this.initialiseModel(this.models.size());
        this.adapter.notifyItemInserted(this.models.size() - 1);
    }

    @SimpleFunction(description = "Adds multiple items in the list view at once, length of all list must be same and list must be non empty")
    public void AddItemFromList(final YailList images, final YailList titles, final YailList subTitles, final YailList secondaryTexts) {
        String[] t = titles.toStringArray();
        String[] i = images.toStringArray();
        String[] st = subTitles.toStringArray();
        String[] s = secondaryTexts.toStringArray();
        if (images.length() == titles.length() && !titles.isEmpty()) {
            for (int l = 0; l < t.length; l++) {
                AddItem(i[l], t[l], st.length >= 1 ? st[l] : "", s.length >= 1 ? s[l] : "");
            }
        } else
            throw new YailRuntimeError("List length are not same or are empty", "Custom List View");
    }

    @SimpleFunction(description = "Adds an item at given position in the list view")
    public void AddItemAt(int position, String image, String title, String subTitle, String secondaryText) {
        Model model = new Model(title, subTitle, image, secondaryText);
        this.models.set(position - 1, model);
        this.initialiseModel(position);
        this.adapter.notifyItemInserted(position - 1);
        Log.i(TAG, "AddItem: Item has added to the list view at " + position + " position");
    }

    @SimpleFunction(description = "Set/Update the multiple items at once from first position to the length of given lists.")
    public void Set(YailList images, YailList titles, YailList subtitles, YailList secondaryTexts) {
        if (images.length() == titles.length() && !titles.isEmpty()) {
            String[] title = titles.toStringArray();
            String[] subtitle = subtitles.toStringArray();
            String[] image = images.toStringArray();
            String[] secondary = secondaryTexts.toStringArray();
            for (int i = 0; i < title.length; i++) {
                Model model = this.models.get(i);
                model.setImage(image[i]);
                model.setSecondary(secondary.length >= 1 ? secondary[i] : "");
                model.setTitle(title[i]);
                model.setSubtitle(subtitle.length >= 1 ? subtitle[i] : "");
            }
        } else
            throw new YailRuntimeError("All list's length must be same", "List Length Error");
    }

    @SimpleFunction(description = "Refresh the whole list view")
    public void RefreshList() {
        this.adapter.notifyDataSetChanged();
    }

    @SimpleFunction(description = "Reset all the property of a particular list view item for given position")
    public void ResetItem(int position) {
        this.initialiseModel(position);
        this.adapter.notifyItemChanged(position - 1);
    }

    @SimpleFunction(description = "Set the background color of a particular list view item for given position")
    public void SetBackgroundColor(int position, int color) {
        this.getModel(position).setBgColor(color);
        this.adapter.notifyItemChanged(position - 1);
    }

    @SimpleFunction(description = "Set the size and the border properties of a particular item image for given position")
    public void SetImageProperties(int position, int size, int strokeColor, int strokeWidth) {
        this.getModel(position).setImageStroke(new int[]{strokeWidth, strokeColor, size});
        this.adapter.notifyItemChanged(position - 1);
    }

    @SimpleFunction(description = "Set the corner/border radius of a particular item image for given position")
    public void SetCornerRadius(int position, int topLeft, int topRight, int bottomLeft, int bottomRight) {
        this.getModel(position).setCircleImages(new int[]{topLeft, topRight, bottomLeft, bottomRight});
        this.UpdateTitle(position, GetTitle(position));
    }

    @SimpleFunction(description = "Set the title color and title font size of a particular item for given position")
    public void SetTitle(int position, int color, int textSize) {
        this.getModel(position).setTitles(new int[]{textSize, color});
        this.UpdateTitle(position, GetTitle(position));
    }

    @SimpleFunction(description = "Set the subtitle color and font size of a particular item for given position")
    public void SetSubTitle(int position, int color, int textSize) {
        this.getModel(position).setSubtitles(new int[]{textSize, color});
        this.UpdateSubTitle(position, GetSubTitle(position));
    }

    @SimpleFunction(description = "Set the color and font size of the secondary label for a particular item for given position")
    public void SetSecondaryText(int position, int color, int textSize) {
        this.getModel(position).setSecondaryTexts(new int[]{textSize, color});
        this.adapter.notifyItemChanged(position - 1);
    }

    @SimpleFunction(description = "Set all the properties of the extra image for a particular item for given position")
    public void SetExtraImage(int index, String picture, int strokeColor, int strokeWidth, int cornerRadius, int height, int width, boolean visible) {
        this.getModel(index).setExtraImageProperties(new Object[]{picture, height, width, strokeWidth, strokeColor, cornerRadius, visible});
        this.adapter.notifyItemChanged(index - 1);
    }

    @SimpleFunction(description = "Set the checkbox properties of a particular list view item for a given position")
    public void SetCheckbox(int position, int backgroundColor, int color, boolean checked, boolean visible) {
        this.getModel(position).setCheckbox(backgroundColor, color, visible, checked);
        this.adapter.notifyItemChanged(position - 1);
    }

    @SimpleFunction(description = "Set the divider properties of a particular item for given position")
    public void SetDivider(int position, int height, int color, int leftMargin, int rightMargin, boolean visibility) {
        final Model model = this.getModel(position);
        model.setDividerColor(color);
        model.setDividerHeight(height);
        model.setDividerVisibility(visibility);
        model.setLeftMargin(leftMargin);
        model.setRightMargin(rightMargin);
        this.adapter.notifyItemChanged(position - 1);
    }

    @SimpleFunction(description = "Set the margin of a particular list view item for given position, Margin String format :- Left, Top, Right, Bottom")
    public void SetMargin(int position, String margins) {
        if (margins.split(",").length == 4) {
            this.getModel(position).setMargins(margins.split(","));
            this.adapter.notifyItemChanged(position - 1);
        } else
            throw new IllegalArgumentException("Provide 4 values");
    }


    @SimpleFunction(description = "Set the paddings of a particular list view item for given position, Padding String format :- Left, Top, Right, Bottom")
    public void SetPadding(int position, String paddings) {
        if (paddings.split(",").length == 4) {
            this.getModel(position).setPaddings(paddings.split(","));
            this.adapter.notifyItemChanged(position - 1);
        } else
            throw new IllegalArgumentException("Provide 4 values");
    }

    @SimpleFunction(description = "Update the image for given position")
    public void UpdateImage(int position, String image) {
        this.getModel(position).setImage(image);
        this.adapter.notifyItemChanged(position - 1);
        Log.i(TAG, "UpdateImage: Image Updated at " + position + " position");
    }

    @SimpleFunction(description = "Update the title for given position")
    public void UpdateTitle(int position, String title) {
        this.getModel(position).setTitle(title);
        this.adapter.notifyItemChanged(position - 1);
        Log.i(TAG, "UpdateTitle: Title Updated at " + position + " position");
    }

    @SimpleFunction(description = "Update the subtitle for given position")
    public void UpdateSubTitle(int position, String subTitle) {
        this.getModel(position).setSubtitle(subTitle);
        this.adapter.notifyItemChanged(position - 1);
        Log.i(TAG, "UpdateSubTitle: Sub title updated at " + position + " position");
    }

    @SimpleFunction(description = "Update the secondary text for given position")
    public void UpdateSecondaryText(int position, String text) {
        this.getModel(position).setSecondary(text);
        this.adapter.notifyItemChanged(position - 1);
        Log.i(TAG, "UpdateSecondaryText: Secondary text update at " + position + " position");
    }

    @SimpleFunction(description = "Moves an item from an old position to a new position, could be used with swipe events")
    public void MoveItem(int fromPosition, int toPosition) {
        fromPosition--;
        toPosition--;
        Collections.swap(this.models, fromPosition, toPosition);
        this.adapter.notifyItemMoved(fromPosition, toPosition);
        Log.i(TAG, "MoveItem: Item moved from " + fromPosition + " to " + toPosition + " position");
    }

    @SimpleFunction(description = "Removes a list view item at given position")
    public void RemoveItem(int position) {
        position--;
        this.models.remove(position);
        this.adapter.notifyItemRemoved(position);
        Log.i(TAG, "RemoveItem: Item remove at " + position + " position");
    }

    @SimpleFunction(description = "Removes all the items from list view")
    public void ClearListView() {
        this.models.clear();
        this.adapter.notifyDataSetChanged();
        Log.i(TAG, "ClearListView: List view cleared");
    }

    @SimpleFunction(description = "Set a particular item draggable at given position")
    public void SetDraggable(int position, boolean draggable) {
        this.getModel(position).setDragging(draggable);
    }

    @SimpleFunction(description = "Set a particular item swipeable at given position")
    public void SetSwiping(int position, boolean leftSwipeable, boolean rightSwipeable) {
        this.getModel(position).setSwiping(new boolean[]{leftSwipeable, rightSwipeable});
    }

    @SimpleFunction(description = "Set the properties of the text that is shown during the swiping of a particular item at a given position")
    public void DecorateItem(int position, int leftColor, int rightColor, String leftIcon, String rightIcon, String leftText, String rightText) {
        this.getModel(position).setDecorators(new Object[]{leftColor, rightColor, leftIcon, rightIcon, leftText, rightText});
        this.updateRecyclerView();
    }

    @SimpleFunction(description = "Scroll to a given position, if smooth set to true then a smooth time taking scroll will happen otherwise a quick scroll will happen")
    public void Scroll(int position, boolean smooth) {
        if (position < 1 || position > this.models.size())
            return;
        this.recyclerView.post(new Runnable() {
            @Override
            public void run() {
                if (smooth)
                    CustomListView.this.recyclerView.smoothScrollToPosition(position - 1);
                else
                    CustomListView.this.recyclerView.scrollToPosition(position - 1);
            }
        });
    }

    @SimpleFunction(description = "Scrolls to the bottom of the list view")
    public void ScrollBottom(boolean smooth) {
        Scroll(models.size(), smooth);
    }

    @SimpleFunction(description = "Scrolls to the top of the list view")
    public void ScrollTop(boolean smooth) {
        Scroll(1, smooth);
    }

    @SimpleFunction(description = "Returns the current scroll offset of the list view")
    public int GetScrollOffset() {
        return recyclerView.computeVerticalScrollOffset();
    }

    @SimpleFunction(description = "Returns the image at given position")
    public String GetImage(int position) {
        return this.getModel(position).getImage();
    }

    @SimpleFunction(description = "Returns a list of all images")
    public YailList GetAllImages() {
        String[] images = new String[this.models.size()];
        for (Model model : this.models) {
            images[this.models.indexOf(model)] = model.getImage();
        }
        return YailList.makeList(images);
    }

    @SimpleFunction(description = "Returns the title at given position")
    public String GetTitle(int position) {
        return this.getModel(position).getTitle();
    }

    @SimpleFunction(description = "Returns a list of all titles")
    public YailList GetAllTitles() {
        String[] images = new String[this.models.size()];
        for (Model model : this.models) {
            images[this.models.indexOf(model)] = model.getTitle();
        }
        return YailList.makeList(images);
    }

    @SimpleFunction(description = "Returns the subtitle at given position")
    public String GetSubTitle(int position) {
        return this.getModel(position).getSubtitle();
    }

    @SimpleFunction(description = "Returns a list of all subtitles")
    public YailList GetAllSubTitles() {
        String[] images = new String[this.models.size()];
        for (Model model : this.models) {
            images[this.models.indexOf(model)] = model.getSubtitle();
        }
        return YailList.makeList(images);
    }

    @SimpleFunction(description = "Returns the secondary text at given position")
    public String GetSecondaryText(int position) {
        return this.getModel(position).getSecondary();
    }

    @SimpleFunction(description = "Returns a list of all secondary texts")
    public YailList GetAllSecondaryText() {
        String[] images = new String[this.models.size()];
        for (Model model : this.models) {
            images[this.models.indexOf(model)] = model.getSecondary();
        }
        return YailList.makeList(images);
    }

    @SimpleFunction(description = "Returns true if the checkbox at given position is checked")
    public boolean CheckboxChecked(int index) {
        return this.getModel(index).isChecked();
    }

    @SimpleFunction(description = "Returns the whole list view item properties in dictionary format")
    public YailDictionary GetItem(int position) {
        return this.getModel(position).getInfo();
    }

    private Model getModel(int position) {
        return this.models.get(position - 1);
    }

    private void initialiseModel(int position) {
        final Model model = this.models.get(position - 1);
        model.setCircleImages(new int[]{0, 0, 0, 0});
        model.setDragging(draggable);
        model.setSwiping(new boolean[]{swipeLeft, swipeRight});
        model.setDecorators(new Object[]{});
        model.setLeftMargin(5);
        model.setRightMargin(5);
        model.setDividerVisibility(divider);
        model.setDividerHeight(dividerHeight);
        model.setDividerColor(dividerColor);
        model.setTitles(new int[]{titleSize, titleColor});
        model.setSubtitles(new int[]{subTitleSize, subTitleColor});
        model.setSecondaryTexts(new int[]{secondarySize, secondaryColor});
        model.setImageStroke(new int[]{iconStrokeWidth, iconStrokeColor, iconSize});
        model.setCheckbox(0, 0, false, false);
        model.setPaddings(new String[]{"0", "5", "0", "5"});
        model.setMargins(new String[]{"5", "0", "5", "0"});
        model.setBgColor(backgroundColor);
        model.setExtraImageProperties(new Object[]{"", 40, 40, 0, 0, 0, false});
    }

    @SimpleEvent(description = "This event raises when an item is being swiped, returns the position of the item and the offset")
    public void Swiping(int index, float offset) {
        EventDispatcher.dispatchEvent(this, "Swiping", index, offset);
    }

    private void updateRecyclerView() {
        if (draggable || swipeLeft || swipeRight) {
            int drag = 0;
            if (draggable)
                drag = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END;
            int swipe = 0;
            if (swipeRight && swipeLeft)
                swipe = 4 | 8;
            else if (swipeLeft)
                swipe = 4;
            else if (swipeRight)
                swipe = 8;
            this.dragDirs = drag;
            this.swipeDirs = swipe;
            ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(drag, swipe) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                    ItemDragged(viewHolder.getAdapterPosition() + 1, viewHolder1.getAdapterPosition() + 1);
                    Log.i(TAG, "onMove: Item Dragged");
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                    Log.i(TAG, "onSwiped: Item Swiped");
                    switch (i) {
                        case ItemTouchHelper.LEFT:
                            LeftSwiped(viewHolder.getAdapterPosition() + 1);
                            break;
                        case ItemTouchHelper.RIGHT:
                            RightSwiped(viewHolder.getAdapterPosition() + 1);
                            break;
                    }
                }

                @Override
                public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                    int drag = dragDirs;
                    drag = CustomListView.this.getModel(viewHolder.getAdapterPosition() + 1).isDragging() ? 1 | 2 : 0;
                    int swipe = swipeDirs;
                    boolean left = CustomListView.this.getModel(viewHolder.getAdapterPosition() + 1).getSwiping()[0];
                    boolean right = CustomListView.this.getModel(viewHolder.getAdapterPosition() + 1).getSwiping()[1];
                    if (right && left)
                        swipe = 4 | 8;
                    else if (left)
                        swipe = 4;
                    else if (right)
                        swipe = 8;
                    else
                        swipe = 0;
                    return makeMovementFlags(drag, swipe);
                }

                @Override
                public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                    try {
                        Swiping(viewHolder.getAdapterPosition() + 1, dX);
                        if (models.get(viewHolder.getAdapterPosition()).getDecorators().length != 0) {
                            final Object[] data = CustomListView.this.getModel(viewHolder.getAdapterPosition() + 1).getDecorators();
                            int leftColor = (int) data[0];
                            int rightColor = (int) data[1];
                            String leftIcon = (String) data[2];
                            String rightIcon = (String) data[3];
                            String leftText = (String) data[4];
                            String rightText = (String) data[5];
                            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                                    .addSwipeLeftActionIcon(MediaUtil.getBitmapDrawable(form, leftIcon))
                                    .addSwipeLeftLabel(leftText)
                                    .addSwipeLeftBackgroundColor(leftColor)
                                    .addSwipeRightActionIcon(MediaUtil.getBitmapDrawable(form, rightIcon))
                                    .addSwipeRightLabel(rightText)
                                    .addSwipeRightBackgroundColor(rightColor)
                                    .setSwipeLeftLabelColor(swipeLeftColor)
                                    .setSwipeRightLabelColor(swipeRightColor)
                                    .setSwipeLeftLabelTypeface(getCustomFont(swipeCustomFont))
                                    .setSwipeRightLabelTypeface(getCustomFont(swipeCustomFont))
                                    .create().decorate();
                        } else {
                            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                                    .addSwipeLeftActionIcon(MediaUtil.getBitmapDrawable(form, leftIcon))
                                    .addSwipeLeftBackgroundColor(leftBackgroundColor)
                                    .addSwipeLeftLabel(leftText)
                                    .addSwipeRightActionIcon(MediaUtil.getBitmapDrawable(form, rightIcon))
                                    .addSwipeRightBackgroundColor(rightBackgroundColor)
                                    .addSwipeRightLabel(rightText)
                                    .setSwipeLeftLabelColor(swipeLeftColor)
                                    .setSwipeRightLabelColor(swipeRightColor)
                                    .setSwipeLeftLabelTypeface(getCustomFont(swipeCustomFont))
                                    .setSwipeRightLabelTypeface(getCustomFont(swipeCustomFont))
                                    .create().decorate();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }
            };
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
            itemTouchHelper.attachToRecyclerView(this.recyclerView);
        }
    }

    private Typeface getCustomFont(String font) {
        try {
            if (font.equalsIgnoreCase("none"))
                return null;
            else {
                if (isCompanion) {
                    String path = context.getExternalFilesDir(null).getPath() + "/assets/" + font;
                    return Typeface.createFromFile(new File(path));
                } else
                    return Typeface.createFromAsset(context.getAssets(), font);
            }
        } catch (Exception e) {
            Log.i(TAG, "getCustomFont: " + e.getMessage());
            return null;
        }
    }

    private void setCustomFont(final TextView textView, final String font) {
        try {
            textView.setTypeface(getCustomFont(font));
        } catch (Exception e) {
            Log.i(TAG, "setCustomFont: " + e.getMessage());
        }
    }

    private int px2dp(int dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }

    private int[] px2dp(String[] array) {
        int[] ints = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            ints[i] = px2dp(Integer.parseInt(array[i]));
        }
        return ints;
    }

    private void setRippleDrawable(View view) {
        try {
            if (touchColor == 0)
                view.setBackground(null);
            else if (Build.VERSION.SDK_INT >= 21) {
                final Drawable drawable = new RippleDrawable(ColorStateList.valueOf(touchColor), view.getBackground(), null);
                view.setBackground(drawable);
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void enableOverscroll() {
        if (this.overscroll) {
            iOverScrollDecor = OverScrollDecoratorHelper.setUpOverScroll(recyclerView, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);
            this.iOverScrollDecor.setOverScrollUpdateListener(new IOverScrollUpdateListener() {
                @Override
                public void onOverScrollUpdate(IOverScrollDecor iOverScrollDecor, int i, float v) {
                    OverScrolled((int) v);
                }
            });
        } else
            iOverScrollDecor.detach();
    }

    private void UpdateDivider() {
        if (this.divider) {
            removeAllItemDecoration();
            this.recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(context)
                    .colorProvider(adapter)
                    .visibilityProvider(adapter)
                    .sizeProvider(adapter)
                    .build());
            Log.i(TAG, "DividerColor: Divider color set to " + dividerColor);
        }
    }

    private ImageLoader loader;
    private final DisplayImageOptions options = new DisplayImageOptions.Builder()
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .build();

    private void loadImages(final CircleImage circleImage, final String picture) {
        if (circleImage.getPicture().equals(picture))
            return;
        circleImage.setPicture(picture);
        if (picture.startsWith("https://")) {
            try {
                if (loader == null) {
                    loader = ImageLoader.getInstance();
                    ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(context).build();
                    loader.init(configuration);
                }
                ImageView imageView = (ImageView) circleImage.getView();
                loader.displayImage(picture, imageView, options, this);
            } catch (Exception e) {
                Log.i(TAG, "Failed to load the image: " + e.getMessage());
            }
        } else {
            try {
                ((ImageView) circleImage.getView()).setImageDrawable(MediaUtil.getBitmapDrawable(form, picture));
            } catch (Exception e) {
                Log.i(TAG, "onBindViewHolder: Failed to set the image " + picture);
            }

        }
    }

    @Override
    public void onLoadingStarted(String s, View view) {

    }

    @Override
    public void onLoadingFailed(String s, View view, FailReason failReason) {

    }

    @Override
    public void onLoadingComplete(String s, View view, Bitmap bitmap) {

    }

    @Override
    public void onLoadingCancelled(String s, View view) {

    }

    public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> implements
            FlexibleDividerDecoration.ColorProvider,
            HorizontalDividerItemDecoration.MarginProvider,
            FlexibleDividerDecoration.VisibilityProvider,
            FlexibleDividerDecoration.SizeProvider {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ViewHolder(new MyLayout(context, container));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            viewHolder.bind(i);
        }

        @Override
        public int getItemCount() {
            return CustomListView.this.models.size();
        }

        @Override
        public boolean shouldHideDivider(int position, RecyclerView parent) {
            return !getModel(position + 1).isDividerVisible();
        }

        @Override
        public int dividerColor(int position, RecyclerView parent) {
            return getModel(position + 1).getDividerColor();
        }

        @Override
        public int dividerSize(int position, RecyclerView parent) {
            return px2dp(getModel(position + 1).getDividerHeight());
        }

        @Override
        public int dividerLeftMargin(int position, RecyclerView parent) {
            return px2dp(getModel(position + 1).getLeftMargin());
        }

        @Override
        public int dividerRightMargin(int position, RecyclerView parent) {
            return px2dp(getModel(position + 1).getRightMargin());
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements MyLayout.ClickListener {
            final MyLayout myLayout;

            public ViewHolder(MyLayout linearLayout) {
                super(linearLayout);
                this.myLayout = linearLayout;
                linearLayout.setClickListener(this);
            }

            private int getActualPosition() {
                return getAdapterPosition() + 1;
            }

            public void bind(int i) {
                final Model model = CustomListView.this.getModel(i + 1);
                try {
                    loadImages(myLayout.image, model.getImage());
                    int[] imageStroke = CustomListView.this.getModel(i + 1).getImageStroke();
                    myLayout.image.Height(imageStroke[2]);
                    myLayout.image.Width(imageStroke[2]);
                    int[] c = model.getCircleImages();
                    myLayout.image.setRadius(px2dp(c[0]), px2dp(c[1]), px2dp(c[2]), px2dp(c[3]));
                    int[] margins = px2dp(model.getMargins());
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -2);
                    params.setMargins(margins[0], margins[1], margins[2], margins[3]);
                    myLayout.layout.setLayoutParams(params);
                    int[] paddings = px2dp(model.getPaddings());
                    myLayout.layout.setPadding(paddings[0], paddings[1], paddings[2], paddings[3]);
                    myLayout.layout.setBackgroundColor(model.getBgColor() != 0 ? model.getBgColor() : backgroundColor);
                    CustomListView.this.setRippleDrawable(myLayout.layout);
                    myLayout.title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                    CustomListView.this.setCustomFont(myLayout.title, titleFont);
                    CustomListView.this.setCustomFont(myLayout.subTitle, subTitleFont);
                    CustomListView.this.setCustomFont(myLayout.secondary, secondaryFont);
                    myLayout.image.setBorderColor(imageStroke[1]);
                    myLayout.image.setBorderWidth(px2dp(imageStroke[0]));
                    myLayout.title.setTextColor(model.getTitles().length != 0 ? model.getTitles()[1] : titleColor);
                    myLayout.subTitle.setTextColor(model.getSubtitles().length != 0 ? model.getSubtitles()[1] : subTitleColor);
                    myLayout.secondary.setTextColor(model.getSecondaryTexts().length != 0 ? model.getSecondaryTexts()[1] : secondaryColor);
                    TextViewUtil.setFontSize(myLayout.title, model.getTitles().length != 0 ? model.getTitles()[0] : titleSize);
                    TextViewUtil.setFontSize(myLayout.subTitle, model.getSubtitles().length != 0 ? model.getSubtitles()[0] : subTitleSize);
                    TextViewUtil.setFontSize(myLayout.secondary, model.getSecondaryTexts().length != 0 ? model.getSecondaryTexts()[0] : secondarySize);
                    final CircleImage extraImage = myLayout.extraImage;
                    Object[] list = model.getExtraImageProperties();
                    String picture = (String) list[0];
                    int height = (int) list[1];
                    int width = (int) list[2];
                    int strokeWidth = (int) list[3];
                    int stroke = (int) list[4];
                    int radius = (int) list[5];
                    boolean visibility = (boolean) list[6];
                    if (visibility) {
                        extraImage.setBorderColor(stroke);
                        extraImage.setBorderWidth(strokeWidth);
                        extraImage.setRadius(radius, radius, radius, radius);
                        extraImage.Height(height);
                        extraImage.Width(width);
                        loadImages(extraImage, picture);
                        extraImage.Visible(true);
                    } else
                        extraImage.Visible(false);
                    if (model.isCheckboxVisible()) {
                        TextViewUtil.setBackgroundColor(myLayout.checkBox, model.getCheckboxBgColor());
                        CompoundButtonCompat.setButtonTintList(myLayout.checkBox, ColorStateList.valueOf(model.getCheckboxColor()));
                        myLayout.checkBox.setVisibility(View.VISIBLE);
                        myLayout.checkBox.setChecked(model.isChecked());
                    } else
                        myLayout.checkBox.setVisibility(View.INVISIBLE);
                    if (html) {
                        TextViewUtil.setTextHTML(myLayout.secondary, model.getSecondary());
                        TextViewUtil.setTextHTML(myLayout.title, model.getTitle());
                        TextViewUtil.setTextHTML(myLayout.subTitle, model.getSubtitle());
                    } else {
                        myLayout.secondary.setText(model.getSecondary());
                        myLayout.title.setText(model.getTitle());
                        myLayout.subTitle.setText(model.getSubtitle());
                    }
                } catch (Exception e) {
                    Log.i(TAG, e.getMessage());
                }
            }

            @Override
            public void clicked() {
                Click(getActualPosition());
            }

            @Override
            public void longClicked() {
                LongClick(getActualPosition());
            }

            @Override
            public void iconClicked() {
                ImageClicked(getActualPosition());
            }

            @Override
            public void iconLongClicked() {
                ImageLongClicked(getActualPosition());
            }

            @Override
            public void extraImageClicked() {
                ExtraImageClicked(getActualPosition());
            }

            @Override
            public void checkboxClicked() {
                CheckboxClick(getActualPosition());
            }

            @Override
            public void checkBoxChecked(boolean checked) {
                CheckboxChanged(getActualPosition(), checked);
                CustomListView.this.getModel(getActualPosition()).setCheckbox(checked);
            }
        }
    }
}
