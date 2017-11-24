package software_a.com.bionicvision;

import org.opencv.core.Mat;

//.===========================================================================.
//|   HOW TO SETUP AN ALGORITHM                                               |
//|---------------------------------------------------------------------------|
//|   1. Create a new class that implements 'Algorithm'.                      |
//|   2. Override the process method with necessary formulas.                 |
//|   3. Add a new case to the 'AlgorithmSwitch'.                             |
//|   4. Define a phospheneRendering in onCameraFrame() within                |
//|      CameraActivity.                                                      |
//'==========================================================================='

import java.util.List;

//.===========================================================================.
//|   HOW TO SETUP AN ALGORITHM                                               |
//|---------------------------------------------------------------------------|
//|   1. Create a new class that implements 'Algorithm'.                      |
//|   2. Override the process method with necessary formulas.                 |
//|   3. Add a new case to the 'AlgorithmSwitch'.                             |
//|   4. Define a phospheneRendering in onCameraFrame() within                |
//|      CameraActivity.                                                      |
//|   5. Add the Algorithm name to the string aray in res->strings.xml.       |
//|      It's at the top of the file, defined by the name "algorithm-array".  |
//'==========================================================================='

public abstract class Algorithm
{
    private String name = "";

    Algorithm() {}

    public String getName() {return name;}
    public void setName(String n) {name = n;}

    abstract List<Phosphene> process(Mat frame, List<Phosphene> phospheneGrid, int maxGrid);
}
