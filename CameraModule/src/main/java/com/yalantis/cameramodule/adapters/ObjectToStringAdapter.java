
package com.yalantis.cameramodule.adapters;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yalantis.cameramodule.R;

public class ObjectToStringAdapter<T> extends BaseListAdapter<T> {

    public ObjectToStringAdapter(Context context, List<T> list) {
        super(context, list, R.layout.object_to_string_list_item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return createView(position, convertView, false);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return createView(position, convertView, true);
    }

    private View createView(int position, View convertView, boolean dropDown) {
        ViewHolder holder;

        if (convertView == null) {
            if (dropDown) {
                convertView = getDropDownLayout();
            } else {
                convertView = getLayout();
            }
            holder = new ViewHolder();
            holder.text = (TextView) convertView.findViewById(R.id.title);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        T item = getItem(position);
        if (item != null) {
            holder.text.setText(item.toString());
        }

        return convertView;
    }

    public View getDropDownLayout() {
        return getInflater().inflate(R.layout.object_to_string_dropdown_list_item, null);
    }

    class ViewHolder {

        TextView text;

    }

}
