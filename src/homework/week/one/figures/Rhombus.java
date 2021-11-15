package homework.week.one.figures;

import java.util.Optional;

public class Rhombus implements Figure{
    private int diagonalOne;
    private int diagonalTwo;
    private int side;
    //Why java doesnt support default values
    public Rhombus(int diagonalOne, int diagonalTwo, int side){
        this.diagonalOne = diagonalOne;
        this.diagonalTwo = diagonalTwo;
        this.side = side;
    }
    @Override
    public int area(){
        return (int)((diagonalOne*diagonalTwo)/2);
    }

    @Override
    public int perimeter() {
        return 4*side;
    }
}

