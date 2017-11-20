package com.example.auser.btnswip;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

/**
 * Created by auser on 2017/11/12.
 */

public class Fragment2 extends Fragment {
    Spinner spinnerCity,spinnerRoad;
    private List<CharSequence> age_data = null;
    private ArrayAdapter<CharSequence> ages = null;// 声明一个ArrayAdapter来适配年龄
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment1, container, false);//更改layout的位置
        spinnerCity = (Spinner) view.findViewById(R.id.spinnerCity);
        spinnerRoad = (Spinner) view.findViewById(R.id.spinnerRoad);

//        ArrayAdapter adapter=ArrayAdapter.createFromResource(
//                getContext(),R.array.city,android.R.layout.simple_spinner_dropdown_item);
//        spinnerCity.setAdapter(adapter);

//        age = new Spinner(this);// 创建Spinner对象
//        spinnerCity.setPrompt("選擇城市:");// 设置Prompt
        age_data = Arrays.asList(new CharSequence[] {
                "桃園市中壢區", "桃園市平鎮區","桃園市楊梅區"});// 设置年龄段数组并最终转换为List类型
        ages = new ArrayAdapter<CharSequence>(getContext(),
                android.R.layout.simple_spinner_item, age_data);// 实例化ArrayAdapter
//        ages.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// 设置列表项显示风格


        ages   = ages.createFromResource(getActivity().getBaseContext(),
                R.array.city, android.R.layout.simple_spinner_item);
//        spinnerCity.setAdapter(ages);// 设置显示信息
        return inflater.inflate(R.layout.fragment2, container, false);
//        return view;
    }
}
