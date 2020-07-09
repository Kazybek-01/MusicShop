package com.example.mymusic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymusic.models.Order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MusicShop extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    TextView result;
    TextView priceView;
    Button addToCart;
    Button button2;
    Button button3;
    Spinner spinner;
    ArrayList<String> spinnerArrayList = new ArrayList<>();
    ArrayAdapter<String> spinnerAdapter;
    HashMap<String, Double> database;
    String itemName; //имя товара
    ImageView imageView;
    double price;
    EditText userName;
    List<Order> orderList = new ArrayList<>();

    private int counter = 0;
    private static final String TAG = "MyTagMusic";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_shop);

        // + - result
        addToCart = findViewById(R.id.addToCart);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        result = findViewById(R.id.textView2);
        priceView = findViewById(R.id.price);
        imageView = findViewById(R.id.imageView2);
        userName = findViewById(R.id.editTextTextPersonName);

        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        //arraylist for Spinner
        spinnerArrayList.add("guitar");
        spinnerArrayList.add("keyboard");
        spinnerArrayList.add("drums");
        spinnerArrayList.add("rock");

        //Adapter for Spinner
        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerArrayList); //layout для одного элемента
        //берем каждый элемент и помещаем в шаблон
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        //Database for Spinner
        database = new HashMap<>();
        database.put("guitar", 500.0);
        database.put("keyboard", 1000.0);
        database.put("drums", 700.0);
        database.put("rock", 1500.0);

        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        addToCart.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {    //применить это меню к этому активити
        MenuInflater inflater = getMenuInflater();     //создание меню
        inflater.inflate(R.menu.music_cart_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.item1) {
            Intent secondActivity = new Intent(MusicShop.this,OrderActivity.class);
            if(!orderList.isEmpty()){
                secondActivity.putExtra("orders", (Serializable) orderList);
                secondActivity.putExtra("status",1);
            }
            else {
                secondActivity.putExtra("status",0);
            }
            startActivity(secondActivity);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button3:
                if (counter == 0) {
                    break;
                } else {
                    counter--;
                    result.setText(String.valueOf(counter));
                    priceView.setText(String.valueOf(price * counter));
                    break;
                }
            case R.id.button2:
                counter++;
                result.setText(String.valueOf(counter));
                priceView.setText(String.valueOf(price * counter));
                break;
            case R.id.addToCart:
                orderProcess();
                break;
        }
    }
    public void orderProcess(){
        Order order = new Order();

        if(!TextUtils.isEmpty(userName.getText().toString())){
            order.setUserName(userName.getText().toString());
            order.setGoodName(spinner.getSelectedItem().toString());
            order.setGoodPrice(Double.parseDouble(priceView.getText().toString()));
            order.setGoodQuantity(Integer.parseInt(result.getText().toString()));

            if(orderList.size() > 0){
                Order answer = checking(order.getGoodName(), orderList);
                if (answer != null){
                    answer.setGoodQuantity(order.getGoodQuantity() + answer.getGoodQuantity());
                    answer.setGoodPrice(order.getGoodPrice() + answer.getGoodPrice());
                }
                else {
                    orderList.add(order);
                }
            }
            else {
                orderList.add(order);
            }
            Log.i(TAG, order.toString());

            Toast.makeText(this, "Вы успешно добавили товар в корзину!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Пожалуйства заполните все поля!", Toast.LENGTH_SHORT).show();
        }
    }
    public Order checking(String goodName, List<Order> orderList){
//        Order answer = null;
        for(Order order: orderList){
            if(order.getGoodName().equals(goodName)){
               return order;
            }
        }
        return null;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        itemName = spinner.getSelectedItem().toString(); //guitar
        price = database.get(itemName);//возвращает цену 500.0
        priceView.setText(String.valueOf(price));
        counter = 1;
        result.setText("1");

        switch (itemName) {
            case "guitar":
                imageView.setImageResource(R.drawable.guitar);
                break;
            case "drums":
                imageView.setImageResource(R.drawable.drums);
                break;
            case "rock":
                imageView.setImageResource(R.drawable.rock);
                break;
            case "keyboard":
                imageView.setImageResource(R.drawable.keyboard);
                break;
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}