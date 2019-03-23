package Entity;

public class Cell {

    //Cell coordinates
    private int x;
    private int y;

    //Cost from the starting cell to the goal cell
    private int gCost;

    //Temprory gCost
    private int tempgCost;

    //Heuristic value
    private int hCost;

    //previous cell
    private Cell parentCell;

    //blocked or non blocked cell
    private boolean isBlocked;

    //movement cost
    private int movementCost;

    public Cell(int x, int y, boolean isBlocked, int movementCost) {
        this.x = x;
        this.y = y;
        this.isBlocked = isBlocked;
        this.movementCost = movementCost;
        this.gCost = movementCost;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getgCost() {
        return gCost;
    }

    /**
     * Setting the path gCost
     *
     * @param parentCell
     */
    public void setgCost(Cell parentCell) {
        gCost = parentCell.gCost + movementCost;
    }

    /**
     * Calculate gCost without making any changes
     * @return
     */
    public int getTempgCost(Cell parentCell){
        return tempgCost = (parentCell.getgCost() + movementCost);
    }

    public int gethCost() {
        return hCost;
    }

    public void sethCost(Cell targetCell, char method) {
        switch (method){
            case 'm':
                //if Manhattan method is selected
                hCost = Math.abs(getX() - targetCell.getX()) + Math.abs(getY() - targetCell.getY());
                break;
            case 'e':
                //if Euclidean method is selected
                hCost =(int) Math.sqrt(Math.pow((getX() - targetCell.getX()),2.0) + Math.pow((getY()-targetCell.getY()),2.0));
                break;
            case 'c':
                //if Chebyshev method is selected
                hCost = Math.max(Math.abs(getX()-targetCell.getX()), Math.abs(getY()-targetCell.getY()));
                break;
        }
    }

    public int getfCost(){
        return this.gCost + this.hCost;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public Cell getParentCell() {
        return parentCell;
    }

    public void setParentCell(Cell parentCell) {
        this.parentCell = parentCell;
    }

    public int getMovementCost() {
        return movementCost;
    }

    public void setMovementCost(int movementCost) {
        this.movementCost = movementCost;
    }
}
