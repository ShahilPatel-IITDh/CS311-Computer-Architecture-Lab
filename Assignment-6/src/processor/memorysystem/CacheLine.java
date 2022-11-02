package processor.memorysystem;

public class CacheLine {
    int data;
    int tag;

    public CacheLine() {
        this.tag = -1;
        this.data = -1;
    }

    public void setData(int newData){
        this.data = newData;
    }
    public int getData() {
        return this.data;
    }

    public void setTag(int newTag){
        this.tag = newTag;
    }

    public int getTag() {
        return this.tag;
    }

}
