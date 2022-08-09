//importing packages to handle exceptions and file handling 

import java.io.File;
import java.io.FileWriter; 
import java.io.IOException;   //used to handle exceptions which may occur during file creation and accessing

public class ass0{
    public static void main (String[] args) throws IOException
    {
        File newObj = new File("Output.csv"); //csv file will be used to store the data points
        
        //generate the file using method of File package
        if(newObj.createNewFile()){
            System.out.println("File generated: "+ newObj.getName()); //Returns name of the file created successfully.
        }

        //if file is already created
        else{
            System.out.println("File already exists"); //If the file with same name is already present
        }
        
        //writer variable will be used to write in the output file.
        FileWriter writer = new FileWriter("Output.csv");
        
        //we will be using for loop to give value to probability (prob) variable with jump of 0.02
        for(float prob=0.02f; prob<=0.98; prob+=0.02){

            //different width values for same probability value will hep to generate average time.
            //jump in width = 10 units
            //initial width = 10 units, final = 200 
            for(int width=10; width<=200; width+=10){

                int[] arr = new int[5];   //array to store time

                for(int times=0;times<5;times++){
                    
                    //giving arguments as (length, probability, width) respectively
                    //length value = 5000
                    infiltrator Attacking_country = new infiltrator(5000,prob,width);
                    
                    Attacking_country.Move();
                    arr[times] = Attacking_country.time.t;  
                }
                // System.out.println((arr[0] + arr[1] + arr[2]+arr[3]+arr[4])/5);

                //calculating average for each case
                int avg_time = (arr[0] + arr[1] + arr[2]+arr[3]+arr[4])/5;
                
                //write the average time in a file.
                writer.write(String.format("%d %f %d \n",width,prob,avg_time)); //Writing the Output file
            }
        }
        writer.close();
    }
}
