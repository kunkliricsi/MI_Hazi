import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

//Item storing structure
class rec implements Comparable<rec> {
    public int height;
    public int width;
    public int name;
    private long priority;
    
    rec(){
        height = 0;
        width = 0;
        name = -1;
        priority = 0;
    }
    
    rec(String h, String w, int n){
        height = Integer.parseInt(h);
        width = Integer.parseInt(w);
        name = n;
        priority = 0;
    }
    
    public void increasePriority(){
        priority++;
    }
    
    public void rotate(){
        int temp = height;
        height = width;
        width = temp;
    }
    
    @Override
    public int compareTo(rec other){
        long value1 = (this.height + this.priority) * (this.width + this.priority);
        long value2 = (other.height + other.priority) * (other.width + other.priority);
        int retValue = 0;
        
        if (value1 < value2)
            retValue = -1;
        else if (value1 > value2)
            retValue = 1;
        else if (value1 == value2)
            retValue = 0;
        
        return retValue;
    }
}

//Positions on matrix
class pos {
    public int x;
    public int y;
    
    pos(int setX, int setY){
        x = setX;
        y = setY;
    }
}

//Handles luggage matrix
class luggageHandler {
    public int width;
    public int height;
    public int[][] luggage;
    public ArrayList<rec> listOfItems;
    
    public void createLuggage(){
        luggage = new int[height][width];
    }
    
    private boolean addItem(rec r, pos p){
        int h = p.y + r.height;
        int w = p.x + r.width;
        
        if (h > height || w > width) 
            return false;
        
        for(int j = p.y; j < h; j++)
            for(int i = p.x; i < w; i++)
                if (luggage[j][i] != 0)
                    return false;
        
        for(int j = p.y; j < h; j++)
            for(int i = p.x; i < w; i++)
                luggage[j][i] = r.name;
        
        return true;
    }
    
    private void removeItem(rec r, pos p){
        for(int j = p.y; j < r.height + p.y; j++)
            for(int i = p.x; i < r.width + p.x; i++)
                luggage[j][i] = 0;
    }
    
    private ArrayList<pos> determinePositions(){
        ArrayList<pos> toReturn = new ArrayList<>();
        
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                if (luggage[i][j] == 0){
                    
                    if (i > 0 && luggage[i - 1][j] == 0)
                        continue;
                    
                    if (j > 0 && luggage[i][j - 1] == 0)
                        continue;
                    
                    toReturn.add(new pos(j, i));
                }
        
        return toReturn;
    }
    
    //This was worth 7 points
    public boolean findAllocationRecursion(ArrayList<rec> items){
        if (items.isEmpty())
            return true;
        
        Collections.sort(items, Collections.reverseOrder());
        
        ArrayList<pos> positions = determinePositions();
        ArrayList<rec> itemsClone = (ArrayList<rec>)items.clone();
        for (pos p : positions)
            for (rec i : items){
                if (addItem(i, p)){
                    itemsClone.remove(i);
                    if (findAllocationRecursion(itemsClone))
                        return true;
                    else {
                        itemsClone.add(i);
                        removeItem(i, p);
                    }
                }
                else {
                    i.rotate();
                    if (addItem(i, p)){
                        itemsClone.remove(i);
                        if (findAllocationRecursion(itemsClone))
                            return true;
                        else {
                            itemsClone.add(i);
                            removeItem(i, p);
                        }
                    }
                    i.rotate();
                }
            }
        
        return false;
    }
    
    private boolean placeItems(ArrayList<rec> items){
        if (items.isEmpty())
            return true;
        
        ArrayList<pos> positions = determinePositions();
        
        for(pos p : positions){
            for (rec i : items){
                if (addItem(i, p)){
                    items.remove(i);
                    return placeItems(items);
                }
                else {
                    i.rotate();
                    if (addItem(i, p)){
                        items.remove(i);
                        return placeItems(items);
                    }      
                }
            }
        }
        
        items.forEach((item) -> {
            for (rec i : listOfItems)
                if (i.equals(item)){
                    i.increasePriority();
                    break;
                }
        });
        
        return false;
    }
    
    public void findAllocation(){
        ArrayList<rec> listCopy;
        while(true){
            createLuggage();
            listCopy = (ArrayList<rec>)listOfItems.clone();
            
            Collections.sort(listCopy, Collections.reverseOrder());
            
            if (placeItems(listCopy))
                return;
        }
    }
    
    public void printLuggage(){
        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                System.out.print(luggage[i][j]);
                
                if (j != width - 1)
                    System.out.print("\t");
            }
            
            if (i != height - 1)
                System.out.print("\n");
        }
    }
}

public class Main {
    
    public static void main(String[] args) throws IOException {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        //Reading size of luggage
        String[] temp = br.readLine().split("\t");
        luggageHandler lh = new luggageHandler();
        lh.height = Integer.parseInt(temp[0]);
        lh.width = Integer.parseInt(temp[1]);
        
        //Reading number of items
        int numberOfItems;
        String s = br.readLine();
        numberOfItems = Integer.parseInt(s);
        
        //Reading items
        lh.listOfItems = new ArrayList();
        for (int i = 0; i < numberOfItems; i++){
            temp = br.readLine().split("\t");
            lh.listOfItems.add(new rec(temp[0], temp[1], i+1));
        }
        
        //Finding the correct allocation
        lh.findAllocation();
        
        //Printing solution
        lh.printLuggage();
    }
}
