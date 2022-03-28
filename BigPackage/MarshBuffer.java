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
        this.buf = new byte[numbOfByte];
    }

    public MarshBuffer(byte[] buf){
        this();
        this.buf = buf;
    }

    public byte[] toByte() {
        return Arrays.copyof(this.buf,this.buf.length);   
    }

    public void marshInt(Integer data){
        MarshUtil.marshInt(data,this.buf,this.writeBufPt);
    }

    public Integer unmarshInt(){
        return MarshUtil.unmarshInt(data,this.buf,this.readBufPt);
    }

    public void marshShort(Short data){
        MarshUtil.marshShort(data,this.buf,this.writeBufPt);
    }

    public Short unmarshShort(){
        return MarshUtil.unmarshS(data,this.buf,this.readBufPt);
    }

    public void marshFloat(Float data){
        MarshUtil.marshFloat(data,this.buf,this.writeBufPt);
    }

    public Float unmarshFloat(){
        return MarshUtil.unmarshFloat(data,this.buf,this.readBufPt);
    }

    public void marshString(String data){
        MarshUtil.marshString(data,this.buf,this.writeBufPt);
    }

    public Float unmarshString(){
        return MarshUtil.unmarshString(data,this.buf,this.readBufPt);
    }

    public void marshCharArray(char[] data){
        MarshUtil.marshCharArray(data,this.buf,this.writeBufPt);
    }

    public Float unmarshCharArray(){
        return MarshUtil.unmarshCharArray(data,this.buf,this.readBufPt);
    }

    public void marshCType(CurrencyType data){
        MarshUtil.marshCType(data,this.buf,this.writeBufPt);
    }

    public CurrencyType unmarshCType(){
        return MarshUtil.unmarshCtype(data,this.buf,this.readBufPt);
    }

    
   


}