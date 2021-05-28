package Snack;
//游戏背景
public class GridBean {
    private int   height = 1920; //手机高
    private int   width = 1080; //手机宽

    private int offset = 90 ;//偏移量，就是间距  上 左 右 间距一样

    private int gridSize = 20;//每行格子的数量
    private int lineLength = 800;//线的长度
    private int gridWidth = 40;//格子宽

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getGridSize() {
        return gridSize;
    }

    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }

    public int getLineLength() {
        return lineLength;
    }

    public void setLineLength(int lineLength) {
        this.lineLength = lineLength;
    }

    public int getGridWidth() {
        return gridWidth;
    }

    public void setGridWidth(int gridWidth) {
        this.gridWidth = gridWidth;
    }
}
