package com.example.chris.weekend6.di.app;


import com.example.chris.weekend6.di.main.MainComponent;
import com.example.chris.weekend6.di.main.MainModule;

import dagger.Component;

/**
 * Created by chris on 12/7/2017.
 */

@Component(modules = AppModule.class)
public interface AppComponent
{
    MainComponent add(MainModule mainModule);
}
