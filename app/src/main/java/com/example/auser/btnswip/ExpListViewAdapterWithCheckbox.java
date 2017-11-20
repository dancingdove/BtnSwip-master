package com.example.auser.btnswip;

/**
 * Created by dbhat on 15-03-2016.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// Eclipse wanted me to use a sparse array instead of my hashmaps, I just suppressed that suggestion
@SuppressLint("UseSparseArrays")
public class ExpListViewAdapterWithCheckbox extends BaseExpandableListAdapter {

    public static String SelTtem;
    public String strBffer = "";
    public static int totalMoney;
    public static int TolCnt;
    ArrayList<String> alrMeal=new ArrayList();
    ArrayList<Integer> alrCount=new ArrayList();
    ArrayList<Integer> alrMoney=new ArrayList();

    // Define activity context
    private Context mContext;

    /*
     * Here we have a Hashmap containing a String key
     * (can be Integer or other type but I was testing
     * with contacts so I used contact name as the key)
    */
    private HashMap<String, List<String>> mListDataChild;

    // ArrayList that is what each key in the above
    // hashmap points to
    private ArrayList<String> mListDataGroup;

    // Hashmap for keeping track of our checkbox check states
    private HashMap<Integer, boolean[]> mChildCheckStates;
    private HashMap<Integer, int[]> mChildNumberStates;

    // Our getChildView & getGroupView use the viewholder patter
    // Here are the viewholders defined, the inner classes are
    // at the bottom
    private ChildViewHolder childViewHolder;
    private GroupViewHolder groupViewHolder;


    /*
          *  For the purpose of this document, I'm only using a single
     *	textview in the group (parent) and child, but you're limited only
     *	by your XML view for each group item :)
    */
    private String groupText;
    private String childText;

    /*  Here's the constructor we'll use to pass in our calling
     *  activity's context, group items, and child items
    */
    public ExpListViewAdapterWithCheckbox(Context context, ArrayList<String> listDataGroup, HashMap<String, List<String>> listDataChild) {

        mContext = context;
        mListDataGroup = listDataGroup;
        mListDataChild = listDataChild;

        // Initialize our hashmap containing our check states here
        mChildCheckStates = new HashMap<Integer, boolean[]>();
        mChildNumberStates = new HashMap<Integer, int[]>();
    }

    public int getNumberOfCheckedItemsInGroup(int mGroupPosition) {  //取得一共選了幾個項目
        boolean getChecked[] = mChildCheckStates.get(mGroupPosition);
        int count = 0;
        if (getChecked != null) {
            for (int j = 0; j < getChecked.length; ++j) {
                if (getChecked[j] == true) count++;
            }
        }
        return count;
    }

    public int getNumberOfNumberItemsInGroup(int mGroupPosition) {  //取得一共選了幾個項目
        int getChecked[] = mChildNumberStates.get(mGroupPosition);
        int count = 0;
        if (getChecked != null) {
            for (int j = 0; j < getChecked.length; ++j) {
                if (getChecked[j] >0) count++;
            }
        }
        return count;
    }

    @Override
    public int getGroupCount() {
        return mListDataGroup.size();
    }

    /*
     * This defaults to "public object getGroup" if you auto import the methods
     * I've always make a point to change it from "object" to whatever item
     * I passed through the constructor
    */
    @Override
    public String getGroup(int groupPosition) {
        return mListDataGroup.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        //  I passed a text string into an activity holding a getter/setter
        //  which I passed in through "ExpListGroupItems".
        //  Here is where I call the getter to get that text
        groupText = getGroup(groupPosition);

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_group, null);

            // Initialize the GroupViewHolder defined at the bottom of this document
            groupViewHolder = new GroupViewHolder();

            groupViewHolder.mGroupText = (TextView) convertView.findViewById(R.id.lblListHeader);

            convertView.setTag(groupViewHolder);

