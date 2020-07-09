package com.example.mymusic;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mymusic.adapters.OrderAdapter;
import com.example.mymusic.models.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {

    List<Order> orderList = new ArrayList<>();
    List<Order> messageList = new ArrayList<>();
    ListView listView;
    TextView emptyCart;
    Button proceedBtn;
    private int status;
    String[] addresses = {"abuhanifa1199@gmail.com","osh.itacademy@gmail.com"};
    String subject = "Music Shop. Android Testing";
//    ArrayList<String> orderArrayList = new ArrayList<>();
//    ArrayAdapter<String> orderAdapter;

    public static final String TAG = "SecondTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        ActionBar actionBar = getSupportActionBar();  //стрелка назад
        actionBar.setDisplayHomeAsUpEnabled(true);

        listView = findViewById(R.id.orderView);
        emptyCart = findViewById(R.id.emptyCart);
        Intent intent = getIntent();
        status = intent.getIntExtra("status",0);
        proceedBtn = findViewById(R.id.proceed);

        messageList = (List<Order>) intent.getSerializableExtra("orders");

        final StringBuilder message = new StringBuilder();

        for(Order order : orderList){
            message.append("Customer name: " + order.getUserName() + "\n" +
                    "Goods name: " + order.getGoodName() + "\n" +
                    "Quantity: " + order.getGoodQuantity() + "\n" +
                    "Order Price: " + order.getGoodPrice() + "\n\n");
        }

        proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                intent.putExtra(Intent.EXTRA_EMAIL, addresses);
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                intent.putExtra(Intent.EXTRA_TEXT, String.valueOf(message));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        if(status==1){
            orderList = (List<Order>) intent.getSerializableExtra("orders");
            OrderAdapter adapter = new OrderAdapter(this,R.layout.list_item,orderList); //создаем адаптер
            listView.setAdapter(adapter);
        }else {
            listView.setVisibility(View.GONE);
            emptyCart.setVisibility(View.VISIBLE);
            proceedBtn.setVisibility(View.GONE);
        }




//        for(Order order: orderList){
//            orderArrayList.add(order.getGoodName());
//        }
//        orderAdapter = new ArrayAdapter<>(this,R.layout.list_item,orderArrayList);
//        listView.setAdapter(orderAdapter);
    }
}