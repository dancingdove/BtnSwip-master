package com.example.auser.btnswip;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by auser on 2017/11/12.
 */

public class Fragment1 extends Fragment {
    ExpListViewAdapterWithCheckbox listAdapter;
    ExpandableListView expListView;
    public static ArrayList<String> listDataHeader;  //第一層
    public static HashMap<String, List<String>> listDataChild;
    TextView textView;
    Button button;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment1, container, false);//更改layout的位置
        textView = (TextView) view.findViewById(R.id.textView);

        button = (Button) view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int count =0;
                for(int mGroupPosition =0; mGroupPosition < listAdapter.getGroupCount(); mGroupPosition++)
                {
                    count = count +  listAdapter.getNumberOfCheckedItemsInGroup(mGroupPosition);
                }
                textView.setText(ExpListViewAdapterWithCheckbox.SelTtem + "\n總金額:" + ExpListViewAdapterWithCheckbox.totalMoney + "元");

//                strBffer + "\n總共"+ TolCnt  +"個\n總金額"+totalMoney+"元",
            }
        });

        // get the listview
        expListView = (ExpandableListView) view.findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();
        listAdapter = new ExpListViewAdapterWithCheckbox(getContext(), listDataHeader, listDataChild);
        // setting list adapter
        expListView.setAdapter(listAdapter);
        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
//                 Toast.makeText(getApplicationContext(),
//                 "Group Clicked " + listDataHeader.get(groupPosition),
//                 Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
//                Toast.makeText(getApplicationContext(),
//                        listDataHeader.get(groupPosition) + " Expanded",
//                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
//                Toast.makeText(getApplicationContext(),
//                        listDataHeader.get(groupPosition) + " Collapsed",
//                        Toast.LENGTH_SHORT).show();

            }
        });

//        return inflater.inflate(R.layout.fragment1, container, false);
        return  view;
    }

    /*
      * Preparing the list data
      */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();  //第一層類別
        listDataChild = new HashMap<String, List<String>>();//第二層子項

        // Adding 第一層類別內容
        listDataHeader.add("便當,快餐類");
        listDataHeader.add("麵食類");
        listDataHeader.add("快炒類");
        listDataHeader.add("燒烤類");

        // Adding child data //設定第一類的子項
        List<String> top250 = new ArrayList<String>();
        top250.add("紅燒/蔥爆牛肉飯 120元");
        top250.add("泰式椒麻雞腿飯  120元");
        top250.add("懷舊便當(控肉/排骨) 110元");
        top250.add("炸大雞腿飯 110元");
        top250.add("油雞飯 100元");
        top250.add("雞排飯 100元");
        top250.add("滷雞腿飯 100元");
        //設定第二類的子項
        List<String> nowShowing = new ArrayList<String>();
        nowShowing.add("牛肉麵 120元");
        nowShowing.add("牛肉炒麵 100元");
        nowShowing.add("牛肉湯餃 90元");
        nowShowing.add("肉絲炒麵 80元");
        nowShowing.add("家常麵(湯) 80元");
        nowShowing.add("牛肉湯麵 70元");
        //設定第三類的子項
        List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("胡椒蝦 380元");
        comingSoon.add("檸檬麵 380元");
        comingSoon.add("宮保雞丁 200元");
        comingSoon.add("乾煎鱈魚 200元");
        comingSoon.add("薑絲大腸 200元");
        //設定第四類的子項
        List<String> burn = new ArrayList<String>();
        burn.add("烤蝦 380元");
        burn.add("牛小排 200元");

        // listDataChild HashMap<String, List<String>>
        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
        listDataChild.put(listDataHeader.get(1), nowShowing);
        listDataChild.put(listDataHeader.get(2), comingSoon);
        listDataChild.put(listDataHeader.get(3), burn);
    }
}


