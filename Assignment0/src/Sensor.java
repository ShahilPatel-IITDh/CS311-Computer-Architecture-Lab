//package to generate random numbers.
import java.lang.Math;

public class Sensor {

    private double probability; //variable to store the probability values.
    
    //constructor for sensor.
    public Sensor(double probability) {
        this.probability = probability;
    }
    
    //getter for Probability value.
    public double getP() {
        return probability;
    }

    //setter for Probability value.
    public void setP(double probability) {
        this.probability = probability;
    }

    private int sensorA,sensorB, sensorC, sensorD; //sensors to take decision regarding the movement of Invader.
    
    public void takeDecision(){
       
        double stateC =  Math.random();
        double stateD =  Math.random();
        double stateA =  Math.random();
        double stateB =  Math.random();
        
        //if random integer A is less than probability value then the sensor will be ON else OFF.
        
        if (stateD<probability){
            setSensorD(1);
        }
        else {
            setSensorD(0);
        }

        if (stateC<probability){
            setSensorC(1);
        }
        else {
            setSensorC(0);
        }

        if (stateB<probability){
            setSensorB(1);
        }
        else {
            setSensorB(0);
        }

        if (stateA<probability){
            setSensorA(1);
        }
        else {
            setSensorA(0);
        }
    }

    //getter for sensors
    public int getSensorA() {
        return sensorA;
    }

    // setter for sensors
    public void setSensorA(int sensorA) {
        this.sensorA = sensorA;
    }

    public int getSensorB() {
        return sensorB;
    }

    public void setSensorB(int sensorB) {
        this.sensorB = sensorB;
    }

    public int getSensorC() {
        return sensorC;
    }

    public void setSensorC(int sensorC) {
        this.sensorC = sensorC;
    }

    public int getSensorD() {
        return sensorD;
    }

    public void setSensorD(int sensorD) {
        this.sensorD = sensorD;
    }
}
