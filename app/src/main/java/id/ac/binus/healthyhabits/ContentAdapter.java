package id.ac.binus.healthyhabits;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ContentAdapter extends BaseAdapter {
    Context context;
    List<DataModel> listData = new ArrayList<>();
    LayoutInflater inflater;

    public ContentAdapter(Context context, List<DataModel> listData) {
        this.context = context;
        this.listData = listData;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.listview_content, null);
        TextView desc = view.findViewById(R.id.txtDesc);

        desc.setText(listData.get(i).getPlan_desc());
        return view;
    }
}
