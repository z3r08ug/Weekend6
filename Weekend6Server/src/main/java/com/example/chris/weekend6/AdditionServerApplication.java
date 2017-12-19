package com.example.chris.weekend6;

import android.app.Application;
import android.content.Context;

import com.example.chris.weekend6.di.app.AppComponent;
import com.example.chris.weekend6.di.app.AppModule;
import com.example.chris.weekend6.di.app.DaggerAppComponent;
import com.example.chris.weekend6.di.main.MainComponent;
import com.example.chris.weekend6.di.main.MainModule;

/**
 * Created by chris on 12/18/2017.
 */

public class AdditionServerApplication extends Application
{
    private AppComponent appComponent;
    private MainComponent mainComponent;
    
    @Override
    public void onCreate()
    {
        super.onCreate();
    
        AppModule appModule = new AppModule();
        
        appComponent = DaggerAppComponent.builder()
                .appModule(appModule)
                .build();
    }
    
    public static AdditionServerApplication get(Context context)
    {
        return (AdditionServerApplication) context.getApplicationContext();
    }
    
    public MainComponent getMainComponent()
    {
        mainComponent = appComponent.add(new MainModule());
        return mainComponent;
    }
    public void clearMainComponent()
    {
        mainComponent = null;
    }
}
