package com.example.chris.weekend6.view.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.chris.weekend6.AdditionServerApplication;
import com.example.chris.weekend6.R;

import javax.inject.Inject;

public class ServerMainActivity extends AppCompatActivity implements MainContract.View
{
    @Inject
    MainPresenter presenter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    
        AdditionServerApplication.get(this).getMainComponent().inject(this);
        
        presenter.attachView(this);
    }
    
    @Override
    protected void onStop()
    {
        super.onStop();
        AdditionServerApplication.get(this).clearMainComponent();
    }
    
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        presenter.detachView();
    }
    
    @Override
    public void showError(String error)
    {
    
    }
}
