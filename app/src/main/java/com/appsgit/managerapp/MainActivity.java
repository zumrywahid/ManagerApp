package com.appsgit.managerapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends Activity {

    public static final int READ_EMPLOYEE_DATA = 100;

    ListView listView;

    SimpleCursorAdapter simpleCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getActionBar() != null) {
            getActionBar().hide();
        }

        listView = findViewById(R.id.employeeList);

        loadData();

    }

    public void loadData() {
        try {
            Cursor cursor = getEmployeeDetails();

            String[] projection = {"name", "email"};

            int[] elementIds = {R.id.name, R.id.email};


            if (cursor != null) {
                simpleCursorAdapter = new SimpleCursorAdapter(getBaseContext(), R.layout.listview_item, cursor, projection, elementIds, 0);
                listView.setAdapter(simpleCursorAdapter);
            } else {
                ((TextView)findViewById(R.id.title)).setText("No Data available to show.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * We should acces EmployeeContract constants . but for now i will copy and paste it from there.
     * We can make a jar file EmployeeContract class and share with the app otherwise.
     */
    public Cursor getEmployeeDetails() {
        try {
            //I copied this uri from EmployeeProviderApp.
            Uri employeeUri = Uri.parse("content://com.appsgit.employeeprovider/employee");
            String[] projection = { "_id", "name", "email"};

            String selection = "";

            String[] selectionArgs = null;

            String sortOrder = null;

            return getContentResolver().query(employeeUri, projection, selection, selectionArgs, sortOrder);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

}
