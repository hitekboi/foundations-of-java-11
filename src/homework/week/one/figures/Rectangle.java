package homework.week.one.figures;

public class Rectangle implements Figure{

    private int width;
    private int height;
    public Rectangle(int width,int height){
        this.width = width;
        this.height = height;
    }

    @Override
    public int area() {
        return width * height;
    }

    @Override
    public int perimeter() {
        return width * 2 + height * 2;
    }
}
