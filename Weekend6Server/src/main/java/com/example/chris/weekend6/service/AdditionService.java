package com.example.chris.weekend6.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.example.chris.weekend6.IAdd;

/**
 * Created by chris on 12/18/2017.
 */

public class AdditionService extends Service
{
    private final IAdd.Stub mBinder = new IAdd.Stub()
    {
        @Override
        public int add(int num1, int num2) throws RemoteException
        {
            // TODO Auto-generated method stub
            return (num1 + num2);
        }
    };
    
    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return mBinder;
    }
}
