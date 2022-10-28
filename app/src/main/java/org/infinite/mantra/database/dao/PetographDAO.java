package org.infinite.mantra.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import org.infinite.mantra.database.model.PetographModel;

import java.util.ArrayList;
import java.util.List;

public class PetographDAO extends SQLiteOpenHelper {

    private static final String BMI_TABLE = "bmiTable";
    private static final String DATABASE_NAME = "vitalsDB";
    private static final int DATABASE_VERSION = 3;
    private static final String INSERT_BMI = "CREATE TABLE IF NOT EXISTS bmiTable(height TEXT,weight TEXT,bmi TEXT)";
    private static final String INSERT_VITAL = "CREATE TABLE IF NOT EXISTS vitalsTable(name TEXT, systolic TEXT,diastolic TEXT,dataDate TEXT,dataTime TEXT,pulse TEXT,lnmp TEXT)";
    private static final String VITALS_TABLE = "vitalsTable";
    Context context;

    public PetographDAO(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(INSERT_VITAL);
        sqLiteDatabase.execSQL(INSERT_BMI);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS vitalsTable");
        onCreate(sqLiteDatabase);
    }

    public void insertIntoDB(String name, String systolic, String diastolic, String dataDate, String dataTime, String pulse, String lnmp) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("systolic", systolic);
        values.put("diastolic", diastolic);
        values.put("dataDate", dataDate);
        values.put("dataTime", dataTime);
        values.put("pulse", pulse);
        values.put("lnmp", lnmp);
        db.insert(VITALS_TABLE, null, values);
        db.close();
    }

//    public void insertIntoDB(String systolicBP, String diastolicBP, String dateRecorded, String timeRecorded, String pulseRate, String lnmp) {
//        System.out.println("Running DAO......!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//        SQLiteDatabase db = getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("systolic", systolicBP);
//        values.put("diastolic", diastolicBP);
//        values.put("dataDate", dateRecorded);
//        values.put("dataTime", timeRecorded);
//        values.put("pulse", pulseRate);
//        values.put("lnmp", lnmp);
//        db.insert(VITALS_TABLE, null, values);
//        db.close();
//    }

    public void insertBMI(String height, String weight, String bmi) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("height", height);
        values.put("weight", weight);
        values.put("bmi", bmi);
        db.insert(BMI_TABLE, null, values);
        db.close();
    }

    public List<PetographModel> getDataFromDB() {
        Exception e;
        List<PetographModel> modelList = null;
        try {
            List<PetographModel> modelList2 = new ArrayList<>();
            try {
                Cursor cursor = getWritableDatabase().rawQuery("select * from vitalsTable", null);
                if (cursor == null || !cursor.moveToFirst()) {
                    assert cursor != null;
                    cursor.close();
                    return modelList2;
                }
                do {
                    PetographModel model = new PetographModel();
                    model.setName(cursor.getString(0));
                    model.setSystolic(cursor.getString(1));
                    model.setDiastolic(cursor.getString(2));
                    model.setDateMeasured(cursor.getString(3));
                    model.setTimeMeasured(cursor.getString(4));
                    model.setPulseRate(cursor.getString(5));
                    model.setLnmp(cursor.getString(6));
                    modelList2.add(model);
                } while (cursor.moveToNext());
                cursor.close();
                return modelList2;
            } catch (Exception e2) {
                e = e2;
                modelList = modelList2;
                e.printStackTrace();
                return modelList;
            }
        } catch (Exception e3) {
            e = e3;
            e.printStackTrace();
            return modelList;
        }
    }

    public List<PetographModel> getDataBMI() {
        Exception e;
        List<PetographModel> modelList = null;
        try {
            List<PetographModel> modelList2 = new ArrayList<>();
            try {
                Cursor cursor = getWritableDatabase().rawQuery("select * from bmiTable", null);
                if (cursor == null || !cursor.moveToFirst()) {
                    cursor.close();
                    return modelList2;
                }
                do {
                    PetographModel model = new PetographModel();

                    model.setUserHeight(cursor.getString(0));
                    model.setUserWeight(cursor.getString(1));
                    model.setBmi(cursor.getString(1));
                    modelList2.add(model);
                } while (cursor.moveToNext());
                cursor.close();
                return modelList2;
            } catch (Exception e2) {
                e = e2;
                modelList = modelList2;
                e.printStackTrace();
                return modelList;
            }
        } catch (Exception e3) {
            e = e3;
            e.printStackTrace();
            return modelList;
        }
    }

    public void deleteARow(String time) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM vitalsTable WHERE dataTime='" + time + "';");
        db.close();
    }

    public void deleteAll() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM vitalsTable");
        db.execSQL("DELETE FROM bmiTable");
        db.close();
    }
}
