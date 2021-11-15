package homework.week.one.tictactoe;

/**
 * Board Cell manager
 */
public class BoardCell {
    private Checker checker;

    private  BoardCell() {}

    public BoardCell(Checker checker) {
        this.setChecker(checker);
    }

    public void setChecker(Checker checker) {
        this.checker = checker;
    }

    public char getChecker() {
        return checker.getCheckerSign();
    }
}
