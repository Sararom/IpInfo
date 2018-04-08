package com.romero.ipinfo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText ip, mask, networkId, broadcast, hosts, netPart, hostPart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ip = findViewById(R.id.ip_text);
        mask = findViewById(R.id.mask_text);
        networkId = findViewById(R.id.net_id_text);
        broadcast = findViewById(R.id.broad_text);
        hosts = findViewById(R.id.hosts_text);
        netPart = findViewById(R.id.netp_text);
        hostPart = findViewById(R.id.edit_text_hostp);
    }

    public void calculate(View v){
        String[] ip_separada = ip.getText().toString().split("\\.");

        long bIp =0;
        long bMask = 0;
        long invertMask = 0;
        int intMask = Integer.parseInt(mask.getText().toString());

        if(ip_separada.length != 4) return;

        for(int i=3; i>=0; i--) {
            bIp |= (Long.parseLong(ip_separada[3-i])) << (i*8);
        }

        for(int i=1; i <= intMask; i++) {
            bMask |= 1L << (32-i);
        }

        invertMask = ~bMask;

        networkId.setText(longToIP(bIp & bMask));
        broadcast.setText(longToIP(bIp | invertMask));

        hosts.setText((int)(Math.pow(2, (double)(32-intMask))) -2 + "");
        netPart.setText(intMask + "");
        hostPart.setText((32-intMask) + "");

    }

    public String longToIP(long ip){
        String st="";

        for(int i=3; i>=0; i--) {
            st += (0b1111_1111 & (ip >> (i*8) )) + (i!=0? ".": "");
        }

        return st;
    }


    public void reset(View v){
        ip.setText("");
        ip.requestFocus();
        mask.setText("");
        networkId.setText("");
        broadcast.setText("");
        hosts.setText("");
        netPart.setText("");
        hostPart.setText("");
    }
}
