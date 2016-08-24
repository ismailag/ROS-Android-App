package com.example.ismail.app3;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;

import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.SocketIO;
import io.socket.SocketIOException;



import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;

import java.util.Enumeration;

import org.xwalk.core.XWalkView;

public class MainActivity extends AppCompatActivity {
    private static SeekBar seek,seekv;
    private XWalkView xWalkWebView;

    private boolean done=true ;
    protected SocketIO socket ;

    {
        try {
            socket = new SocketIO("http://192.168.0.201:8080"); // connect to the server
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main );
        socket.connect(new IOCallback() {

            @Override
            public void onMessage(JSONObject json, IOAcknowledge ack) {
                try {
                    System.out.println("Server said:" + json.toString(2));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMessage(String data, IOAcknowledge ack) {
                System.out.println("Server said: " + data);
            }

            @Override
            public void onError(SocketIOException socketIOException) {
                System.out.println("an Error occured");
                socketIOException.printStackTrace();
            }

            @Override
            public void onDisconnect() {
                System.out.println("Connection terminated.");
            }

            @Override
            public void onConnect() {
                System.out.println("Connection established");

            }

            @Override
            public void on(String event, IOAcknowledge ack, Object... args) {
                System.out.println("Server triggered event '" + event + "'");
            }
        });


        new Thread(new Runnable() {
            public void run(){
                seek_bar() ;
            }
        }).start();
// crosswalk is an enhanced webview (more fluid than the native one) ,
// so you can easily open webcontent inside a native android application
       String imageUrl ="http://192.168.0.201:8181/stream?topic=/camera/rgb/image_raw&quality=65";
      String stream="<img src="+imageUrl+" style=\"display: inline; height: 100%; width:100%\" />" ;
        xWalkWebView=(XWalkView)findViewById(R.id.xwalkWebView);
        xWalkWebView.load("http://192.168.1.2/demos/streamingtest.html?stream=1",null);
        xWalkWebView.setBackgroundColor(Color.TRANSPARENT);

    }



    public static byte[] getLocalIPAddress () {
        byte ip[]=null;
        try {
            for (Enumeration en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = (NetworkInterface)en.nextElement();
                for (Enumeration enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = (InetAddress)enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        ip= inetAddress.getAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            Log.i("SocketException ", ex.toString());
        }
        return ip;

    }



    public void seek_bar()
    {

        seekv=(SeekBar)findViewById(R.id.mySeekBar) ; //vertical seekv to control velocity
        seek=(SeekBar)findViewById(R.id.seekBar1) ; // seek to control direction
        seek.setMax(50);
        seek.setProgress(25) ;
        seekv.setProgress(50) ;
        socket.emit("move1",50,50); // at the beginnig the direction is forward (first arg) and the velocity is 0 (second arg)

        seek.setOnSeekBarChangeListener(

                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        int x=2*progress; // the direction
                        int y=seekv.getProgress(); // the velocity
                        //this block is made to avoid sending the same command multiple times
                        if (x>45 && x<55 && fromUser )
                        {seek.setProgress(25);
                            if(!done)
                            {done=true ;
                                socket.emit("move1",x,y); // send the command
                            }
                        }
                        else if (fromUser){
                            socket.emit("move1", x, y); //send the command
                            done=false ;
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        socket.emit("move1",2*seek.getProgress(),seekv.getProgress());
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        seek.setProgress(25) ;

                        socket.emit("move1",50,50);

                    }
                }
        ) ;

//velocity control
        seekv.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (seekv.getProgress()<60 && seekv.getProgress()>40)
                        {
                            seekv.setProgress(50);
                            socket.emit("move1",50,50);
                        }
                        //else {socket.emit("move1",seek.getProgress(),seekv.getProgress());}
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                }
        ) ;

    }}

