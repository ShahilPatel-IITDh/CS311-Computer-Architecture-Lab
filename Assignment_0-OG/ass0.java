// import java.util.Scanner;
import java.io.File;
import java.io.FileWriter; 
import java.io.IOException;   //used to handle exception which may occur during file creation and accessing

public class ass0{
    public static void main (String[] args) throws IOException
    {
        // System.out.println("Hello Java");

        File newObj = new File("Output.csv"); //csv file will be used to store the data points
            if(newObj.createNewFile()){
                System.out.println("File generated: "+ newObj.getName()); //Returns name of the file created successfully.
            }
            else{
                System.out.println("File already exists"); //If the file with same name is already present
            }
            FileWriter writer = new FileWriter("Output.csv");
        for(float prob=0.02f;prob<0.98;prob+=0.02)
        {
            for(int width=10;width<200;width+=10)
            {
                int[] arr = new int[5];
                for(int times=0;times<5;times++)
                {
                    infiltrator Attacking_country = new infiltrator(5000,prob,width);
                    Attacking_country.Move();
                    arr[times] = Attacking_country.time.t;  
                }
                // System.out.println((arr[0] + arr[1] + arr[2]+arr[3]+arr[4])/5);
                int avg_time = (arr[0] + arr[1] + arr[2]+arr[3]+arr[4])/5;

                writer.write(String.format("%d %f %d \n",width,prob,avg_time)); //Writing the Output file
            }
        }
        writer.close();
    }
}
