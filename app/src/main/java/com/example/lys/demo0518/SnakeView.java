package com.example.lys.demo0518;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

public class SnakeView extends View implements Runnable{

    public boolean isPaused=false;
    public boolean isRunning=true;
    private int[][] snake=new int[100][2];//100节点总数，第二维下标0代表x坐标，1代表y坐标
    private int snakeNum;//已经使用的节点数
    private int direction;
    private final int direction_up=0;
    private final int direction_down=1;
    private final int direction_left=2;
    private final int direction_right=3;
    private int width=320,height=480;//view的宽高，不是全屏320*480
    private final int SNAKEWIDTH=5;//单节蛇体宽
    private int SLEEP_TIME;
    private int foodX,foodY;//食物坐标
    private boolean b=true;//控制食物闪烁
    private Random r;
    private Thread t;
    private Paint paint;
    public SnakeView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        r=new Random();
        paint=new Paint();
        init();
        t=new Thread(this);
        t.start();
    }

    public void init(){
        snakeNum=12;
        for(int i=0;i<snakeNum;i++){
            snake[i][0]=100-SNAKEWIDTH*i;
            snake[i][1]=100;
        }
        direction=direction_right;
        generateFood();
        SLEEP_TIME=200;
        b=true;
        isRunning=true;
        isPaused=false;

    }
    @Override
    public void run() {
        // TODO Auto-generated method stub
        while (isRunning) {
            long start=System.currentTimeMillis();
            if(!isPaused){
                if(isGameOver()){
                    isPaused=true;
                }else{
                    eatFood();
                    move(direction);
                    b=!b;
                }
            }

            postInvalidate();//相当于repaint
            long end=System.currentTimeMillis();
            if(end-start<SLEEP_TIME){
                try {
                    Thread.sleep(SLEEP_TIME-(end-start));
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        width=getWidth();
        height=getHeight();
        width=canvas.getWidth();
        height=canvas.getHeight();

        canvas.drawColor(0xffffff);

        paint.setColor(Color.BLUE);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        for(int i=0;i<snakeNum;i++){
            canvas.drawRect(snake[i][0], snake[i][1], snake[i][0]+SNAKEWIDTH, snake[i][1]+SNAKEWIDTH, paint);
        }
        paint.setColor(Color.WHITE);
        if(b){
            canvas.drawRect(foodX,foodY,foodX+SNAKEWIDTH,foodY+SNAKEWIDTH,paint);
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if(direction!=direction_up)
                    direction=direction_down;
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                if(direction!=direction_down)
                    direction=direction_up;
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                if(direction!=direction_right)
                    direction=direction_left;
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if(direction!=direction_left)
                    direction=direction_right;
                break;
            case KeyEvent.KEYCODE_DPAD_CENTER:
                isPaused=!isPaused;
                break;

            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // TODO Auto-generated method stub
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(event);
    }

    private void eatFood(){
        if(snake[0][0]==foodX&&snake[0][1]==foodY){
            snakeNum++;
            generateFood();
        }
    }

    private void generateFood(){
        while(true){
            //确保在屏幕内产生食物
            foodX=Math.abs(r.nextInt()%(width-SNAKEWIDTH+1))/SNAKEWIDTH*SNAKEWIDTH;
            foodY=Math.abs(r.nextInt()%(height-SNAKEWIDTH+1))/SNAKEWIDTH*SNAKEWIDTH;
            boolean b=true;
            for(int i=0;i<snakeNum;i++){//确保不在蛇体上产生食物
                if(foodX==snake[i][0]&&foodY==snake[i][1]){
                    b=false;
                    break;
                }
            }
            if(b){
                break;
            }
        }
    }

    private void move(int direction){
        for(int i=snakeNum-1;i>0;i--){
            snake[i][0]=snake[i-1][0];
            snake[i][1]=snake[i-1][1];
        }
        switch (direction) {
            case direction_up:
                snake[0][1]=snake[0][1]-SNAKEWIDTH;
                break;
            case direction_down:
                snake[0][1]=snake[0][1]+SNAKEWIDTH;
                break;
            case direction_left:
                snake[0][0]=snake[0][0]-SNAKEWIDTH;
                break;
            case direction_right:
                snake[0][0]=snake[0][0]+SNAKEWIDTH;
                break;
            default:
                break;
        }
    }

    private boolean isGameOver(){//越界判断
        if(snake[0][0]<0||snake[0][0]>width-SNAKEWIDTH||snake[0][1]<0||snake[0][1]>height-SNAKEWIDTH){
            return true;
        }
        for(int i=4;i<snakeNum;i++){//自身碰撞判断
            if(snake[0][0]==snake[i][0]&&snake[0][1]==snake[i][1]){
                return true;
            }
        }
        return false;
    }

}
