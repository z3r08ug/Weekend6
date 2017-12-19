package com.example.chris.weekend6.di.main;


import com.example.chris.weekend6.view.main.ServerMainActivity;

import dagger.Subcomponent;

/**
 * Created by Admin on 11/29/2017.
 */

@Subcomponent(modules = MainModule.class)
public interface MainComponent
{
    void inject(ServerMainActivity serverMainActivity);
}
