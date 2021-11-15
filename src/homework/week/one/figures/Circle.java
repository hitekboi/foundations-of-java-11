package homework.week.one.figures;

public class Circle implements Figure{
    private int radius;
    public Circle(int radius){
        this.radius = radius;
    }
    @Override
    public int area(){
        return (int)(Math.PI * Math.pow(radius, 2));
    }
    @Override
    public int perimeter(){
        return (int)(Math.PI*radius*2);
    }
}
