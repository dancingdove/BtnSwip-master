package com.example.auser.btnswip;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by auser on 2017/11/12.
 */

public class Fragment3 extends Fragment {

    Spinner spin,spin2;
    ArrayAdapter<String> adapter,adapter2;
     String[] city = new String[] {"中壢區","平鎮區","桃園區"};
     String[] road1 = new String[] {"中正路","中興路","中山路","大同路"};
     String[][] road2 = new String[][] {{"中正路","中興路","中山路","大同路"},{"環中路","環西路","環南路","環北路"},{"中央東路","中央西路","中山南路","中山北路"}};
//    private Context context;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment3, container, false);
        spin = (Spinner)view.findViewById(R.id.spinner);
        spin2 = (Spinner)view.findViewById(R.id.spinner1);

        adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,city);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, type);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        adapter2 = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,road1);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin2.setAdapter(adapter2);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int pos = spin.getSelectedItemPosition();
                adapter2 = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, road2[pos]);
//                adapter2 = new ArrayAdapter(getContext(),R.layout.simple_list_item,road);
                Toast.makeText(getContext(),position+"",Toast.LENGTH_SHORT).show();
                spin2.setAdapter(adapter2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                int pos = spin.getSelectedItemPosition();
//                adapter2 = new ArrayAdapter(getContext(),R.layout.simple_list_item,road);

                if (position > 0)
                Toast.makeText(getContext(),position + "",Toast.LENGTH_SHORT).show();
//                spin2.setAdapter(adapter2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }



}
