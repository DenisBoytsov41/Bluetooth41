package com.example.bluetoothmatrixrgbledcontroller;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ControlActivity extends AppCompatActivity {
    String mac;
    BluetoothSocket clientSocket;

    SeekBar xSeekBar;
    SeekBar ySeekBar;
    SeekBar rSeekBar;
    SeekBar gSeekBar;
    SeekBar bSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        xSeekBar = findViewById(R.id.seekBarX);
        ySeekBar = findViewById(R.id.seekBarY);
        rSeekBar = findViewById(R.id.seekBarRed);
        gSeekBar = findViewById(R.id.seekBarGreen);
        bSeekBar = findViewById(R.id.seekBarBlue);
        TextView textViewX = findViewById(R.id.seekBarValueX);
        TextView textViewY = findViewById(R.id.seekBarValueY);
        TextView textViewR = findViewById(R.id.seekBarValueR);
        TextView textViewG = findViewById(R.id.seekBarValueG);
        TextView textViewB = findViewById(R.id.seekBarValueB);
        Button colBut = findViewById(R.id.button);

        xSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                textViewX.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        ySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                textViewY.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        rSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                textViewR.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        gSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                textViewG.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        bSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                textViewB.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        SeekBar.OnSeekBarChangeListener seeekchange= new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                colBut.setBackgroundColor(Color.rgb(rSeekBar.getProgress(),gSeekBar.getProgress(),bSeekBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };

        mac = getIntent().getStringExtra("mac");

        TextView textViewMac = findViewById(R.id.textViewMac);

        textViewMac.setText(mac);

        connect();
    }

    public void reconnectButton(View v)
    {
        reconnect();
    }

    private void reconnect()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void sendButton(View v)
    {
        try {
            OutputStream outStream = clientSocket.getOutputStream();
            StringBuilder data = new StringBuilder();
            data.append("$");
            data.append(this.xSeekBar.getProgress()).append(' ');
            data.append(this.ySeekBar.getProgress()).append(' ');
            data.append(this.rSeekBar.getProgress()).append(' ');
            data.append(this.gSeekBar.getProgress()).append(' ');
            data.append(this.bSeekBar.getProgress()).append(';');
            byte[] dataByte = data.toString().getBytes();

            outStream.write(dataByte);

        } catch (IOException e) {
            Log.d("BLUETOOTH", e.getMessage());
        }
    }

    private void connect ()
    {
        BluetoothAdapter bluetooth = BluetoothAdapter.getDefaultAdapter();
        boolean isConnected = true;
        try{
            BluetoothDevice device = bluetooth.getRemoteDevice(mac);

            Method m = device.getClass().getMethod(
                    "createRfcommSocket", new Class[] {int.class});

            clientSocket = (BluetoothSocket) m.invoke(device, 1);
            clientSocket.connect();


        } catch (IOException e) {
            Log.d("BLUETOOTH", e.getMessage());
            isConnected = false;
        } catch (SecurityException e) {
            Log.d("BLUETOOTH", e.getMessage());
            isConnected = false;
        } catch (NoSuchMethodException e) {
            Log.d("BLUETOOTH", e.getMessage());
            isConnected = false;
        } catch (IllegalArgumentException e) {
            Log.d("BLUETOOTH", e.getMessage());
            isConnected = false;
        } catch (IllegalAccessException e) {
            Log.d("BLUETOOTH", e.getMessage());
            isConnected = false;
        } catch (InvocationTargetException e) {
            Log.d("BLUETOOTH", e.getMessage());
            isConnected = false;
        }

        if (isConnected)
            Toast.makeText(getApplicationContext(), "Подключено", Toast.LENGTH_LONG).show();
        else {
            Toast.makeText(getApplicationContext(), "Не удалось подключиться", Toast.LENGTH_LONG).show();
            reconnect();
        }
    }
}