//These class file calculates the time taken to cross the boundary.

import java.time.LocalTime;
//these package will be used to generate the local time.
import java.time.Duration;

public class Time {
    private int time;
    private LocalTime initial_time;
    private LocalTime current_time;
    int iteration = 0;
    Time(){
        initial_time = LocalTime.now(); //initialize the initial_time with the current time i.e. time while executing the file.
        iteration++;
    }

    public LocalTime getinitial_time(){
        return initial_time;
    }
    public LocalTime getcurrent_time(){
        return current_time;
    }

    public void setcurrent_time(){
        this.current_time = LocalTime.now();
    }

    public int getduration(){
        Duration duration = Duration.between(initial_time, current_time); //the duration package is used here to get time difference between the initial and current time.
        
        return (int) duration.getSeconds(); //return seconds of time difference between the initial time and current time in integer format.
    }


    //method to set time using 'this'
    public Time(int t){
        this.time = t;
    }

    //setter method
    public int getTime(){
        return time;
    }

    public void setTime(int t) {
        this.time = t;
    }
}
