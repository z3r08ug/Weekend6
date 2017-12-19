package com.example.chris.weekend6.di.main;



import com.example.chris.weekend6.view.main.MainPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Admin on 11/29/2017.
 */

@Module
public class MainModule
{
    @Provides
    MainPresenter providerMainPresenter()
    {
        return new MainPresenter();
    }
}
