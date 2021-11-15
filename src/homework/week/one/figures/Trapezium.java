package homework.week.one.figures;

public class Trapezium implements Figure{
    private int sideOne;
    private int sideTwo;
    private int sideThree;
    private int sideFour;
    private int height;
    //Why java doesnt support default values
    public Trapezium(int sideOne,int sideTwo,int sideThree,int sideFour,int height){
        this.sideOne=sideOne;
        this.sideTwo=sideTwo;
        this.sideThree=sideThree;
        this.sideFour=sideFour;
        this.height=height;
    }
    @Override
    public int area(){
        return ((sideOne+sideTwo)/2)*height;
    }
    @Override
    public int perimeter(){
        return sideOne+sideTwo+sideThree+sideFour;
    }
}
