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
            for(int width=3;width<60;width+=3)
            {
                int[] arr = new int[20];
                int avg_time=0;
                for(int times=0;times<20;times++)
                {
                    infiltrator Attacking_country = new infiltrator(5000,prob,width);
                    Attacking_country.Move();
                    arr[times] = Attacking_country.time.t;
                    avg_time +=arr[times];  
                }
                avg_time/=20;
                
                // System.out.println((arr[0] + arr[1] + arr[2]+arr[3]+arr[4])/5);
                // int avg_time = (arr[0] + arr[1] + arr[2]+arr[3]+arr[4])/5;

                writer.write(String.format("%d %f %d \n",width,prob,avg_time)); //Writing the Output file
            }
        }
        writer.close();
    }
}