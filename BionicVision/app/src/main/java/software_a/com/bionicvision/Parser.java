package software_a.com.bionicvision;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Parser
{
    private Context fContext;

    Parser(Context context)
    {
        this.fContext = context;
    }

    StringBuilder readFile()
    {
        StringBuilder result = new StringBuilder();

        try
        {
            AssetManager assetManager = fContext.getAssets();
            InputStream input = assetManager.open("phosphenes.csv");
            InputStreamReader inputReader = new InputStreamReader(input);
            BufferedReader buffReader = new BufferedReader(inputReader);
            String line;

            while ((line = buffReader.readLine()) != null)
            {
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
}
