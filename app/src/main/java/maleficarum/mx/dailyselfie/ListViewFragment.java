package maleficarum.mx.dailyselfie;

import android.app.ListFragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

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
        // retrieve theListView item
        ListViewItem item = (ListViewItem) getListAdapter().getItem(position);

        Toast.makeText(getActivity(), item.title, Toast.LENGTH_SHORT).show();
    }
}