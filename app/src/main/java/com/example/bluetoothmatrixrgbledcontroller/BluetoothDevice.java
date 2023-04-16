package com.example.bluetoothmatrixrgbledcontroller;

public class BluetoothDevice {
    private final String name;
    private final String mac;

    public BluetoothDevice(String name, String gender) {
        this.name = name;
        this.mac = gender;
    }

    public String getName() {
        return this.name;
    }

    public String getMac() {
        return this.mac;
    }
}
