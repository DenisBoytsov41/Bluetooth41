package com.example.bluetoothmatrixrgbledcontroller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BluetoothDeviceAdapter extends ArrayAdapter<BluetoothDevice> {
    public BluetoothDeviceAdapter(Context context, List<BluetoothDevice> devices) {
        super(context, R.layout.bluetooth_device_item, devices);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BluetoothDevice device = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.bluetooth_device_item, null);
        }
        ((TextView) convertView.findViewById(R.id.bt_name))
                .setText(device.getName());
        ((TextView) convertView.findViewById(R.id.bt_mac))
                .setText(device.getMac());
        return convertView;
    }
}
