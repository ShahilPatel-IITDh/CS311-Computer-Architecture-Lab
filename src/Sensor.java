//importing package to generate random numbers
import java.util.Random;

public class Sensor {

    private boolean switched;
    Sensor(){
        this.switched = false;   //setting the initial set of Sensor to OFF. //Here false = OFF and true = ON
    }
    //method (function) to generate the random numbers according to given parameters.
    public int randomNumberInRange(int min, int max){
        Random random = new Random();
        return random.nextInt((max-min)+1)+min;  //bounding the next random integer that will be generated.
    }

    //set the state of Sensors (On/Off)
    public boolean getSwitch(){
        return switched; //return the state of the switch when called.
    }

    public void setSwitched(boolean state){
        this.switched = state; //given the state as parameter, this will set the state of Switch to the specified state.
    }

    //using probability to change the state of the switch
    public void setSwitch(float temp){
        int x = 10-(int)temp*10; //temp is the given probabilistic value
        int y = randomNumberInRange(1,10);
        setSwitched(y<x); //need to verify if condition is y>x or y<x;
    }

}
