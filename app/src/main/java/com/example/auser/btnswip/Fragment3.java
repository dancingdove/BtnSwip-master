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
    ArrayAdapter adapter,adapter2;
//     String[] city = new String[] {"桃園市中壢區","桃園市平鎮區","桃園市桃園區"};
//    private String[] road = new String[] {"中正路","中興路","中山路","大同路"};
//     String[][] road = new String[][] {{"中正路","中興路","中山路","大同路"},{"環中路","環西路","環南路","環北路"},{"中央東路","中央西路","中山南路","中山北路"}};
//    private Context context;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment3, container, false);
        spin = (Spinner)view.findViewById(R.id.spinner);
        spin2 = (Spinner)view.findViewById(R.id.spinner1);

        adapter = ArrayAdapter.createFromResource(getContext(),R.array.city,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.select_dialog_singlechoice01);
        spin.setAdapter(adapter);
        adapter2 = ArrayAdapter.createFromResource(getContext(),R.array.road,android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(R.layout.select_dialog_singlechoice01);
        spin2.setAdapter(adapter2);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                int pos = spin.getSelectedItemPosition();
//                adapter2 = new ArrayAdapter(getContext(),R.layout.simple_list_item,road);
//                Toast.makeText(getContext(),"1",Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getContext(),position+1 + "",Toast.LENGTH_SHORT).show();
                spin2.setAdapter(adapter2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }



}
