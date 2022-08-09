// import java.util.Scanner;

public class ass0 {
    public static void main (String[] args) 
    {
        // System.out.println("Hello Java");
        // border bor = new border();
        // Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        for(float prob=0.02f;prob<0.98;prob+=0.02)
        {
            for(int width=10;width<200;width+=10)
            {
                int[] arr = new int[5];
                for(int times=0;times<5;times++)
                {
                    infiltrator Attacking_country = new infiltrator(1000,prob,width);
                    Attacking_country.Move();
                    arr[times] = Attacking_country.time.t;  
                }
                System.out.println((arr[0] + arr[1] + arr[2]+arr[3]+arr[4])/5);
            }
        }
    }
}
