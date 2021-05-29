package Snack;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

public class GameView extends View {
    private GridBean gridBean;
    private SnakeBean snakeBean;
    private Paint mPaint ;
    PointBean food;
    int x;
    int y;
    Control control;

    private void init(){
        x = (int) (Math.random()*10);
        y = (int) (Math.random()*10);
        control = Control.UP;
        gridBean = new GridBean();   // 初始化背景
        snakeBean = new SnakeBean(); // 初始化蛇
        PointBean pointBean = new PointBean(gridBean.getGridSize()/2,gridBean.getGridSize()/2);
        snakeBean.getPointBeanList().add(pointBean);
        snakeBean.getPointBeanList().add(pointBean);
        snakeBean.getPointBeanList().add(pointBean);
        mPaint = new Paint();
    }

    public GameView(Context context) {
        super(context);
        init();
    }

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (gridBean != null){
            mPaint.setColor(Color.RED);
            drawGrid(canvas);
        }
        if (snakeBean != null){
            mPaint.setColor(Color.GREEN);
            drawSnake(canvas);
        }
        mPaint.setColor(Color.BLUE);
        food(canvas);
        if (isEat(snakeBean)){
            x = (int) (Math.random()*10);
            y = (int) (Math.random()*10);
            refreshView(true);
        }
    }

    // 画蛇
    private void drawSnake(Canvas canvas){
        List<PointBean> list = snakeBean.getPointBeanList();
        for (PointBean pointBean : list){
            int startX = gridBean.getOffset() + gridBean.getGridWidth() * pointBean.getX();
            int stopX = startX + gridBean.getGridWidth();
            int startY = gridBean.getOffset() + gridBean.getGridWidth() * pointBean.getY();
            int stopY = startY + +gridBean.getGridWidth();
            canvas.drawRect(startX, startY, stopX, stopY, mPaint);
        }
    }

    // 画格子
    private void drawGrid(Canvas canvas){
        // 画竖线
        for (int i = 0;i <= gridBean.getGridSize();i++){
            int startX = gridBean.getOffset()+gridBean.getGridWidth()*i;
            int stopX = startX;
            int startY = gridBean.getOffset();
            int stopY = startY + gridBean.getLineLength();
            canvas.drawLine(startX,startY,stopX,stopY,mPaint);
        }
        // 画横线
        for (int j = 0;j<=gridBean.getGridSize();j++){
            int startX = gridBean.getOffset();
            int stopX = gridBean.getOffset()+gridBean.getLineLength();
            int startY = gridBean.getOffset()+gridBean.getGridWidth()*j;
            int stopY = startY;
            canvas.drawLine(startX,startY,stopX,stopY,mPaint);
        }
    }

    // 蛇移动实现
    public void refreshView(boolean isAdd){
        List<PointBean> pointBeanList = snakeBean.getPointBeanList();
        PointBean pointBean = pointBeanList.get(0);
        PointBean pointBean1 = null;
        if (control == Control.LEFT){
            pointBean1 = new PointBean(pointBean.getX() - 1,pointBean.getY());
        }else if (control == Control.RIGHT){
            pointBean1 = new PointBean(pointBean.getX() + 1,pointBean.getY());
        }else if (control == Control.UP){
            pointBean1 = new PointBean(pointBean.getX(),pointBean.getY()-1);
        }else if (control == Control.DOWN){
            pointBean1 = new PointBean(pointBean.getX(),pointBean.getY()+1);
        }
        if (pointBean1 != null){
            pointBeanList.add(0,pointBean1);
            if (!isAdd){
                pointBeanList.remove(pointBeanList.get(pointBeanList.size()-1));
            }
        }
        if (isDead(pointBeanList)){
            init();
        }else {
            postInvalidate();
        }
    }

    // 是否死亡
    public boolean isDead(List<PointBean> pointBeanList){
        PointBean pointBean = pointBeanList.get(0);
        if (pointBean.getY()==-1&&control== Control.UP){
            return true;
        }else if (pointBean.getX()==-1&&control== Control.LEFT){
            return true;
        }else if (pointBean.getX()==gridBean.getGridSize()&&control== Control.RIGHT){
            return true;
        }else if (pointBean.getY()==gridBean.getGridSize()&&control== Control.DOWN){
            return true;
        }
        return false;
    }

    // 画食物
    public void food(Canvas canvas){
        food = new PointBean(x,y);
        int startX = gridBean.getOffset() + gridBean.getGridWidth() * food.getX();
        int stopX = startX + gridBean.getGridWidth();
        int startY = gridBean.getOffset() + gridBean.getGridWidth() * food.getY();
        int stopY = startY + +gridBean.getGridWidth();
        canvas.drawRect(startX, startY, stopX, stopY, mPaint);
    }

    // 吃食物
    boolean isEat(SnakeBean list){
        int eatX = list.getPointBeanList().get(0).getX();
        int eatY = list.getPointBeanList().get(0).getY();
        if (eatX==x && eatY==y){
            return true;
        }
        return false;
    }
}
