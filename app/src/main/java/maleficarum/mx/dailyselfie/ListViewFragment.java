package maleficarum.mx.dailyselfie;

import android.app.Dialog;
import android.app.ListFragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oscar on 11/26/14.
 */
public class ListViewFragment extends ListFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setListAdapter(new ListViewAdapter(getActivity(), new ArrayList<ListViewItem>()));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getListView().setDivider(null);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        ListViewItem item = (ListViewItem) getListAdapter().getItem(position);

        final Dialog dialog = new Dialog(this.getActivity());

        //setting custom layout to dialog
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setTitle("Daily Selfie");

        //adding text dynamically
        TextView txt = (TextView) dialog.findViewById(R.id.textView);
        txt.setText(item.description + " " + item.hours);

        ImageView image = (ImageView)dialog.findViewById(R.id.image);
        image.setImageBitmap(item.image);

        //adding button click event
        Button dismissButton = (Button) dialog.findViewById(R.id.button);
        dismissButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}