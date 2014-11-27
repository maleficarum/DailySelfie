package maleficarum.mx.dailyselfie;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class ListViewItem {
    public final String title;        // the text for the ListView item title
    public final String description;  // the text for the ListView item description
    public Bitmap image ;
    public String hours;

    public ListViewItem(String title, String description, String hour, Bitmap bm) {
        this.title = title;
        this.description = description;
        this.image = bm;
        this.hours = hour;
    }
}