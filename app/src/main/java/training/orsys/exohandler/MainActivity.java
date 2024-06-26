package training.orsys.exohandler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.FragmentActivity

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Handler handler = null;
    private TextView tv1 = null;
    private ProgressBar pb1 = null;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) { // thread UI, main thread
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pb1 = findViewById(R.id.progressBar1);
        pb1.setVisibility(View.INVISIBLE);
        tv1 = findViewById(R.id.champ_compteur);

        /*Button btn = findViewById(R.id.button_start);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

        handler = new Handler(Looper.myLooper()) { // code inner
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                tv1.setText(String.valueOf(msg.what));
                if(msg.what == 9) {
                    pb1.setVisibility(View.INVISIBLE);
                    Button b1 = findViewById(R.id.button_start);
                    b1.setEnabled(true);
                }
            }
        };
    }

    // inner class
    class ThreadCompteur extends java.lang.Thread { // worker thread
        public void run() { // debut du thread
            for( int t = 0; t < 10; t++ ) {
                //tv1.setText(String.valueOf(t+1)); // KO
                handler.sendEmptyMessage(t); // img, java.lang
                //handler.sendMessage(bmp)
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /*    */
    public void clickButtonStart( View v ) { // thread UI, main thread
        pb1.setVisibility(View.VISIBLE);
        Button b1 = findViewById(R.id.button_start);
        b1.setEnabled(false);

        /*tv1 = findViewById(R.id.champ_compteur);
        for( int t = 0; t < 10; t++ ) {
            //tv1.setText(String.valueOf(t+1));
            //handler.sendEmptyMessage(t);
            try {
                Thread.sleep(1000);
                tv1.setText(String.valueOf(t));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        pb1.setVisibility(View.INVISIBLE);
        b1.setEnabled(true);*/
        new ThreadCompteur().start();
    }


}
