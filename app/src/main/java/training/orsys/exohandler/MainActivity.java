package training.orsys.exohandler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) { // thread UI, main thread
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ProgressBar pb1 = findViewById(R.id.progressBar1);
        pb1.setVisibility(View.INVISIBLE);

        final TextView tv1 = findViewById(R.id.champ_compteur);
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);

                tv1.setText(String.valueOf(msg.what));

                if(msg.what == 9) {
                    ProgressBar pb1 = findViewById(R.id.progressBar1);
                    pb1.setVisibility(View.INVISIBLE);
                    Button b1 = findViewById(R.id.button_start);
                    b1.setEnabled(true);
                }
            }
        };
    }

    public void clickButtonStart( View v ) { // thread UI
        ProgressBar pb1 = findViewById(R.id.progressBar1);
        pb1.setVisibility(View.VISIBLE);

        Button b1 = findViewById(R.id.button_start);
        b1.setEnabled(false);

        new ThreadCompteur().start();
    }

    class ThreadCompteur extends java.lang.Thread { // worker thread
        public void run() { // debut du thread
            for( int t = 0; t < 10; t++ ) {
                //tv1.setText(String.valueOf(t+1));
                handler.sendEmptyMessage(t);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

       /*ProgressBar pb1 = findViewById(R.id.progressBar1);
        pb1.setVisibility(View.VISIBLE);

        TextView tv1 = findViewById(R.id.champ_compteur);
        for( int t = 0; t < 10; t++ ) {
            tv1.setText(String.valueOf(t+1));
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        pb1.setVisibility(View.INVISIBLE);*/
