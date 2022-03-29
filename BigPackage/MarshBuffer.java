package BigPackage;

import java.util.Arrays;

public class MarshBuffer {
    private byte[] buf;
    private BufferPointer writeBufPt;
    private BufferPointer readBufPt;

    public MarshBuffer(){
        this.writeBufPt = new BufferPointer();
        this.readBufPt = new BufferPointer();
    }
    
    public MarshBuffer(int numOfByte){
        this();
        this.buf = new byte[numOfByte];
    }

    public MarshBuffer(byte[] buf){
        this();
        this.buf = buf;
    }

    public byte[] toByte() {
        return Arrays.copyOf(this.buf,this.buf.length);   
    }

    public void marshInt(Integer data){
        MarshUtil.marshInt(data,this.buf,this.writeBufPt);
    }

    public Integer unmarshInt(){
        return MarshUtil.unmarshInt(this.buf,this.readBufPt);
    }

    public void marshShort(Short data){
        MarshUtil.marshShort(data,this.buf,this.writeBufPt);
    }

    public Short unmarshShort(){
        return MarshUtil.unmarshShort(this.buf,this.readBufPt);
    }

    public void marshFloat(Float data){
        MarshUtil.marshFloat(data,this.buf,this.writeBufPt);
    }

    public Float unmarshFloat(){
        return MarshUtil.unmarshFloat(this.buf,this.readBufPt);
    }

    public void marshString(String data){
        MarshUtil.marshString(data,this.buf,this.writeBufPt);
    }

    public String unmarshString(){
        return MarshUtil.unmarshString(this.buf,this.readBufPt);
    }

    public void marshCharArray(char[] data){
        MarshUtil.marshCharArray(data,this.buf,this.writeBufPt);
    }

    public char[] unmarshCharArray(int len){
        return MarshUtil.unmarshCharArray(this.buf,len,this.readBufPt);
    }

    public void marshCType(CurrencyType data){
        MarshUtil.marshCType(data,this.buf,this.writeBufPt);
    }

    public CurrencyType unmarshCType(){
        return MarshUtil.unmarshCType(this.buf,this.readBufPt);
    }

    
   


}