public class sensor
{    
    float p;
    public border bord;
    public void Create()
    {
        public bord = new border();
        // bord.l = 1000;
        // bord.w = w;     // bord.w will be provided through ass0.java
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                if(Math.random()<p)  bord.border[i][j] = 1;
                else bord.border[i][j] = 0;
            }
        }
    }

    public void main(String args[])
    {
// 
    }

}
