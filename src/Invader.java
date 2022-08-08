//importing java library for generating random numbers

import javax.xml.stream.FactoryConfigurationError;
import java.util.Random;

public class Invader {
    //these class file has details regarding movements of Invader (Infiltrator)
    int length;   //determine the horizontal position of the Invader
    int width;    //determine the vertical position of the Invader
    Boundary type;
    boolean success; //variable to check whether the invader has crossed the fence or not.
    boolean caught; //variable to check if Invader was caught by sensors.

    public Invader() {   //initializing the variables for object Invader
        this.success = false;
        this.caught = false;
        this.length = 0;
        this.width = 0;
        this.type = new Boundary(width, length);
    }

}
