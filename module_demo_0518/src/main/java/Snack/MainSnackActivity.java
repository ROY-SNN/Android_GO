package Snack;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.example.module_demo_0518.R;

public class MainSnackActivity extends AppCompatActivity {
    public static final int WHAT_REFRESH = 200;
    private GameView gameView;
    protected static final float FLIP_DISTANCE = 50;
    GestureDetector mDetector;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(WHAT_REFRESH == msg.what){
                gameView.refreshView(false);
                sendControlMessage();
            }
        }
    };

    private void sendControlMessage(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessage(WHAT_REFRESH);
            }
        },300);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snack_main);


        gameView = new GameView(this);
        setContentView(gameView);
        sendControlMessage();
        mDetector = new GestureDetector(this, new GestureDetector.OnGestureListener() {

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                // TODO Auto-generated method stub

            }

            /**
             * e1 The first down motion event that started the fling. e2 The
             * move motion event that triggered the current onFling.
             */
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (e1.getX() - e2.getX() > FLIP_DISTANCE) {
                    gameView.control=Control.LEFT;
                    return true;
                }
                if (e2.getX() - e1.getX() > FLIP_DISTANCE) {
                    gameView.control=Control.RIGHT;
                    return true;
                }
                if (e1.getY() - e2.getY() > FLIP_DISTANCE) {
                    gameView.control=Control.UP;
                    return true;
                }
                if (e2.getY() - e1.getY() > FLIP_DISTANCE) {
                    gameView.control=Control.DOWN;
                    return true;
                }
                return false;
            }

            @Override
            public boolean onDown(MotionEvent e) {
                // TODO Auto-generated method stub
                return false;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mDetector.onTouchEvent(event);
    }
}
