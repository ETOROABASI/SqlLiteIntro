package com.example.android.sqlliteintro;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DbHelper dbHelper;               //CREATES AN INSTANCE IF THE DbHelper CLASS
    EditText fname, lname, marks, id;
    Button btnAddData, btnViewAll, btnUpdate, btnDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DbHelper(this);                   //THIS CALLS THE DBHelper CLASS AND HENCE CREATES THE DB AS THE ACTIVITY IS CREATED

        setContentView(R.layout.activity_main);

        fname = (EditText) findViewById(R.id.edit_main_fname);
        lname = (EditText) findViewById(R.id.edit_main_sname);
        marks = (EditText) findViewById(R.id.edit_main_marks);
        id = (EditText) findViewById(R.id.edit_main_id);
        btnAddData= (Button) findViewById(R.id.btn_main_submit);
        btnViewAll = (Button) findViewById(R.id.btn_main_viewAll);
        btnUpdate  = (Button) findViewById(R.id.btn_main_update);
        btnDelete = (Button) findViewById(R.id.btn_main_delete);
        addData();
        viewAllData();
        updateData();
        deleteData();

    }

    public void addData(){
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = dbHelper.insertData(fname.getText().toString(), lname.getText().toString(), marks.getText().toString());
                        if(isInserted == true){
                            Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                            id.clearFocus();                       //CLEARS THE CURSOR FROM THE EDITTEXT FIELD
                            marks.clearFocus();
                            fname.setText("");                  //CLEARS TEXT
                            lname.setText("");
                            marks.getText().clear();            //SAME THING: CLEARS TEXT
                            id.getText().clear();



                        }

                        else{
                            Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    public void viewAllData(){
        btnViewAll.setOnClickListener(
                new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        Cursor cursor = dbHelper.getAllData();  //   THIS STORES ALL THE DATA IN THE CURSOR
                        if (cursor.getCount() == 0){            //IF THERE IS NO DATA FOUND
                            //SHOW ERROR MESSAGE
                            showMessage("ERROR", "No data found");      //IT SHOWS THIS IN THE DIALOGBOX
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();  //SAVES THE DATA IN A STRINGBUFFER
                        while(cursor.moveToNext()){     //WHILE THERE IS STILL ANOTHER ROW,

                            //HERE, THE BUFFER GETS THE DATA AT ID COLUMN USING ITS INDEX 0. SAME AS THAT FOR FIRST_NAME
                            //INDEX 1 AND SO ON. THE CURSOR GETS THE VALUES IN THE COLUMN AND CONVERTS TO STRING FOR THE
                            //STRING BUFFER
                            buffer.append("ID: "+ cursor.getString(0) + "\n");       //takes in a string and the column content
                            //SAME THING AS WHAT IS DONE FOR ID
                            buffer.append("FIRST NAME: "+ cursor.getString(cursor.getColumnIndex(DbHelper.FIRST_NAME)) + "\n");
                            buffer.append("LAST NAME: "+ cursor.getString(2) + "\n");
                            buffer.append("MARKS: "+ cursor.getString(3) + "\n\n");

                            showMessage("LIST", buffer.toString());  //GETS THE BUFFER TO STRING AND SENDS IT AS THE MESSAGE TO
                                                                    //THE DIALOGBOX

                        }
                    }
                }
        );
    }
        //TO SHOW THE DATA IN THE DIALOG BOX
    public void showMessage(String title, String message){
        //ALERTDIALOGBUILDER IS LIKE A POP UP SCREEN WHERE WE CAN DISPLAY OUR TEXT.
        // WE ARE NOT YET PUTTING IT INTO A VIEW OR ANYTHING YET...
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);            //TO CANCEL IT AFTER IT HAS BUILT
        builder.setTitle(title);                   //SETS THE TITLE OF THE DIALOG BOX
        builder.setMessage(message);                //SETS THE MESSAGE INSIDE THE DIALOG BOX
        builder.show();                             //TO SHOW THE DIALOG BOX

    }

    //TO UPDATE DATA
    public void updateData(){
        btnUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdated = dbHelper.updateData(id.getText().toString(), fname.getText().toString(),
                                lname.getText().toString(), marks.getText().toString());
                        if(isUpdated){
                            Toast.makeText(MainActivity.this, "DataBase Updated", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Database Not Update", Toast.LENGTH_SHORT).show();
                        }
                        id.clearFocus();
                        fname.setText("");
                        lname.setText("");
                        marks.setText("");
                        id.setText("");
                    }
                }
        );

    }

    //TO DELETE DATA
    public void deleteData(){
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int deletedRow = dbHelper.deleteData(id.getText().toString());
                        if(deletedRow != 0){
                            Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(MainActivity.this, "No Data Deleted", Toast.LENGTH_SHORT).show();
                        }
                        id.clearFocus();
                        fname.setText("");
                        lname.setText("");
                        marks.setText("");
                        id.setText("");
                    }
                }

        );
    }



}
