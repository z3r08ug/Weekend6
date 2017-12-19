package com.example.chris.weekend6.view.main;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.chris.weekend6.AdditionClientApplication;
import com.example.chris.weekend6.IAdd;
import com.example.chris.weekend6.R;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MainContract.View
{
    @Inject
    MainPresenter presenter;
    private TextView tvSum;
    private EditText etVal1;
    private EditText etVal2;
    protected IAdd AddService;
    ServiceConnection connection;
    public static final String TAG = MainActivity.class.getSimpleName() + "_TAG";
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    
        AdditionClientApplication.get(this).getMainComponent().inject(this);
    
        tvSum = findViewById(R.id.tvSum);
        etVal1 = findViewById(R.id.etVal1);
        etVal2 = findViewById(R.id.etVal2);
        
        initConnection();
        
        presenter.attachView(this);
    }
    
    private void initConnection()
    {
        connection = new ServiceConnection()
        {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service)
            {
                AddService = IAdd.Stub.asInterface(service);
                Toast.makeText(MainActivity.this, "Addition Service Connected.", Toast.LENGTH_SHORT).show();
            }
    
            @Override
            public void onServiceDisconnected(ComponentName name)
            {
                AddService = null;
                Toast.makeText(MainActivity.this, "Service Disconnected.", Toast.LENGTH_SHORT).show();
            }
        };
        
        if (AddService == null)
        {
            Intent intent = new Intent();
            intent.setAction("service.Calculator");
            Log.d("TAG", "initConnection: intent"+intent);
            intent = createExplicitFromImplicitIntent(this, intent);
            //bind to remote service
            Log.d("TAG", "initConnection: intent: "+intent);
            bindService(intent, connection, Service.BIND_AUTO_CREATE);
        }
    }
    
    @Override
    protected void onStop()
    {
        super.onStop();
        AdditionClientApplication.get(this).clearMainComponent();
    }
    
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        presenter.detachView();
        unbindService(connection);
    }
    
    @Override
    public void showError(String error)
    {
    
    }
    
    public void onCalculate(View view)
    {
        int val1 = Integer.parseInt(etVal1.getText().toString());
        int val2 = Integer.parseInt(etVal2.getText().toString());
        try
        {
            tvSum.setText("Sum: " + AddService.add(val1, val2));
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }
    
    public static Intent createExplicitFromImplicitIntent(Context context, Intent implicitIntent)
    {
        // Retrieve all services that can match the given intent
        PackageManager pm = context.getPackageManager();
        Log.d(TAG, "createExplicitFromImplicitIntent: pm"+pm);
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);
        Log.d(TAG, "createExplicitFromImplicitIntent: resolve"+resolveInfo);
        // Make sure only one match was found
        if (resolveInfo == null || resolveInfo.size() != 1)
        {
            return null;
        }
        
        // Get component info and create ComponentName
        ResolveInfo serviceInfo = resolveInfo.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName component = new ComponentName(packageName, className);
        
        // Create a new intent. Use the old one for extras and such reuse
        Intent explicitIntent = new Intent(implicitIntent);
        
        // Set the component to be explicit
        explicitIntent.setComponent(component);
        
        return explicitIntent;
    }
}
