package software_a.com.bionicvision;
/**
 * Created by Mitchell on 10/10/2017.
 */
import java.util.ArrayList;
import java.util.List;

public class PhospheneMap {
    //public ArrayList<ArrayList<Phosphene>> phospheneBuilder;
    List<List<Phosphene>> phospheneBuilder;// = new List<List<Phosphene>>();
    List<Phosphene> temp;
    int phosCount;
    int layer;
    PhospheneMap(int numOfPhos)
    {
        phosCount = numOfPhos - 1; //Take one away to include centre point
        phospheneBuilder = new ArrayList<>();
        temp = new ArrayList<>();
        for (int i = 0; i < 17; i++) //Max size of 17 by 17
        {
            temp = new ArrayList<>();
            for (int j = 0; j < 17; j++)
            {
                layer = getLayer(i,j); //Get the layer that this location should be at
                temp.add(new Phosphene(i,j, layer)); //Create the phosphene at that location
            }
            phospheneBuilder.add(temp); //Add the list of new phosphenes
        }
        CreateCP(); //Create the centre point in the map

        BuildMap(); //Build the map
    }

    private void CreateCP()
    {
        phospheneBuilder.get(8).get(8).isCP = Boolean.TRUE;
        phospheneBuilder.get(8).get(8).touchingCP = Boolean.TRUE;
        phospheneBuilder.get(8).get(8).alive = Boolean.TRUE;
        phospheneBuilder.get(8).get(8).layer = 0;
    }

    public List<Phosphene> getPhosphenes()
    {
        List<Phosphene> result = new ArrayList<>();
        for (List<Phosphene> j: phospheneBuilder)
        {
            for (Phosphene p: j)
            {
                if (p.alive)
                {
                    result.add(p);
                }
            }
        }
        return result;
    }

    private int getLayer(int y, int x)
    {
        if (y >= 7 && y <= 9 && x >= 7 && x <= 9) //layer 1
        {
            layer = 1;
        }
        else if (y >= 6 && y <= 10 && x >= 6 && x <= 10) //layer 2
        {
            layer = 2;
        }
        else if (y >= 5 && y <= 11 && x >= 5 && x <= 11) //layer 3
        {
            layer = 3;
        }
        else if (y >= 4 && y <= 12 && x >= 4 && x <= 12) //layer 4
        {
            layer = 4;
        }
        else if (y >= 3 && y <= 13 && x >= 3 && x <= 13) //layer 5
        {
            layer = 5;
        }
        else if (y >= 2 && y <= 14 && x >= 2 && x <= 14) //layer 6
        {
            layer = 6;
        }
        else if (y >= 1 && y <= 15 && x >= 1 && x <= 15) //layer 7
        {
            layer = 7;
        }
        else if (y >= 0 && y <= 16 && x >= 0 && x <= 16) //layer 8
        {
            layer = 8;
        }
        return layer;
    }


    private void BuildMap()
    {
        startloop:
        for (int i = 0; i < phosCount; i++) //For each phosphene we need to create
        {
            for(int l = 1; l < 8; l++) //For every layer
            {
                for (List<Phosphene> m :phospheneBuilder) //For every list
                {
                    for (Phosphene p: m ) //For every phosphene is that list
                    {
                        if (p.isCP || p.alive) //If the phosphene is already alive in this position, skip
                            continue;

                        if (CheckBorders(p.xLoc, p.yLoc)) //If the location of the phosphene is acceptable
                        {
                            if (p.layer == l) //If the current phosphene is on the chosen layer
                            {
                                p.alive = Boolean.TRUE; //This phos is now alive
                                p.touchingCP = Boolean.TRUE; //This phos is now touching the centre point
                                continue startloop; //Jump back up to the next phosphene (maybe a goto statement?)
                            }
                        }
                    }
                }
            }
        }
    }

    private Boolean CheckBorders(int x, int y) //Checks if a phosphene placed at loction x, y will have contact with another phosphene that is touching the centre point
    {
        Boolean result = Boolean.FALSE;
        if (x > 0) //If x is not 0
        {
            if (x >= 16) //If x is 16
            {
                if (y == 0) //If y is 0 and x is 16
                {
                    if (phospheneBuilder.get(x - 1).get(y + 1).touchingCP) {
                        result = Boolean.TRUE;
                    }
                }
                else if (y >= 16) //If y is 16 and x is 16
                {
                    if (phospheneBuilder.get(x - 1).get(y - 1).touchingCP) {
                        result = Boolean.TRUE;
                    }
                }
                else //From 1,16 to 15,16
                {
                    if (phospheneBuilder.get(x - 1).get(y + 1).touchingCP || phospheneBuilder.get(x - 1).get(y - 1).touchingCP)
                    {
                        result = Boolean.TRUE;
                    }
                }
            }
            else
            {
                if (y == 0) // if y is 0  and x is not 16 or 0
                {
                    if (phospheneBuilder.get(x - 1).get(y + 1).touchingCP || phospheneBuilder.get(x + 1).get(y + 1).touchingCP)
                    {
                        result = Boolean.TRUE;
                    }
                }
                else if (y >= 16) //if y is 16 and x is not 16 or 0
                {
                    if (phospheneBuilder.get(x - 1).get(y - 1).touchingCP || phospheneBuilder.get(x + 1).get(y - 1).touchingCP)
                    {
                        result = Boolean.TRUE;
                    }
                }
                else //if x is not 0 or 16 and y is not 0 or 16
                {
                    if (phospheneBuilder.get(x - 1).get(y - 1).touchingCP || phospheneBuilder.get(x - 1).get(y + 1).touchingCP
                            ||phospheneBuilder.get(x + 1).get(y - 1).touchingCP || phospheneBuilder.get(x + 1).get(y + 1).touchingCP )
                    {
                        result = Boolean.TRUE;
                    }
                }

            }
        }
        else if (x == 0)//If x is 0
        {
            if (y == 0) //If y is 0 and x is 0
            {
                if (phospheneBuilder.get(x + 1).get(y + 1).touchingCP) {
                    result = Boolean.TRUE;
                }
            }
            else if (y >= 16) //If y is 16 and x is 0
            {
                if (phospheneBuilder.get(x + 1).get(y - 1).touchingCP) {
                    result = Boolean.TRUE;
                }
            }
            else //From 1,0 to 15,0
            {
                if (phospheneBuilder.get(x + 1).get(y - 1).touchingCP || phospheneBuilder.get(x + 1).get(y + 1).touchingCP)
                {
                    result = Boolean.TRUE;
                }
            }
        }

        if (result)
        {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}