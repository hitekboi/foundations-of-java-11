package homework.week.one.figures;

public class Triangle implements Figure {
    private int a;
    private int b;
    private int c;
    public Triangle(int a, int b, int c){
        this.a = a;
        this.b = b;
        this.c = c;
    }
    @Override
    public int area(){
        int semiperimeter = perimeter()/2;
        return (int) Math.sqrt((double) (semiperimeter)*(semiperimeter-a)*(semiperimeter-b)*(semiperimeter-c));
    }
    @Override
    public int perimeter(){
        return a+b+c;
    }
}
