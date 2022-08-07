
//packages to create a file to store output, which will be used to generate a graph plot.

import  java.io.File;
import java.io.FileWriter;
import java.io.IOException;   //used to handle exception which may occur during file creation and accessing.
import java.util.Random;      //package to generate random numbers which will be used to assign probability values
public class Land {
    public static void main(String[] args) throws IOException {
            File newObj = new File("data.csv"); //csv file will be used to store the data points
            //the file will be stored in same project as no path is specified.
            if(newObj.createNewFile()){
                System.out.println("File generated: "+ newObj.getName()); //return name of file is created successfully.
            }
            else{
                System.out.println("File already exists"); //these condition will run when the file with same name is already present.
            }
            FileWriter writer = new FileWriter("data.csv");
            double prob = 0;  //variable to assign probability of Switch ON/OFF to sensors.
            int width = 0;    //variable to set width of border.
    }
}
