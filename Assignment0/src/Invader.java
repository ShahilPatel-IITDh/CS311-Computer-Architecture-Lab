//these file return the vertical steps of invader that is used to generate movement.
public class Invader {
    private int vertical_step; //length of the vertical distance covered.
    
    public int getVertical_step(){
        return vertical_step;
    }

    public void setVertical_step(int vertical_step){
        this.vertical_step = vertical_step;
    }
    
    public Invader(int vertical_step){
        this.vertical_step = vertical_step;
    }
}
