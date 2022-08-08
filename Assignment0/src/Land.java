
//packages to create a file to store output, which will be used to generate a graph plot.

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
            
            for(double probability=0;probability<=0.95;probability+=0.05){

                for(int width=5;width<=100;width+=5){

                    Boundary boundary = new Boundary(width,1000);
                    Sensor sensor = new Sensor(probability);  //giving probability as input ro Sensor class which will decide whether the sensor is ON or OFF
                    Invader inv = new Invader(1); //the initial vertical step is 0 initially.
                    Time time = new Time(0);  //initial time is 0.
                    
                    //while the invader has not yet crossed the boundary
                    while ((inv.getVertical_step()!= boundary.getWidth()+1)){
                        if (time.getTime()%10==0) {
                            sensor.takeDecision();
                            if (sensor.getSensorA() == 0) {
                                if(inv.getVertical_step()== boundary.getWidth()){
                                    inv.setVertical_step(inv.getVertical_step() + 1);
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
                    System.out.println(time.getTime());
                    writer.write(String.format("%d %f %d \n",boundary.getWidth(),sensor.getP(),time.getTime()));

                }
            }

            writer.close();
    }
}