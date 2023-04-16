package com.example.bluetoothmatrixrgbledcontroller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private static final List<com.example.bluetoothmatrixrgbledcontroller.BluetoothDevice> bt_device
            = new ArrayList<com.example.bluetoothmatrixrgbledcontroller.BluetoothDevice>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 2);
        }

        Set<BluetoothDevice> pairedDevices;
        mBluetoothAdapter.startDiscovery();

        pairedDevices = mBluetoothAdapter.getBondedDevices();

        bt_device.clear();

        for (BluetoothDevice bt : pairedDevices) {
            bt_device.add(new com.example.bluetoothmatrixrgbledcontroller.BluetoothDevice(bt.getName(), bt.getAddress()));
        }

        ListView listView = findViewById(R.id.listViewBluetoothDevice);

        BluetoothDeviceAdapter adapter = new BluetoothDeviceAdapter(this, bt_device);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openControl(bt_device.get(i));
            }
        });
    }


    private void openControl(com.example.bluetoothmatrixrgbledcontroller.BluetoothDevice bt)
    {
        Intent intent = new Intent(this, ControlActivity.class);
        intent.putExtra("mac",bt.getMac());
        startActivity(intent);
    }
}