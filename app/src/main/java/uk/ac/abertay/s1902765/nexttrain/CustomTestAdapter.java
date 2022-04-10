package uk.ac.abertay.s1902765.nexttrain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.fragment.app.Fragment;

import java.util.List;

public class CustomTestAdapter extends BaseAdapter {

    List<StationItem> result;
    Context context;
    private static LayoutInflater inflater = null;

    public CustomTestAdapter(Fragment fragmentActivity, List<StationItem> stationItems) {
        result = stationItems;
        context = fragmentActivity.getContext();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return result.size();
    }

    @Override
    public StationItem getItem(int i) {
        return result.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.station_searchitem_layout, viewGroup, false);
        }

        StationItem currentItem = (StationItem) getItem(i);
        TextView titleTextView = view.findViewById(R.id.stationName);
        TextView crsTextView = view.findViewById(R.id.stationCrs);

        titleTextView.setText(currentItem.Name);
        crsTextView.setText(currentItem.CrsCode);

        return view;
    }
}

