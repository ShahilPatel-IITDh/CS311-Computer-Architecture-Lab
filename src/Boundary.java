public class Boundary {
    //class which will provide length and width of boundary whenever asked from Main class i.e. Land class
    private int width;  //value assigned using Boundary package
    private int length; //value assigned using Boundary package

    public Boundary(int w, int l){
        this.width  = w; //w = width
        this.length = l; //l = length
    }


    //getter method to return value whenever called.
    public int getWidth(){
        return width;
    }

    //setter method to set value of width.
    public void setWidth(int w){
        this.width = w;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
