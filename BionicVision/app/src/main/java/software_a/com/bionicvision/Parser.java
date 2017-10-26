package software_a.com.bionicvision;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Parser
{
    private Context fContext;

    Parser(Context context)
    {
        this.fContext = context;
    }

    ArrayList<ArrayList<Integer>> readFile()
    {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();

        try
        {
            AssetManager assetManager = fContext.getAssets();
            InputStream input = assetManager.open("phosphenes.csv");
            InputStreamReader inputReader = new InputStreamReader(input);
            BufferedReader buffReader = new BufferedReader(inputReader);
            String line;

            while ((line = buffReader.readLine()) != null)
            {
                String[] splitLine = line.split(",");
                Integer[] coorVals = new Integer[1];
                coorVals[0] = Integer.parseInt(splitLine[0])

                result.append(line);
                result.append("\n");
            }

            buffReader.close();
        }

        catch (IOException e)
        {
            e.printStackTrace();
        }

        return result;
    }

    Integer convertAngleToCoordinate(double angle, boolean isHorizontal)
    {
        if (isHorizontal)
        {
            
        }
    }

}
