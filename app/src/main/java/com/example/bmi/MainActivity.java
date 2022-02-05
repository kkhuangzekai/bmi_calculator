package com.example.bmi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText vHeight, vWeight;
    Button submitButton,aboutButton;  //挖个坑，这里需要加这句对按钮变量的声明

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  //设置显示出的画面是res==layout==activiti_main
        //-- get views
        vHeight = (EditText) findViewById(R.id.heightET);
        vWeight = (EditText) findViewById(R.id.weightET);
        submitButton = (Button) findViewById(R.id.reportBtn);
        aboutButton =(Button) findViewById(R.id.aboutBtn);  //挖个坑，这里需要加这句对按钮ID联动调用的声明
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(R.string.about_button);
                builder.setMessage(R.string.about_msg);
                builder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            { }
                        });
                builder.create();
                builder.show();
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String height = vHeight.getText().toString();
                String weight = vWeight.getText().toString();

                //检查是否有输入，防止异常退出  data verification
                if (height.equals("") || weight.equals("")) {
                    Toast.makeText(MainActivity.this, R.string.bmi_warning, Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), ReportActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("height", height);
                    bundle.putString("weight", weight);
                    intent.putExtras(bundle);
                    startActivity(intent);
                };
                savePreferences(height, weight);//对下面两个动作：数据储存的调用
            }
        });
    }
    public void savePreferences(String h, String w) {
        SharedPreferences pref = getSharedPreferences("BMI", MODE_PRIVATE);
        pref.edit().putString("height", h).apply();
        pref.edit().putString("weight", w).apply();
    }
    public void loadPreferences() {
        SharedPreferences pref = getSharedPreferences("BMI", MODE_PRIVATE);
        vHeight.setText(pref.getString("height", "0"));
        vWeight.setText(pref.getString("weight", "0"));
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadPreferences(); } //载入数据
}