//            textViewHolder=new TextViewHolder();
//            textViewHolder.mTextView=(TextView) convertView.findViewById(R.id.textView);
        } else {

            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }

        groupViewHolder.mGroupText.setText(groupText);

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mListDataChild.get(mListDataGroup.get(groupPosition)).size();
    }

    /*
     * This defaults to "public object getChild" if you auto import the methods
     * I've always make a point to change it from "object" to whatever item
     * I passed through the constructor
    */
    @Override
    public String getChild(int groupPosition, int childPosition) {
        return mListDataChild.get(mListDataGroup.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final int mGroupPosition = groupPosition;
        final int mChildPosition = childPosition;

        //  I passed a text string into an activity holding a getter/setter
        //  which I passed in through "ExpListChildItems".
        //  Here is where I call the getter to get that text
        childText = getChild(mGroupPosition, mChildPosition);

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null);

            childViewHolder = new ChildViewHolder();

            childViewHolder.mChildText = (TextView) convertView
                    .findViewById(R.id.lblListItem);

//            childViewHolder.mCheckBox = (CheckBox) convertView
//                    .findViewById(R.id.lstcheckBox);

            childViewHolder.mImageView = (ImageView) convertView.findViewById(R.id.imageView);
            childViewHolder.mSpinner = (Spinner) convertView.findViewById(R.id.spinner);

            //設定spinner物件內容
            ArrayAdapter adapter = ArrayAdapter.createFromResource(
                    this.mContext, R.array.number, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
            childViewHolder.mSpinner.setAdapter(adapter);
//            childViewHolder.mSpinner.setSelection(2);
            convertView.setTag(R.layout.list_item, childViewHolder);

        } else {

            childViewHolder = (ChildViewHolder) convertView
                    .getTag(R.layout.list_item);
        }

        childViewHolder.mChildText.setText(childText);

        childViewHolder.mSpinner.setOnItemSelectedListener(null);
        if (mChildNumberStates.containsKey(mGroupPosition)) {
			/*
			 * if the hashmap mChildCheckStates<Integer, Boolean[]> contains
			 * the value of the parent view (group) of this child (aka, the key),
			 * then retrive the boolean array getChecked[]
			*/
            int getChecked[] = mChildNumberStates.get(mGroupPosition);

            // set the check state of this position's checkbox based on the
            // boolean value of getChecked[position]
            childViewHolder.mSpinner.setSelection(getChecked[mChildPosition]);

        } else {

			/*
			 * if the hashmap mChildCheckStates<Integer, Boolean[]> does not
			 * contain the value of the parent view (group) of this child (aka, the key),
			 * (aka, the key), then initialize getChecked[] as a new boolean array
			 *  and set it's size to the total number of children associated with
			 *  the parent group
			*/
            int getChecked[] = new int[getChildrenCount(mGroupPosition)];

            // add getChecked[] to the mChildCheckStates hashmap using mGroupPosition as the key
            mChildNumberStates.put(mGroupPosition, getChecked);

            // set the check state of this position's checkbox based on the
            // boolean value of getChecked[position]
            childViewHolder.mSpinner.setSelection(0);
        }



        childViewHolder.mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //取得餐點,數量,金額
                strBffer = mListDataChild.get(Fragment1.listDataHeader.get(mGroupPosition)).get(mChildPosition);
                int money= Integer.parseInt(strBffer.substring(strBffer.length() - 4, strBffer.length() - 1).trim());
                strBffer=strBffer.substring(0,strBffer.length()-4);

                if (position>0) {
                    int getChecked[] = mChildNumberStates.get(mGroupPosition);
                    getChecked[mChildPosition] = position;
                    mChildNumberStates.put(mGroupPosition, getChecked);


                    int j=-1;
                    if ((j=alrMeal.indexOf(strBffer))>=0) {//更改餐點數量
                        alrCount.set(j,position);
                        alrMoney.set(j,money);
                    }else {
                        alrMeal.add(strBffer);
                        alrMoney.add(money);
                        alrCount.add(position);
                    }
               }else {
                    int getChecked[] = mChildNumberStates.get(mGroupPosition);
                    getChecked[mChildPosition] = position;
                    Log.d("position","=0");
                    //將已選購餐點數量改為0
                    int j = alrMeal.indexOf(strBffer);
                    Log.d("j",alrMeal.indexOf(strBffer)+"");
                    if (alrMeal.indexOf(strBffer)>=0) {
                        alrMeal.remove(j);
                        alrCount.remove(j);
                        alrMoney.remove(j);
                    }
                }
                //                        //總結點餐情況
                SelTtem = "";
                totalMoney = 0;
                TolCnt = 0;
                System.out.println("alrMeal====" + alrMeal);
                for (int i = 0; i < alrMeal.size(); i++) {
                    totalMoney = totalMoney + alrCount.get(i) * alrMoney.get(i);
                    TolCnt = TolCnt + alrCount.get(i);
                    if (!(SelTtem.equals(""))) SelTtem += "\n";
                    SelTtem = SelTtem + alrMeal.get(i) + " " + alrMoney.get(i) + "元 " + alrCount.get(i) + "個";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(mContext,"onNothingSelected", Toast.LENGTH_SHORT).show();
            }
        });

//

		/*
         * You have to set the onCheckChangedListener to null
		 * before restoring check states because each call to
		 * "setChecked" is accompanied by a call to the
		 * onCheckChangedListener
		*/
//        childViewHolder.mCheckBox.setOnCheckedChangeListener(null);

        if (mChildCheckStates.containsKey(mGroupPosition)) {
			/*
			 * if the hashmap mChildCheckStates<Integer, Boolean[]> contains
			 * the value of the parent view (group) of this child (aka, the key),
			 * then retrive the boolean array getChecked[]
			*/
//            boolean getChecked[] = mChildCheckStates.get(mGroupPosition);

            // set the check state of this position's checkbox based on the
            // boolean value of getChecked[position]
//            childViewHolder.mCheckBox.setChecked(getChecked[mChildPosition]);

        } else {

			/*
			 * if the hashmap mChildCheckStates<Integer, Boolean[]> does not
			 * contain the value of the parent view (group) of this child (aka, the key),
			 * (aka, the key), then initialize getChecked[] as a new boolean array
			 *  and set it's size to the total number of children associated with
			 *  the parent group
			*/
            boolean getChecked[] = new boolean[getChildrenCount(mGroupPosition)];

            // add getChecked[] to the mChildCheckStates hashmap using mGroupPosition as the key
            mChildCheckStates.put(mGroupPosition, getChecked);

            // set the check state of this position's checkbox based on the
            // boolean value of getChecked[position]
//            childViewHolder.mCheckBox.setChecked(false);
        }

//        childViewHolder.mCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                if (isChecked) {
//
//                    boolean getChecked[] = mChildCheckStates.get(mGroupPosition);
//                    getChecked[mChildPosition] = isChecked;
//                    mChildCheckStates.put(mGroupPosition, getChecked);
//                } else {
//
//                    boolean getChecked[] = mChildCheckStates.get(mGroupPosition);
//                    getChecked[mChildPosition] = isChecked;
//
//                }
//            }
//        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    public final class GroupViewHolder {

        TextView mGroupText;
    }



    public final class ChildViewHolder {

        TextView mChildText;
//        CheckBox mCheckBox;
        ImageView mImageView;
        Spinner mSpinner;
    }

}


