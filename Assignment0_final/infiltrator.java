public class infiltrator extends sensor 
{
    int x,y; // Coordinates of the Infiltrator
    float p;  // Sensor probability
    int w; //Border width
    int cnt; // Count the iterations
    boolean stop;// Trigger to stop iterations of the infiltrator
    clock time = new clock(); // Calling the Clock class
    public infiltrator(int l,float prob,int width)
    {
        x=l/2;
        y=0;
        p = prob;
        w = width;
        cnt = 0;
        this.stop = false;
        time.t=0; 
    }
    public void Move()
    {
        // bord.l = 1000;
        while((x<=5000 && x!=0) && (y<=w && y>=0))
        {
            time.t+=10; 
            sensor sens = new sensor();
            sens.p = p;
            sens.Create();
            if(sens.bord.border[1][1]==0)
            {
                if(y<w-1 && x<4999) {
                    if(sens.bord.border[2][2]==0) {x+=1;y+=1;}}
                if(x<4999){
                    if(sens.bord.border[2][1]==0) x+=1;}
                if(y>0) {
                    if(x<4999)   {if(sens.bord.border[2][0]==0) {x+=1;y-=1;}}
                    if(sens.bord.border[1][0]==0) y-=1;
                    if(x>0)     {if(sens.bord.border[0][0]==0) {x-=1;y-=1;}}}
                if(x>0)    {if(sens.bord.border[0][1]==0) x-=1;}
                if(y<w-1)  {if(x>0) {if(sens.bord.border[0][2]==0) {x-=1;y+=1;}}
                            if(sens.bord.border[1][2]==0) y+=1;}
            }
        }
        this.stop = true;
    }
    public void main(String args[])
    {

    }
}
