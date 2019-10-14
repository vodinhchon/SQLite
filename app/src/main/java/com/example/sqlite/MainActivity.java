package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText et_id, et_title, et_author;
    Button bt_save, bt_select, bt_exit, bt_update, bt_delete;
    GridView gv_display;
    DBHelper dbHelper;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mappingView();
        eventClick();

    }

    private void eventClick() {
        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = new Book();
                book.setId(Integer.parseInt(et_id.getText().toString()));
                book.setTitle(et_title.getText().toString());
                book.setAuthor(et_author.getText().toString());
                if (dbHelper.insertBook(book)) {
                    Toast.makeText(getApplicationContext(), "da luu thanh cong", Toast.LENGTH_SHORT).show();
                    clear();
                } else {
                    Toast.makeText(getApplicationContext(), "khong luu thanh cong", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bt_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> list = new ArrayList<>();
                ArrayList<Book> booklist = new ArrayList<>();
                String id = et_id.getText().toString();
                if (!id.isEmpty()) {
                    int idkq = Integer.parseInt(id);
                    Book book = dbHelper.getBook(idkq);
                    list.add(book.getId() + "");
                    list.add(book.getTitle());
                    list.add(book.getAuthor());
                } else {
                    booklist = dbHelper.getAllBook();
                    for (Book b : booklist) {
                        list.add(b.getId() + "");
                        list.add(b.getTitle());
                        list.add(b.getAuthor());
                    }
                }
                adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, list);
                gv_display.setAdapter(adapter);
            }
        });

        bt_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        bt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = et_id.getText().toString();
                if (!id.isEmpty()) {
                    int idkq = Integer.parseInt(id);
                    dbHelper.deleteBook(idkq);
                    adapter.notifyDataSetChanged();
                    clear();
                    Toast.makeText(getApplicationContext(), "Xoa thanh cong", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Xoa khong thanh cong", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void clear() {
        et_id.setText("");
        et_title.setText("");
        et_author.setText("");
        et_id.requestFocus();
    }

    private void mappingView() {
        //EditText
        et_id = (EditText) findViewById(R.id.editTextID);
        et_title = (EditText) findViewById(R.id.editTextTitle);
        et_author = (EditText) findViewById(R.id.editTextName);

        //GridView
        gv_display = (GridView) findViewById(R.id.gridView_listItem);

        //DBHelper
        dbHelper = new DBHelper(this);

        //Button
        bt_save = (Button) findViewById(R.id.buttonSave);
        bt_select = (Button) findViewById(R.id.buttonSelect);
        bt_exit = (Button) findViewById(R.id.buttonExit);
        bt_delete = (Button) findViewById(R.id.buttonDelete);
        bt_update = (Button) findViewById(R.id.buttonUpdate);
    }
}


