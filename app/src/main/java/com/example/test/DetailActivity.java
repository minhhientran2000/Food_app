package com.example.test;

import static java.util.ResourceBundle.getBundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;


public class DetailActivity extends AppCompatActivity {

    private TextView addToCartBtn;
    private TextView titleTxt,feeTxt,descriptionTxt,numberOrderTxt;
    private ImageView plusBtn,MinusBtn,picFood;
    private FoodDomain object;
    int numberOrder = 1;
    private ManagementCart managementCart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        managementCart = new ManagementCart(this);
        initView();
        getBundle();
        ImageButton back = (ImageButton) findViewById(R.id.backDetail);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
    private void getBundle()
    {
        object = (FoodDomain) getIntent().getSerializableExtra("object");
        Glide.with(this)
                .load(object.getPic())
                .into(picFood);

        titleTxt.setText(object.getTitle());
        feeTxt.setText("$ " + object.getFee());
        descriptionTxt.setText(object.getDescription());
        numberOrderTxt.setText(String.valueOf(numberOrder));

        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberOrder += 1;
                numberOrderTxt.setText(String.valueOf(numberOrder));
            }
        });
        MinusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numberOrder > 1)
                {
                    numberOrder -=1;
                }
                numberOrderTxt.setText(String.valueOf(numberOrder));

            }
        });
        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  new Database(getBaseContext()).addToCart(new Order(foodId,object.getTitle(),object.getQuantity(),object.getFee()));
                Toast.makeText(DetailActivity.this, "Added to Cart", Toast.LENGTH_SHORT).show();*/
                object.setNumberInCart(numberOrder);
                managementCart.insertFood(object);
                startActivity(new Intent(DetailActivity.this,IntroActivity.class));
            }
        });
    }
    private void initView()
    {
        addToCartBtn = findViewById(R.id.Btn_addtocart);
        titleTxt = findViewById(R.id.title);
        feeTxt = findViewById(R.id.priceTxt);
        descriptionTxt = findViewById(R.id.description);
        numberOrderTxt = findViewById(R.id.numberOrderTxt);
        plusBtn = findViewById(R.id.btn_plus);
        MinusBtn = findViewById(R.id.btn_minus);
        picFood = findViewById(R.id.picFood);

    }

}