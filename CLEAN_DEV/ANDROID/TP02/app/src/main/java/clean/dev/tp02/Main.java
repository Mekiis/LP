package clean.dev.tp02;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Main extends Activity {
    private static final String PROGRESS_BAR_INCREMENT = "INCREMENT";
    private static final String PROGRESS_BAR_ID = "ID";
    private static final int NB_PROGRESS_BAR = 1;
    public static final String THREAD_KEY = "THREAD_KEY";
    public static final String HANDLER_KEY = "HANDLER_KEY";
    public static final String VALUE_PROGRESS_BAR_KEY = "VALUE_PROGRESS_BAR_KEY";
    private LinearLayout root;
    ProgressBar[] p = new ProgressBar[NB_PROGRESS_BAR];

    workerThread thread;
    workerHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        root = (LinearLayout) findViewById(R.id.root);

        int valueProgressBar = 0;

        if(getLastNonConfigurationInstance() != null){
            thread = (workerThread) (((Bundle) getLastNonConfigurationInstance()).getSerializable(THREAD_KEY));
            handler = (workerHandler) (((Bundle) getLastNonConfigurationInstance()).getSerializable(HANDLER_KEY));
            valueProgressBar = ((Bundle) getLastNonConfigurationInstance()).getInt(VALUE_PROGRESS_BAR_KEY);
        }

        if(handler == null){
            handler = new workerHandler(){
                @Override
                public void handleMessage(Message msg) {
                    int progress = msg.getData().getInt(PROGRESS_BAR_INCREMENT, 1);
                    int ID = msg.getData().getInt(PROGRESS_BAR_ID, -1);
                    if(ID != -1){
                        p[ID].incrementProgressBy(progress);
                    }

                }
            };
        }

        if(thread == null){
            for(int i = 0; i < NB_PROGRESS_BAR; i++){
                p[i] = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
                root.addView(p[i]);
                thread = new workerThread(1, i, handler, this);

                Log.d(this.getClass().getName(), thread.toString());

                thread.start();
            }
        } else {
            thread.setHandler(handler);
            p[thread.getID()] = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
            p[thread.getID()].setProgress(valueProgressBar);
            root.addView(p[thread.getID()]);
        }
    }

    @Override
    public Object onRetainNonConfigurationInstance() {
        Bundle b = new Bundle();
        b.putSerializable(HANDLER_KEY, handler);
        b.putSerializable(THREAD_KEY, thread);
        b.putInt(VALUE_PROGRESS_BAR_KEY, p[thread.getID()].getProgress());
        return b;
    }

    public class workerHandler extends Handler implements Serializable{

    }

    public class workerThread extends Thread implements Serializable {
        int pas = 1;
        int ID = -1;
        Handler handler;
        Context context;

        public workerThread(int pas, int ID, Handler handler, Context context){
            this.pas = pas;
            this.ID = ID;
            this.handler = handler;
            this.context = context;
        }

        public void setHandler(Handler handler){
            this.handler = handler;
        }

        public int getID(){
            return this.ID;
        }

        @Override
        public void run() {
            for(int i = 0; i < 100; i++){
                Message myMessage = this.handler.obtainMessage();
                Bundle messageBundle = new Bundle();
                messageBundle.putInt(PROGRESS_BAR_INCREMENT, pas);
                messageBundle.putInt(PROGRESS_BAR_ID, ID);
                myMessage.setData(messageBundle);
                this.handler.sendMessage(myMessage);
                Random r = new Random();
                try {
                    Thread.sleep(r.nextInt(2000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

}
