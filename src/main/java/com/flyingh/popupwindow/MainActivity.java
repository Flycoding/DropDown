package com.flyingh.popupwindow;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    public static final int NUMBER = 20;
    private EditText inputNumberEditText;
    private ListView listView;
    private List<String> numbers;
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputNumberEditText = (EditText) findViewById(R.id.inputNumber);
        init();
    }

    private void init() {
        initNumbers();
        initListView();
    }

    private void initNumbers() {
        numbers = new ArrayList<>();
        for (int i = 0; i < NUMBER; i++) {
            numbers.add("10000" + i);
        }
    }

    private void initListView() {
        listView = new ListView(this);
        listView.setBackgroundResource(R.drawable.listview_background);
        listView.setVerticalScrollBarEnabled(false);
        listView.setDivider(null);
        listView.setAdapter(newAdapter());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ViewHolder viewHolder = (ViewHolder) view.getTag();
                inputNumberEditText.setText(viewHolder.numberTextView.getText());
                popupWindow.dismiss();
            }
        });
    }

    private BaseAdapter newAdapter() {
        return new BaseAdapter() {
            @Override
            public int getCount() {
                return numbers.size();
            }

            @Override
            public Object getItem(int position) {
                return numbers.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                View view = getView(convertView);
                ViewHolder viewHolder = (ViewHolder) view.getTag();
                viewHolder.numberTextView.setText((CharSequence) getItem(position));
                viewHolder.deleteIconImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        numbers.remove(position);
                        notifyDataSetChanged();
                    }
                });
                return view;
            }

            private View getView(View convertView) {
                if (convertView != null) {
                    return convertView;
                }
                View view = View.inflate(MainActivity.this, R.layout.list_item, null);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.numberTextView = (TextView) view.findViewById(R.id.number);
                viewHolder.deleteIconImageView = (ImageView) view.findViewById(R.id.deleteIcon);
                view.setTag(viewHolder);
                return view;
            }
        };
    }

    public void showPopupWindow(View view) {
        popupWindow = new PopupWindow(this);
        popupWindow.setWidth(inputNumberEditText.getWidth());
        popupWindow.setHeight(500);
        popupWindow.setContentView(listView);
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(inputNumberEditText);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (popupWindow != null) {
            popupWindow.dismiss();
            popupWindow = null;
        }
    }

    class ViewHolder {
        TextView numberTextView;
        ImageView deleteIconImageView;

    }


}
