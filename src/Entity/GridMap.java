package Entity;


import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 *@author THIVAGAR MHENDRAN 2016349 w1654202
 */

public class GridMap {

    /**
     * 2D array to store all  the cells for path finding
     */
    private Cell[][] cells;

    /**
     * A variable to assign the array length
     */
    private int mapLength;

    /**
     * Creates a map with blocked cells
     *
     * @param grid_map
     */
    public GridMap(int[][] grid_map) {
        //Getting 2D array length to
        mapLength = grid_map.length;

        cells = new Cell[mapLength][mapLength];

        for (int x = 0; x < mapLength; x++){
            for (int y = 0; y < mapLength; y++){
                cells[x][y] = new Cell(x, y, grid_map[x][y] == 5, grid_map[x][y]);
            }
        }
    }



    /**
     * Returns the specific current Cell
     *
     * @param x
     * @param y
     * @return
     */
    public Cell getCurrentNode(int y, int x){
        if (x >= 0 && x < mapLength && y >= 0 && y < mapLength)
        {
            return cells[x][y];
        }
        else
        {
            return null;
        }
    }

    /**
     * Returns a list of adjacent cells(Cells which are surrounded
     * to the current cell)
     *
     * @param closedList
     * @param cell
     * @return the adjacent cells list
     */
    public List<Cell> getAdjCells(PriorityQueue<Cell> closedList, Cell cell){

        List<Cell> adjCells = new LinkedList<Cell>();
        //Assign coordinates of the current cell
        int x = cell.getX();
        int y = cell.getY();

        Cell tCell;

        if (x > 0){
            //checking the left cell
            tCell = getCurrentNode(y,x-1);
            if (tCell != null && !tCell.isBlocked() && !closedList.contains(tCell)){
                adjCells.add(tCell);
            }
        }

        if (x < mapLength){
            //checking the right cell
            tCell = getCurrentNode(y,x+1);
            if (tCell != null && !tCell.isBlocked() && !closedList.contains(tCell)){
                adjCells.add(tCell);
            }
        }

        if (y < mapLength){
            //checking the bottom cell
            tCell = getCurrentNode(y+1, x);
            if (tCell != null && !tCell.isBlocked() && !closedList.contains(tCell)){
                adjCells.add(tCell);
            }
        }

        if (y > 0){
            //checking the top cell
            tCell = getCurrentNode(y-1, x);
            if (tCell != null && !tCell.isBlocked() && !closedList.contains(tCell)){
                adjCells.add(tCell);
            }
        }

        if (x < mapLength && y > 0){
            //checking the top-right cell
            tCell = getCurrentNode(y-1, x+1);
            if (tCell != null && !tCell.isBlocked() && !closedList.contains(tCell)){
                adjCells.add(tCell);
            }
        }

        if (x > 0 && y > 0){
            //checking the top-left cell
            tCell = getCurrentNode(y-1,x-1);
            if (tCell != null && !tCell.isBlocked() && !closedList.contains(tCell)){
                adjCells.add(tCell);
            }
        }

        if (x > 0 && y < mapLength){
            //checking the bottom-left cell
            tCell = getCurrentNode(y+1,x-1);
            if (tCell != null && !tCell.isBlocked() && !closedList.contains(tCell)){
                adjCells.add(tCell);
            }
        }

        if (x > 0 && y < mapLength){
            //checking the bottom-right cell
            tCell = getCurrentNode(y+1,x+1);
            if (tCell != null && !tCell.isBlocked() && !closedList.contains(tCell)){
                adjCells.add(tCell);
            }
        }
        return adjCells;
    }

    public PriorityQueue<Cell> pathFinder(int sX, int sY, int eX, int eY, char mChar){

        //If the start and end positions are same
        if (sX == eX && sY == eY){
            //it'll return an empty list
            return new PriorityQueue<Cell>();
        }

        // List of cells to be evaluated
        PriorityQueue<Cell> openList = new PriorityQueue<>(new Comparator<Object>() {

            @Override
            public int compare(Object o1, Object o2) {
                Cell c1 = (Cell) o1;
                Cell c2 = (Cell) o2;
                if (c1.getfCost() < c2.getfCost()) {
                    return -1; // For priority
                } else if (c1.getfCost() > c2.getfCost()) {
                    return 1;
                } else {
                    return 0; // if equal fCosts FIFO
                }

            }
        });
        //List of cells that have been already evaluated
        PriorityQueue<Cell> closedList = new PriorityQueue<>(new Comparator<Cell>() {
            @Override
            public int compare(Cell obj1, Cell obj2) {
                Cell cell1 = (Cell) obj1;
                Cell cell2 = (Cell) obj2;
                if (cell1.getfCost() < cell2.getfCost()) {
                    return -1;
                } else if (cell1.getfCost() > cell2.getfCost()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });

        //Insert starting cell to open list
        openList.add(cells[sX][sY]);

        /**
         * This will loop until the current position is equal
         * to the target position
         */
        while (true){

            //Removes the lowest f score cell
            Cell currentCell = openList.poll();
            //Adds the cell to the closed list
            closedList.add(currentCell);

            //if the currentCell is equals to the target node loop will end
            if ((currentCell.getX() == eX) && (currentCell.getY() == eY)){
                PriorityQueue<Cell> pathCells = new PriorityQueue<>(new Comparator<Cell>() {
                    @Override
                    public int compare(Cell obj1, Cell obj2) {
                        Cell cell1 = (Cell) obj1;
                        Cell cell2 = (Cell) obj2;
                        if (cell1.getfCost() < cell2.getfCost()) {
                            return -1;
                        } else if (cell1.getfCost() > cell2.getfCost()) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }

                });

                //LinkedList<Cell> path = new LinkedList<Cell>();

                Cell cellNode = currentCell;
                boolean over = false;
                while (!over) {
                    pathCells.add(cellNode);
                    cellNode = cellNode.getParentCell();
                    if (cellNode.equals(cells[sX][sY])) {
                        over = true;
                    }
                }


                return pathCells;
            }

            List<Cell> adjCells = getAdjCells(closedList, currentCell);
            for (Cell adjCell : adjCells){
                //Check if
                if (adjCell.getgCost() > adjCell.getTempgCost(currentCell)){
                    //Set the parent cell
                    adjCell.setParentCell(currentCell);
                    //Set the gCost
                    adjCell.setgCost(currentCell);
                //Check if
                }else if (!openList.contains(adjCell)){
                    //Set the current node as parent
                    adjCell.setParentCell(currentCell);
                    //Set H costs of this cell (Estimated costs to goal)
                    adjCell.sethCost(cells[eX][eY], mChar);
                    //Set G costs of this current cell
                    adjCell.setgCost(currentCell);
                    //Add the cell to the open list
                    openList.add(adjCell);
                }
            }

            if (openList.isEmpty()){
                return new PriorityQueue<>();
            }

        }

    }


    public Cell[][] getCells() {
        return cells;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    public int getMapLength() {
        return mapLength;
    }

    public void setMapLength(int mapLength) {
        this.mapLength = mapLength;
    }
}
