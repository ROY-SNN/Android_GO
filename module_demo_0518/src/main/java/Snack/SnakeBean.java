package Snack;

import java.util.LinkedList;
import java.util.List;

//蛇
public class SnakeBean {
    private List<PointBean> pointBeanList = new LinkedList<>();

    public List<PointBean> getPointBeanList() {
        return pointBeanList;
    }

    public void setPointBeanList(List<PointBean> pointBeanList) {
        this.pointBeanList = pointBeanList;
    }
}
