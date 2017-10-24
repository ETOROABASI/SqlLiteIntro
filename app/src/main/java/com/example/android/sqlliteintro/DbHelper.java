package com.example.android.sqlliteintro;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.R.attr.version;
import static com.example.android.sqlliteintro.R.string.marks;

/**
 * Created by ETORO on 16/10/2017.
 */

//YOUR DATABASE CLASS MUST ALWAYS EXTEND SQLiteOpenHelper and implement onCReate and onUpgrade method, together with a constructor
public class DbHelper extends SQLiteOpenHelper {
    //CREATE VARIABLES TO HOLD THE INFO USED TO CREATE YOUR DB


    //YOU BAN PUT THESE STATIC VARIABLES IN A CONTRACT CLASS WHICH IMPLEMENTS BASECOLUMN (CHECK SQLite Master App)
    public static final String DATABASE_NAME = "student.db";
    public static final String TABLE_NAME = "student_table";
    public static final String ID = "ID";
    public static final String FIRST_NAME = "FIRST_NAME";
    public static final String LAST_NAME = "LAST_NAME";
    public static final String MARKS = "MARKS";

    //FOR SIMPLICITY WE MADE THE CONSTRUCTOR TO REQUIRE ONLY ONE ARGUMENT
    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        //SQLiteDatabase db =  this.getWritableDatabase();  //THIS IS FOR ERROR CORRECTION
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //THIS CREATES THE TABLE student_table
        db.execSQL("CREATE TABLE "+ TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, FIRST_NAME TEXT, LAST_NAME TEXT, MARKS " +
                "INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);  //THIS DROPS THE PREVIOUS VERSION OF THE DATABASE
            onCreate(db);                                      //THIS CREATES THE UPGRADED VERSION OF THE TABLE


    }



    //THIS METHOD IS TO INSERT DATA TO OUR DB
    public boolean insertData(String fname, String lname, String marks){
        SQLiteDatabase db  = this.getWritableDatabase();  //SINCE WE WANT TO WRITE TO THE DB
        ContentValues contentValues = new ContentValues();     //USED TO MODIFY DATA INA DB
        contentValues.put(FIRST_NAME, fname);   //TAKES IN THE COLUMN NAME AND THE VALUE TO BE INSERTED
        contentValues.put(LAST_NAME, lname);
        contentValues.put(MARKS, marks);

        //WE COULD LEAVE THE db.insert this way or try a new method below. The method is more preferable cos it accounts for
        //any error that may occur during the insersion period for better understanding, check out the declaration of the
        //insert method
        //db.insert(TABLE_NAME, null, contentValues);  //THIS INSERTS THE DATA INTO THE TABLE
                                                    //TAKES PARAMETERS: TABLENAME, NULL AND CONTENTVALUES instance


        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result== -1)
            return false;

        else
            return true;

    }


    //THIS METHOD IS TO RETRIEVE DATA FROM OUR DB
    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();                     //AN INSTANCE OF OUR DATABASE
        //CURSOR IS USED TO GO THROUGH THE TABLE ROW BY ROW. IS LIKE A POINTER TO A PARTICULAR ROW OF DATA AT ANY TIME
        Cursor cursor = db.rawQuery("SELECT * FROM "+ TABLE_NAME,null);  //SELECTS EVERYTHING FROM THE TABLE
        return cursor;

    }

    //METHOD TO UPDATE DATA IN TABLE
    public boolean updateData(String id, String fname, String lname, String marks){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID, id);
        contentValues.put(FIRST_NAME, fname);
        contentValues.put(LAST_NAME, lname);
        contentValues.put(MARKS, marks);
        db.update(TABLE_NAME, contentValues, "ID = ? ", new String[] {id});    //WHERECLAUSE AND WHEREARGUMENT (3 AND 4)
        return true ;
    }

    //THIS METHOD IS TO DELETE DATA IN DB
    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID= ?", new String [] {id});  //THE ? IS REPLACED BY THE id in the string array
        //THE delete() METHOD RETURNS AN INTEGER. HENCE THE LINE OF CODE RETURNS THAT INTEGER.
        //THE INTEGER RETURNED IS THE NUMBER OF ROWS THAT ARE AFFECTED BY THE COMMAND, HENCE 0, MEANS THAT NO ROW WAS DELETED

    }


}
