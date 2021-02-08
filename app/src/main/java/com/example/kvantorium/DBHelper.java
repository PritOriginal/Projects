package com.example.kvantorium;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.telephony.PhoneNumberUtils;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 14;
    public static final String TABLE_NAME = "projects";
    public static final String TABLE_NAME2 = "componentsProject";
    public static final String TABLE_NAME3 = "components";
    public static final String NAME = "name";
    public static final String ID = "id";
    public static final String ID_USER = "idUser";
    public static final String ID_PROJECT = "idProject";
    public static final String ID_COMPONENT = "idComponent";
    public static final String DESCRIPTION = "description";
    public static final String NUMBER = "number";
    public static final String ALL_NUMBER = "allNumber";
    public static final String USE_NUMBER = "useNumber";
    public static final String LOST_NUMBER = "lostNumber";


    String components[] =
            {"Ардуино",
                    "Компьютер",
                    "Светодиоды"};
    int allNumber[] =
            {100,
                    50,
                    5000};
    int useNumber[] =
            {0,
                    0,
                    0};
    //public static final String KEY_ID = "_id";

    public DBHelper(Context context) {
        super(context, "myDB", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//////////////Таблица всех проектов
        db.execSQL("create table " + TABLE_NAME + " (" + ID + " integer primary key," + ID_USER + " integer," + NAME + " text," + DESCRIPTION + " text" + ")");
//////////////Таблица компонентов проекта
        db.execSQL("create table " + TABLE_NAME2 + " (" + ID + " integer primary key," + ID_PROJECT + " integer," + ID_COMPONENT + " text," + NUMBER + " integer" + ")");
//////////////Таблица всех компонентов
        db.execSQL("create table " + TABLE_NAME3 + " (" + ID + " integer primary key," + NAME + " text," + ALL_NUMBER + " integer," + USE_NUMBER + " integer" + ")");

        ContentValues contentValues = new ContentValues();
        for (int i = 0; i < components.length; i++) {
            contentValues.put(NAME, components[i]);
            contentValues.put(ALL_NUMBER, allNumber[i]);
            contentValues.put(USE_NUMBER, useNumber[i]);
            //  contentValues.put(LOST_NUMBER, allNumber[i] - useNumber[i]);
            db.insert(TABLE_NAME3, null, contentValues);
        }
        ContentValues contentValues1 = new ContentValues();
        contentValues1.put(ID_PROJECT, 1);
        contentValues1.put(ID_COMPONENT, 1);
        contentValues1.put(NUMBER, 2);
        db.insert(TABLE_NAME2, null, contentValues1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);
        db.execSQL("drop table if exists " + TABLE_NAME2);
        db.execSQL("drop table if exists " + TABLE_NAME3);
        onCreate(db);
    }

    public void onUpdate(SQLiteDatabase db, String TABLE, String WHERE, String EQUALLY, ContentValues NEW_DATA) {
        db.update(TABLE, NEW_DATA, WHERE + '=' + EQUALLY, null);
    }

    public void onInsert(SQLiteDatabase db, String TABLE, ContentValues ADD_DATA) {
        db.insert(TABLE, null, ADD_DATA);
    }

    public void onDelete(SQLiteDatabase db, String TABLE, String WHERE, String EQUALLY) {
        db.delete(TABLE, WHERE + '=' + EQUALLY, null);
    }

    public List<Project> getAllProjectUser(SQLiteDatabase db, int idUser) {
        List<Project> projects = new ArrayList<Project>();
        Cursor cursor = db.query("projects", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int idUserColIndex = cursor.getColumnIndex(DBHelper.ID_USER);
                int idColIndex = cursor.getColumnIndex(DBHelper.ID);
                int _idUser = cursor.getInt(idUserColIndex);
                int nameColIndex = cursor.getColumnIndex(DBHelper.NAME);
                int descriptionColIndex = cursor.getColumnIndex(DBHelper.DESCRIPTION);
                if (_idUser == idUser) {
                    Project p = new Project(cursor.getInt(idColIndex), cursor.getString(nameColIndex), cursor.getString(descriptionColIndex));
                    p.setName(cursor.getString(nameColIndex));
                    p.setDescription(cursor.getString(descriptionColIndex));
                    p.setId(cursor.getInt(idColIndex));
                    projects.add(p);
                }
            } while (cursor.moveToNext());
        } else {
            cursor.close();
        }
        return projects;
    }

    public Project getProject(SQLiteDatabase db, int idProject) {
        Project project = new Project();
        Cursor cursor = db.query(DBHelper.TABLE_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int idColIndex = cursor.getColumnIndex(DBHelper.ID);
                int _id = cursor.getInt(idColIndex);
                if (_id == idProject) {
                    int nameColIndex = cursor.getColumnIndex(DBHelper.NAME);
                    int descriptionColIndex = cursor.getColumnIndex(DBHelper.DESCRIPTION);
                    project.setName(cursor.getString(nameColIndex));
                    project.setDescription(cursor.getString(descriptionColIndex));
                }
            } while (cursor.moveToNext());
        } else {
            cursor.close();
        }
        return project;
    }

    public void updateComponent(SQLiteDatabase db, int idComp, int number) {
        Cursor cursor1 = db.query(DBHelper.TABLE_NAME3, null, null, null, null, null, null);
        if (cursor1.moveToFirst()) {
            do {
                int idColIndexComp = cursor1.getColumnIndex(DBHelper.ID);
                int nameColIndexComp = cursor1.getColumnIndex(DBHelper.NAME);
                int useColIndexComp = cursor1.getColumnIndex(DBHelper.USE_NUMBER);
                int id = cursor1.getInt(idColIndexComp);
                String name = cursor1.getString(nameColIndexComp);
                int useNumber = cursor1.getInt(useColIndexComp);
                if (idComp == id) {
                    ContentValues contentValues1 = new ContentValues();
                    contentValues1.put(DBHelper.USE_NUMBER, useNumber + number);
                    db.update(DBHelper.TABLE_NAME3, contentValues1, DBHelper.ID + " = " + id, null);
                }
            } while (cursor1.moveToNext());
        } else {
            cursor1.close();
        }
    }

    public List<Component> getAllComponents(SQLiteDatabase db) {
        List<Component> allComponent = new ArrayList<Component>();
        Cursor cursor = db.query(TABLE_NAME3, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int nameColIndexComp = cursor.getColumnIndex(NAME);
                int allColIndexComp = cursor.getColumnIndex(ALL_NUMBER);
                int useColIndexComp = cursor.getColumnIndex(USE_NUMBER);
                int all = cursor.getInt(allColIndexComp);
                int use = cursor.getInt(useColIndexComp);
                Component c = new Component();
                c.setNameComponent(cursor.getString(nameColIndexComp));
                c.setNumber(all - use);
                allComponent.add(c);
            } while (cursor.moveToNext());
        } else {
            cursor.close();
        }
        return allComponent;
    }

    public List<Component> getComponentsProject(SQLiteDatabase db, int id) {
        List<Component> components = new ArrayList<Component>();
        Cursor cursor = db.query(DBHelper.TABLE_NAME2, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int count = cursor.getCount();
                int idColIndexProject = cursor.getColumnIndex(DBHelper.ID_PROJECT);
                int idProject = cursor.getInt(idColIndexProject);
                if (idProject == id) {

                    int idCompColIndexComp = cursor.getColumnIndex(ID_COMPONENT);
                    int idComponent =  cursor.getInt(idCompColIndexComp);
                    int numberColIndexComp = cursor.getColumnIndex(NUMBER);

                    Cursor cursor1 = db.query(TABLE_NAME3, null, null, null, null, null, null);
                    if (cursor1.moveToFirst()) {
                        do {
                            int idColIndexComp = cursor1.getColumnIndex(ID);
                            int idComp_ = cursor1.getInt(idColIndexComp);
                            if (idComponent == idComp_) {
                                int nameColIndexComp = cursor1.getColumnIndex(NAME);
                                Component c = new Component();
                                c.setNameComponent(cursor1.getString(nameColIndexComp));
                                String name = c.getNameComponent();
                                c.setNumber(cursor.getInt(numberColIndexComp));
                                components.add(c);
                            }
                        } while (cursor1.moveToNext());
                    } else {
                        cursor1.close();
                    }
                }
            } while (cursor.moveToNext());
        } else {
            cursor.close();
        }
        return components;
    }
}