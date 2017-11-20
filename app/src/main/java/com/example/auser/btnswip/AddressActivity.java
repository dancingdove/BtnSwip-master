package com.example.auser.btnswip;

import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class AddressActivity extends AppCompatActivity implements ListView.OnItemClickListener {
    ListView listView;
    private DB mDbHelper;
    private Cursor mNotesCursor;
    String[] from;
    AlertDialog dialog; //讓自定Layout可有關閉功能
    View root;
    int count;
    EditText et;
    String[] colField={"姓名","電話","地址"};
    long rowId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        //Tell the list view which view to display when the list is empty
        listView=(ListView)findViewById(R.id.list);  //26版本以上,R.id.list,不用再轉型
        listView.setEmptyView(findViewById(R.id.empty));//當listviews沒有內容時,才需要這一行.告訴使用者目前沒有資料
        listView.setOnItemClickListener(this);
        mDbHelper=new DB(this).open();
        setAdapter();
    }


    private void setAdapter() {
//        mDbHelper = new DB(this);//設定DB物件
//        mDbHelper.open();
//        mDbHelper = new DB(this).open();
        mNotesCursor = mDbHelper.getAll();
        if (mNotesCursor!=null)
            mNotesCursor.moveToFirst();
        startManagingCursor(mNotesCursor);//早期將Cursor交給生命週期管理,目官方建議改成多執行緒來實作
        from = new String[]{"uid","mailcode","city"};//來源資料,且只取note欄位
        int[] to = new int[]{R.id.text1,R.id.text2,R.id.text3};//將資料放在android.R.id.text1,在simple_list_item_i.xml的 android:id="@android:id/text1"
         SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.simple_list_item, mNotesCursor, from, to,SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

//        listView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        rowId=id;
        Toast.makeText(this,"第" + id + "項",Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,1,0,"新增").setIcon(android.R.drawable.ic_menu_add).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(0,2,0,"刪除").setIcon(android.R.drawable.ic_menu_delete).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(0,3,0,"修改").setIcon(android.R.drawable.ic_menu_edit).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case 1://新增
                count++;
                mDbHelper.create("addNote" +count);
                setAdapter();
                break;
            case 2://刪除

                mDbHelper.delete(rowId);
                setAdapter();
                break;

            case 3://修改

                root = getLayoutInflater().inflate(R.layout.dialog_layout, null);//找出根源樹,
                et = (EditText) root.findViewById(R.id.et_tel);  //若不使用root,則它會去找主畫面的layout的元件
                Button confirm = (Button) root.findViewById(R.id.btn_confirm);
                Button cancel=(Button)root.findViewById(R.id.btn_cancel);
                confirm.setOnClickListener(dialoglistener);
                cancel.setOnClickListener(dialoglistener);
                openOptionsDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    View.OnClickListener dialoglistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("v.getContext()",((Button)v).getText().toString());
            switch (v.getId()){
                case R.id.btn_confirm:

                    mDbHelper.update(rowId,et.getText().toString());
                    setAdapter();
                    dialog.dismiss();
                    break;
                case R.id.btn_cancel:
                    dialog.dismiss();
                    break;
            }

        }


    };

    void openOptionsDialog() {
        AlertDialog.Builder ab = new AlertDialog.Builder(this);

        ab.setView(root);
        dialog = ab.show();
    }
}
