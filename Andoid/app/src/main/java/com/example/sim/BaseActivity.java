package com.example.sim;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sim.auth.UserLoginActivity;
import com.example.sim.auth.UserRegistrationActivity;
import com.example.sim.category.CategoryCreateActivity;
import com.example.sim.utils.CommonUtils;

public class BaseActivity extends AppCompatActivity {

    public BaseActivity(){
        CommonUtils.setContext(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        Intent intent;
        switch (menuItem.getItemId()){
            case R.id.m_home:
                try{
                    intent=new Intent(BaseActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }catch (Exception ex){
                    System.out.println("---Problem" + ex.getMessage());
                }
                return true;
            case R.id.m_create:
                try{
                    intent=new Intent(BaseActivity.this, CategoryCreateActivity.class);
                    startActivity(intent);
                    finish();
                }
                catch (Exception ex){
                    System.out.println("Problem" + ex.getMessage());
                }
                return true;
            case R.id.m_login:
                try{
                    intent=new Intent(BaseActivity.this, UserLoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                catch (Exception ex){
                    System.out.println("Problem with Login Page" + ex.getMessage());
                }
            case R.id.m_registration:
                try {
                    intent=new Intent(BaseActivity.this, UserRegistrationActivity.class);
                    startActivity(intent);
                    finish();
                }
                catch (Exception ex){
                    System.out.println("Problem with Registration Page" + ex.getMessage());
                }
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }
}
