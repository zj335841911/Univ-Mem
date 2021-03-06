package com.example.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.preference.PreferenceManager;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.bean.User;
import com.example.univ_mem.MainActivity;
import com.example.univ_mem.R;
import com.example.utils.L;
import com.umeng.message.PushAgent;

import org.litepal.LitePal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by SunPeng.
 * USER : SunPeng.
 * DATE : 2021/4/12.
 * TIME : 12:21.
 */

public class LoginActivity extends AppCompatActivity {
    private static String TAG = "LoginActivity";
    private View head_img;
    private EditText usernameEdit;
    private EditText passwordEdit;
    private Button loginbtn;
    private CheckBox remPwd;
    private int loginCheckFlag;
    private String username;
    private String password;
    private TextView newUser;
    private static final int REGISTER_CODE = 10;
    private SharedPreferences sharepr;
    private SharedPreferences.Editor shEditor;
    private static int REFRESH_LOGIN = 12;
    private static int MAIN_REQUEST = 11;
    public static String dbName = "univ_mem.db";//??????????????????
    private static String DATABASE_PATH = "/data/data/com.example.university_memory/databases/";//??????????????????????????????

    private void init() {
        head_img = (CircleImageView) findViewById(R.id.nav_left_head_image);
        usernameEdit = (EditText) findViewById(R.id.loginActivity_username);
        passwordEdit = (EditText) findViewById(R.id.loginActivity_password);
        loginbtn = (Button) findViewById(R.id.loginActivity_loginButton);
        remPwd = (CheckBox) findViewById(R.id.loginActivity_rem_pwd);
        newUser = (TextView) findViewById(R.id.loginActivity_newUser);
        sharepr = PreferenceManager.getDefaultSharedPreferences(this);
        shEditor = sharepr.edit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        usernameEdit = (EditText) findViewById(R.id.loginActivity_username);
        passwordEdit = (EditText) findViewById(R.id.loginActivity_password);
        loginbtn = (Button) findViewById(R.id.loginActivity_loginButton);
        remPwd = (CheckBox) findViewById(R.id.loginActivity_rem_pwd);
        newUser = (TextView) findViewById(R.id.loginActivity_newUser);
        sharepr = PreferenceManager.getDefaultSharedPreferences(this);
        shEditor = sharepr.edit();

        //???????????????????????????
        boolean dbExist = checkDataBase();
        if (dbExist) {

        } else {//???????????????raw???????????????????????????
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
        PushAgent.getInstance(this).onAppStart();//?????????????????????
        //???????????????android????????????home??????????????????????????????????????????????????????????????????????????????????????????????????????
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        setContentView(R.layout.activity_login);
        loginCheckFlag = sharepr.getInt("checkFlag", 0);//??????????????????
        if (loginCheckFlag == 1) {
            usernameEdit = (EditText) findViewById(R.id.loginActivity_username);
            passwordEdit = (EditText) findViewById(R.id.loginActivity_password);
            remPwd = (CheckBox) findViewById(R.id.loginActivity_rem_pwd);
            String username = sharepr.getString("username", "");
            String password = sharepr.getString("password", "");
            usernameEdit.setText(username);
            passwordEdit.setText(password);
            remPwd.setChecked(true);
        } else {
            usernameEdit = (EditText) findViewById(R.id.loginActivity_username);
            passwordEdit = (EditText) findViewById(R.id.loginActivity_password);
            usernameEdit.setText(" ");//???????????????
            passwordEdit.setText(" ");
        }
        loginbtn = (Button) findViewById(R.id.loginActivity_loginButton);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usernameEdit.getText().toString();
                password = passwordEdit.getText().toString();
                List<User> users = LitePal.where("username=? and password=?", username, password).find(User.class);

                // if (user != null) ????????????????????????????????????????????????????????????????????????????????????
                if (!(users.isEmpty())) {
                    if (loginCheckFlag == 1) {
                        shEditor.putString("username", username);
                        shEditor.putString("password", password);
                        shEditor.putInt("checkFlag", loginCheckFlag);
                    } else {
                        shEditor.clear();
                    }
                    shEditor.apply();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("username", username);
                    intent.putExtra("password", password);
                    intent.putExtra("loginCheckFlag", loginCheckFlag);
                    if (loginCheckFlag == 1) {

                    } else {//?????????????????????????????????????????????????????????????????????????????????????????????
                        usernameEdit.setText("");
                        passwordEdit.setText("");//???????????????
                    }
                    //closeSoftKeyInput();
                    startActivityForResult(intent, MAIN_REQUEST);
                    Toast.makeText(LoginActivity.this, "????????????", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "???????????????????????????", Toast.LENGTH_SHORT).show();
                }

            }
        });
        remPwd = (CheckBox) findViewById(R.id.loginActivity_rem_pwd);
        remPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    loginCheckFlag = 1;
                } else {
                    loginCheckFlag = 0;
                }
            }
        });
        newUser = (TextView) findViewById(R.id.loginActivity_newUser);
        newUser.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                //??????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                sendCode(LoginActivity.this);
            }
        });

    }



    @SuppressLint("ResourceAsColor")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REGISTER_CODE) {
            if (!"".equals(data)) {
                username = data.getStringExtra("username");
                password = data.getStringExtra("password");
                usernameEdit.setText(username);
                passwordEdit.setText(password);
            }
        } else if (requestCode == MAIN_REQUEST && resultCode == REFRESH_LOGIN) {
            if (loginCheckFlag == 1) {//????????????????????????????????????????????????????????????????????????????????????????????????
                usernameEdit.setText(data.getStringExtra("username"));
                passwordEdit.setText(data.getStringExtra("password"));
                shEditor.putString("username", data.getStringExtra("username"));
                shEditor.putString("password", data.getStringExtra("password"));
                shEditor.apply();
            } else {
                usernameEdit.setText(data.getStringExtra("username"));//??????????????????????????????????????????????????????
                shEditor.putString("username", data.getStringExtra("username"));
            }
        }
        newUser.setTextColor(R.color.white);
    }


    //???????????????
    private void closeSoftKeyInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        //boolean isOpen=imm.isActive();//isOpen?????????true???????????????????????????
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * ???????????????????????????
     */
    public boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String databaseFilename = DATABASE_PATH + dbName;
            checkDB = SQLiteDatabase.openDatabase(databaseFilename, null,
                    SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {

        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    /**
     * ?????????
     *?????????????????????????????????
     * @throws IOException
     */
    public void copyDataBase() throws IOException {
        String databaseFilenames = DATABASE_PATH + dbName;
        File dir = new File(DATABASE_PATH);
        if (!dir.exists())//??????????????????????????????????????????????????????
            dir.mkdir();
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(databaseFilenames);//?????????????????????????????????
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        InputStream is = LoginActivity.this.getResources().openRawResource(R.raw.univ_mem);//?????????????????????????????????
        byte[] buffer = new byte[8192];
        int count = 0;
        try {

            while ((count = is.read(buffer)) > 0) {
                os.write(buffer, 0, count);
                os.flush();
            }
        } catch (IOException e) {

        }
        try {
            is.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ??????????????????
     */
    public void sendCode(Context context) {
        RegisterPage page = new RegisterPage();
        //?????????????????????ui?????????????????????????????????????????????null
        page.setTempCode(null);
        page.setRegisterCallback(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // ?????????????????????
                    HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                    // ?????????????????????86???
                    String country = (String) phoneMap.get("country");
                    // ?????????????????????13800138000???
                    String phone = (String) phoneMap.get("phone");
                    // TODO ??????????????????????????????????????????????????????
                    L.e(TAG, "------------->" + country + "  " + phone);
                    //?????????????????????
                    Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                    intent.putExtra("phone", phone);
                    startActivityForResult(intent, REGISTER_CODE);
                } else {
                    // TODO ?????????????????????
                    L.e(TAG, "------------->" + "??????????????????");
                    Toast.makeText(LoginActivity.this, "?????????????????????", Toast.LENGTH_SHORT).show();
                }
            }
        });
        page.show(context);
    }
}
