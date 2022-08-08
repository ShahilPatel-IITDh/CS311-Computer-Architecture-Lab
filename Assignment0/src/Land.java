
//packages to create a file to store output, which will be used to generate output file to plot a graph plot.

//packages to use current time and duration method for tie
import java.time.LocalTime;
import java.time.Duration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;   //used to handle exception which may occur during file creation and accessing.    //package to generate random numbers which will be used to assign probability values
public class Land {
    public static void main(String[] args) throws IOException {

            File newObj = new File("output.csv"); //csv file will be used to store the data points
            //the file will be stored in same project as no path is specified.
            if(newObj.createNewFile()){
                System.out.println("File generated: "+ newObj.getName()); //return name of file is created successfully.
            }
            else{
                System.out.println("File already exists"); //these condition will run when the file with same name is already present.
            }
            FileWriter writer = new FileWriter("output.csv");
            
            for(double probability=0;probability<=0.99;probability+=0.03){

                for(int width=10;width<=300;width+=10){

                    Boundary boundary = new Boundary(width,1000);
                    Sensor sensor = new Sensor(probability);  //giving probability as input ro Sensor class which will decide whether the sensor is ON or OFF
                    Invader inv = new Invader(1); //the initial vertical step is 0 initially.
                    Time time = new Time(0);  //initial time is 0.
                    
                    LocalTime start = LocalTime.now(); //the current time while execution
                    //while the invader has not yet crossed the boundary
                    while ((inv.getVertical_step()!= boundary.getWidth()+1)){
                        
                        int duration = 0;
                        //duration is used to calcuate the duration of calculating the time to cross the border.

                        if (time.getTime()%10==0) {
                            //sensor will take decision at every 10 seconds.
                            sensor.takeDecision();

                            if (sensor.getSensorA() == 0) {
                                if(inv.getVertical_step()== boundary.getWidth()){
                                    inv.setVertical_step(inv.getVertical_step() + 1);
                                    //take 1 step in forward direction

                                    time.setTime(time.getTime()+10);
                                    break;
                                }

                                if (sensor.getSensorB() == 0 || sensor.getSensorC() == 0 || sensor.getSensorD() == 0) {
                                    inv.setVertical_step(inv.getVertical_step() + 1);
                                }
                            }
                        }
                        time.setTime(time.getTime()+1);
                    }
                    //print the output.
                    // System.out.println(time.getTime()); 

                    writer.write(String.format("%d %f %d \n",boundary.getWidth(),sensor.getP(),time.getTime()));

                }
            }
            //close the file 
            writer.close();
    }
}