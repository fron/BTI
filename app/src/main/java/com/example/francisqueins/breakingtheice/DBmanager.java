package com.example.francisqueins.breakingtheice;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

public class DBmanager extends SQLiteOpenHelper {
    String DB_PATH =null;

    private static final int DB_VERSION = 1;
    // Database Name
    private static final String DB_NAME = "contactsDB"; // Database name
    private static final String DB_TABLE = "contacts"; // contacts: table name
    private SQLiteDatabase myDataBase;
    private final Context myContext;

    public DBmanager(Context context) {
        super(context, DB_NAME, null, 1);
        this.myContext = context;
        // retrieves DB path
        DB_PATH="/data/data/"+context.getPackageName()+"/"+"databases/";
    }

    public void createDataBase() throws IOException{
        boolean dbExist = checkDataBase();
        if (dbExist) {
            //do nothing - database already exist
        }
        else {
            this.getReadableDatabase(); // an empty database will be created
            try {copyDataBase();}
            catch (IOException e) {
                throw new Error("Error copying database");
            }
        } // end else
    }

        // Check if the DB exists
    private boolean checkDataBase(){

        SQLiteDatabase checkDB = null;
        try{
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        }
        catch(SQLiteException e){
            //database doesn't exist
        }
        if(checkDB != null){
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    // Copy DB
    private void copyDataBase() throws IOException {
        InputStream myInput = myContext.getAssets().open(DB_NAME); // Input stream <= database
        String outFileName = DB_PATH + DB_NAME;  // access to the DB
        OutputStream myOutput = new FileOutputStream(outFileName); // Output stream => device DB
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }
        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    // Open DB
    public void openDataBase() throws SQLException{
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    // Close DB
    @Override
    public synchronized void close() {
        if(myDataBase != null)
            myDataBase.close();
        super.close();
    }

    // Cursor returns the result of a query
    public Cursor query(String table,String[] columns, String selection,String[] selectionArgs,String groupBy,String having,String orderBy){
        return myDataBase.query("contacts", null, null, null, null, null, null);
    }

    // Insert a contact
    public void insertContact(HashMap<String, String> queryValues){

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("conPhone", queryValues.get("conPhone"));
        values.put("conName", queryValues.get("conName"));

        database.insert("contacts", null, values);
        database.close();
    }

    // Update a contact
    public int updateContact(HashMap<String, String> queryValues){

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("_Id", queryValues.get("_Id"));
        values.put("conPhone", queryValues.get("conPhone"));
        values.put("conName", queryValues.get("conName"));

        return database.update("contacts", values,
                "_Id" + " = ?", new String[] {queryValues.get("_Id") });
    }

    // Delete contact
    public void deleteContact(String _Id){
        SQLiteDatabase database = this.getWritableDatabase();
        String deleteQuery = "DELETE FROM contacts WHERE _Id ='" + _Id + "'";
        database.execSQL(deleteQuery);
    }

    // Getting an specific contact
    public HashMap<String, String> getContactInfo(String conPhone){

        HashMap<String, String> contactsList = new HashMap<String, String>();
        SQLiteDatabase database = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM contacts WHERE conPhone ='" + conPhone + "'";
        Cursor cursor = database.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                contactsList.put("_Id", cursor.getString(0));        // _Id
                contactsList.put("conPhone", cursor.getString(1));   //  conPhone
                contactsList.put("conName", cursor.getString(2));    //  conName
            } while(cursor.moveToNext());
        }
        return contactsList;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Do nothing
    }
}