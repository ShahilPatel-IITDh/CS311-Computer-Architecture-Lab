public class sensor
{    
    float p;
    border board;
    public void Create(){
        board = new border();
        // board.l = 1000;
        // board.w = w;     // board.w will be provided through ass0.java
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(Math.random()<p)  board.border[i][j] = 1;
                else board.border[i][j] = 0;
            }
        }
    }
}
