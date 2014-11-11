package andytheandroidguy.com.uncontext;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.util.Log;
import android.widget.ImageView;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringSystem;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;


public class MyActivity extends Activity {

    private WebSocketClient mWebSocketClient;

    double[] a = new double[2];
    double[] b = new double[2];
    int c;
    double d;
    double e;
    double f;

    View view1;
    View view2;
    View view3;
    View view4;
    View view5;

    ImageView randy;

    Spring spring1;
    Spring spring2;
    Spring spring3;
    Spring spring4;
    Spring spring5;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        view1 = findViewById(R.id.view);
        view2 = findViewById(R.id.view2);
        view3 = findViewById(R.id.view3);
        view4 = findViewById(R.id.view4);
        view5 = findViewById(R.id.view5);
        randy = (ImageView) findViewById(R.id.randy);

        // Create a system to run the physics loop for a set of springs.
        SpringSystem springSystem = SpringSystem.create();

        // Add a spring to the system.
        spring1 = springSystem.createSpring();
        spring2 = springSystem.createSpring();
        spring3 = springSystem.createSpring();
        spring4 = springSystem.createSpring();
        spring5 = springSystem.createSpring();

        // Add a listener to observe the motion of the spring.
        spring1.addListener(new SimpleSpringListener() {

            @Override
            public void onSpringUpdate(Spring spring) {
                // You can observe the updates in the spring
                // state by asking its current value in onSpringUpdate.
                float value = (float) spring.getCurrentValue();
                float scale = getScale(value);
                view1.setScaleX(scale);
                view1.setScaleY(scale);
                setColor(value, view1);
            }
        });

        // Add a listener to observe the motion of the spring.
        spring2.addListener(new SimpleSpringListener() {

            @Override
            public void onSpringUpdate(Spring spring) {
                // You can observe the updates in the spring
                // state by asking its current value in onSpringUpdate.
                float value = (float) spring.getCurrentValue();
                float scale = getScale(value);
                view2.setScaleX(scale);
                view2.setScaleY(scale);
                setColor(value, view2);
            }
        });

        // Add a listener to observe the motion of the spring.
        spring3.addListener(new SimpleSpringListener() {

            @Override
            public void onSpringUpdate(Spring spring) {
                // You can observe the updates in the spring
                // state by asking its current value in onSpringUpdate.
                float value = (float) spring.getCurrentValue();
                float scale = getScale(value);
                view3.setScaleX(scale);
                view3.setScaleY(scale);
                setColor(value, view3);
            }
        });

        // Add a listener to observe the motion of the spring.
        spring4.addListener(new SimpleSpringListener() {

            @Override
            public void onSpringUpdate(Spring spring) {
                // You can observe the updates in the spring
                // state by asking its current value in onSpringUpdate.
                float value = (float) spring.getCurrentValue();
                float scale = getScale(value);
                view4.setScaleX(scale);
                view4.setScaleY(scale);
                setColor(value, view4);
            }
        });

        // Add a listener to observe the motion of the spring.
        spring5.addListener(new SimpleSpringListener() {

            @Override
            public void onSpringUpdate(Spring spring) {
                // You can observe the updates in the spring
                // state by asking its current value in onSpringUpdate.
                float value = (float) spring.getCurrentValue();
                float scale = getScale(value);
                view5.setScaleX(scale);
                view5.setScaleY(scale);
                setColor(value, view5);
            }
        });

        java.lang.System.setProperty("java.net.preferIPv6Addresses", "false");
        java.lang.System.setProperty("java.net.preferIPv4Stack", "true");
        connectWebSocket();
    }

    public float getScale(float value) {
        return 1f - (value * 0.9f);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void connectWebSocket() {
        URI uri;
        try {
            uri = new URI("ws://duel.uncontext.com");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        mWebSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.i("Websocket", "Opened");
//                mWebSocketClient.send("Hello from " + Build.MANUFACTURER + " " + Build.MODEL);
            }

            @Override
            public void onMessage(String s) {
                final String message = s;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        parse(message);
                    }
                });
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                Log.i("Websocket", "Closed " + s);
            }

            @Override
            public void onError(Exception e) {
                Log.e("Websocket", "Error " + e.getMessage());
            }
        };
        mWebSocketClient.connect();
    }

    private void parse(String s) {
        try {
            JSONObject json = new JSONObject(s);
            JSONArray aArray = json.getJSONArray("a");
            a[0] = aArray.getDouble(0);
            a[1] = aArray.getDouble(1);
            JSONArray bArray = json.getJSONArray("b");
            b[0] = bArray.getDouble(0);
            b[1] = bArray.getDouble(1);
            c = json.getInt("c");
            d = json.getDouble("d");
            e = json.getDouble("e");
            f = json.getDouble("f");

            drawRebound(spring1, a[0]);
            drawRebound(spring2, b[0]);
            drawRebound(spring3, d);
            drawRebound(spring4, e);
            drawRebound(spring5, f);
            showRandy(c);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


//    private void draw() {
//        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(this, R.anim.hyper);
//        hyperspaceJumpAnimation.setRepeatCount(Animation.INFINITE);
//        view1.startAnimation(hyperspaceJumpAnimation);
//        view2.startAnimation(hyperspaceJumpAnimation);
//        view3.startAnimation(hyperspaceJumpAnimation);
//        view4.startAnimation(hyperspaceJumpAnimation);
//        view5.startAnimation(hyperspaceJumpAnimation);
//
//    }

    private void drawRebound (Spring spring, double value) {
        // Set the spring in motion; moving from 0 to 1
        spring.setEndValue(value);

    }


    private void setColor(double num, View view){
        if(num < 0) num = 0;

        int r = (int)(num * 255);
        int g = (int)(255 % (255 - (num * 255)));
        int blue = (int)(255 - (num * 255));
        if(r > 255) r = 255;
        if(r < 0) r = 0;
        if (g > 255) g = 255;
        if (g < 0) g = 0;
        if (blue > 255) blue = 255;
        if (blue < 0) blue = 0;

        Log.d("HUE", r + ", " + g + ", " + blue);

        int color = getIntFromColor(r, g, blue);

        view.setBackgroundColor(color);

    }

    public int getIntFromColor(int Red, int Green, int Blue){
        Red = (Red << 16) & 0x00FF0000; //Shift red 16-bits and mask out other stuff
        Green = (Green << 8) & 0x0000FF00; //Shift Green 8-bits and mask out other stuff
        Blue = Blue & 0x000000FF; //Mask out anything not blue.

        return 0xFF000000 | Red | Green | Blue; //0xFF000000 for 100% Alpha. Bitwise OR everything together.
    }

    public void showRandy(int lel) {
        if (lel == 0) {
            randy.setVisibility(View.GONE);
        } else {
            randy.setVisibility(View.VISIBLE);
        }
    }
}
