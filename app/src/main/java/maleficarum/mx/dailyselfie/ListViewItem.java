package maleficarum.mx.dailyselfie;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;


/**
 * @author  maleficarum [ github.com/maleficarum ]
 */
public class ListViewItem {
    public String title;
    public String description;
    public Bitmap image ;
    public String hours;

    public ListViewItem(String title, String description, String hour, Bitmap bm) {
        this.title = title;
        this.description = description;
        this.image = bm;
        this.hours = hour;
    }
}