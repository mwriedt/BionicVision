package software_a.com.bionicvision;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

//.===========================================================================.
//| PARSER CLASS                                                              |
//|---------------------------------------------------------------------------|
//| - Context: mContext                                                       |
//|---------------------------------------------------------------------------|
//| Purpose: Used to read in a phosphene file from the assets folder. The     |
//| file's values determine the position of phosphenes within the             |
//| CameraActivity. Uses the application context to pull the correct assets.  |                                                          |
//'==========================================================================='

class Parser
{
    private Context fContext;

    // gather information from the current activity through a context
    Parser(Context context)
    {
        this.fContext = context;
    }

    ArrayList<ArrayList<Integer>> readFile(String fileName)
    {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();

        try
        {
            // open the file from the assets directory
            // set a buffered input stream to read each line of the input file
            // each line is split and the angles are converted
            // coordinates are stored in a nested ArrayList for easy access
            AssetManager assetManager = fContext.getAssets();
            InputStream input = assetManager.open(fileName);
            InputStreamReader inputReader = new InputStreamReader(input);
            BufferedReader buffReader = new BufferedReader(inputReader);
            String line;

            while ((line = buffReader.readLine()) != null)
            {
                String[] splitLine = line.split(",");
                ArrayList<Integer> phosVals = new ArrayList<>(1);

                phosVals.add(convertAngleToCoordinates(Double.parseDouble(splitLine[0]), true));
                phosVals.add(convertAngleToCoordinates(Double.parseDouble(splitLine[1]), false));

                result.add(phosVals);
            }

            buffReader.close();
        }
        catch (IOException e)
        {
            Log.d("TAG", "Exception Thrown: " + e + "\n");
            e.printStackTrace();
        }

        return result;
    }

    // formula to convert a visual angle to screen coordinates
    // horizontal ratio is different to verticle ratio
    private int convertAngleToCoordinates(double angle, boolean isHorizontal)
    {
        if (isHorizontal)
        {
            return (int) Math.ceil(((2 * + Math.tan(angle)) * 192.913) / 8);
        }
        else
        {
            return (int) Math.ceil(((2 * + Math.tan(angle)) * 192.913) / 6);
        }
    }
}
