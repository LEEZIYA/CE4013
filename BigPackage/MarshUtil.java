package BigPackage;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/* 
Marshalling:
Initialize a buffer with the correct size. 
Sequentially add in the bytes with MarshUtil.marshX(data, buffer, offset). Increment offset after each marshalling. 
Unmarshalling:
Sequentially read the bytes with MarshUtil.unmarshX(data, buffer, offset). Increment offset after each unmarshalling. 
*/

public class MarshUtil{

    public static boolean LITTLE_ENDIAN = true;
    public static Charset CHARSET = StandardCharsets.UTF_8;

    public static void marshInt(Integer data, byte[] out, BufferPointer bufPt) {    
        int offset = bufPt.at;
        if (LITTLE_ENDIAN){
            out[offset+3] = (byte) ((data & 0xFF000000) >> 24);
            out[offset+2] = (byte) ((data & 0x00FF0000) >> 16);
            out[offset+1] = (byte) ((data & 0x0000FF00) >> 8);
            out[offset+0] = (byte) ((data & 0x000000FF) >> 0);
        } else {
            out[offset+0] = (byte) ((data & 0xFF000000) >> 24);
            out[offset+1] = (byte) ((data & 0x00FF0000) >> 16);
            out[offset+2] = (byte) ((data & 0x0000FF00) >> 8);
            out[offset+3] = (byte) ((data & 0x000000FF) >> 0);
        }
        bufPt.at += 4;
    }

    public static Integer unmarshInt(byte[] data, BufferPointer bufPt){
        int offset = bufPt.at;
        Integer result = 0;
        if (LITTLE_ENDIAN){
            result += (data[offset + 3] << 24) & 0xFF000000;
            result += (data[offset + 2] << 16) & 0x00FF0000;
            result += (data[offset + 1] << 8) & 0x0000FF00;
            result += (data[offset + 0] << 0) & 0x000000FF;
        } else {
            result += (data[offset + 0] << 24) & 0xFF000000;
            result += (data[offset + 1] << 16) & 0x00FF0000;
            result += (data[offset + 2] << 8) & 0x0000FF00;
            result += (data[offset + 3] << 0) & 0x000000FF;
        }
        bufPt.at += 4;
        return result;
    }

    public static void marshShort(short data, byte[] out, BufferPointer bufPt) {    
        int offset = bufPt.at;
        if (LITTLE_ENDIAN){
            out[offset+1] = (byte) ((data & 0x0000FF00) >> 8);
            out[offset+0] = (byte) ((data & 0x000000FF) >> 0);
        } else {
            out[offset+0] = (byte) ((data & 0x0000FF00) >> 8);
            out[offset+1] = (byte) ((data & 0x000000FF) >> 0);
        }
        bufPt.at += 2;
    }

    public static short unmarshShort(byte[] data, BufferPointer bufPt){
        int offset = bufPt.at;
        short result = 0;
        if (LITTLE_ENDIAN){
            result += (data[offset + 1] << 8) & 0x0000FF00;
            result += (data[offset + 0] << 0) & 0x000000FF;
        } else {
            result += (data[offset + 0] << 8) & 0x0000FF00;
            result += (data[offset + 1] << 0) & 0x000000FF;
        }
        bufPt.at += 2;
        return result;
    }

    public static void marshCType(CurrencyType data, byte[] out, BufferPointer bufPt){
        out[bufPt.at] = (byte) data.ordinal();
        bufPt.at += 1;
    }

    public static CurrencyType unmarshCType(byte[] data, BufferPointer bufPt){
        CurrencyType result = CurrencyType.values()[(int) data[bufPt.at]];
        bufPt.at += 1;
        return result;
    }

    public static int getStringByteLen(String data){
        // UTF-8 is variable length encoding so not 1 char = 1 byte
        return data.getBytes(CHARSET).length;
    }

    public static int getStringByteLen(char[] data){
        return 2 + getStringByteLen(new String(data));
    }

    public static void marshString(String data, byte[] out, BufferPointer bufPt){
        var charBytes = data.getBytes(CHARSET);
        short strLen = (short) charBytes.length;
        marshShort(strLen, out, bufPt);
        for (int i = 0; i < strLen; i++){
            out[bufPt.at+i] = charBytes[i];
        }
        bufPt.at += strLen;
    }

    public static String unmarshString(byte[] data, BufferPointer bufPt){
        short strLen = unmarshShort(data, bufPt);
        byte[] resultBytes = new byte[strLen];
        for (int i = 0; i < strLen; i++){
            resultBytes[i] = data[bufPt.at + i];
        }
        bufPt.at += strLen;
        String result = new String(resultBytes, CHARSET);
        return result;
    }

    public static void marshCharArray(char[] data, byte[] out, BufferPointer bufPt){
        String dataStr = new String(data);
        byte[] result = dataStr.getBytes(CHARSET);
        for (int i = 0; i < result.length; i++){
            out[bufPt.at+i] = result[i];
        }
        bufPt.at += result.length;
    }

    public static char[] unmarshCharArray(byte[] data, int length, BufferPointer bufPt){
        // note: assumption here is that one char = one byte (in utf-8, ascii characters use only 1 byte)
        byte[] charData = new byte[length];
        for (int i = 0; i < length; i++){
            charData[i] = data[bufPt.at + i];
        }
        bufPt.at += length;
        String charDataStr = new String(charData, CHARSET);
        char[] result = charDataStr.toCharArray();
        return result;
    }

    public static void marshFloat(float data, byte[] out, BufferPointer bufPt){
        int floatInt = Float.floatToRawIntBits(data);
        marshInt(floatInt, out, bufPt);
    }

    public static float unmarshFloat(byte[] data, BufferPointer bufPt){
        int floatInt = unmarshInt(data, bufPt);
        return Float.intBitsToFloat(floatInt);
    }

    private static String byteToHex(byte b){
        return Integer.toHexString(Byte.toUnsignedInt(b));
    }

    public static String bytesToHex(byte[] bytes){
        String result = "";
        for (int i = 0; i < bytes.length; i++){
            result += byteToHex(bytes[i]) + " ";
        }
        return result;
    }

    private static void testInt(int x){
        System.out.println("original int: " + x);
        byte[] buf = new byte[4];
        BufferPointer writeBufPt = new BufferPointer();
        BufferPointer readBufPt = new BufferPointer();

        System.out.println("=== small endian ===");
        LITTLE_ENDIAN = true;
        marshInt(x, buf, writeBufPt);
        int unmarshelled = unmarshInt(buf, readBufPt);
        System.out.println("marshelled: " + bytesToHex(buf));
        System.out.println("unmarshelled: " + unmarshelled);

        System.out.println("=== big endian ===");
        LITTLE_ENDIAN = false;
        writeBufPt.at = 0;
        readBufPt.at = 0;
        marshInt(x, buf, writeBufPt);
        unmarshelled = unmarshInt(buf, readBufPt);
        System.out.println("marshelled: " + bytesToHex(buf));
        System.out.println("unmarshelled: " + unmarshelled);

        System.out.println();
    }

    private static void testShort(short x){
        System.out.println("original short: " + x);
        byte[] buf = new byte[2];
        BufferPointer writeBufPt = new BufferPointer();
        BufferPointer readBufPt = new BufferPointer();

        System.out.println("=== small endian ===");
        LITTLE_ENDIAN = true;
        marshShort(x, buf, writeBufPt);
        short unmarshelled = unmarshShort(buf, readBufPt);
        System.out.println("marshelled: " + bytesToHex(buf));
        System.out.println("unmarshelled: " + unmarshelled);

        System.out.println("=== big endian ===");
        LITTLE_ENDIAN = false;
        writeBufPt.at = 0;
        readBufPt.at = 0;
        marshShort(x, buf, writeBufPt);
        unmarshelled = unmarshShort(buf, readBufPt);
        System.out.println("marshelled: " + bytesToHex(buf));
        System.out.println("unmarshelled: " + unmarshelled);
     
        System.out.println();
    }

    private static void testFloat(float x){
        System.out.println("original float: " + x);
        byte[] buf = new byte[4];
        BufferPointer writeBufPt = new BufferPointer();
        BufferPointer readBufPt = new BufferPointer();

        System.out.println("=== small endian ===");
        LITTLE_ENDIAN = true;
        marshFloat(x, buf, writeBufPt);
        float unmarshelled = unmarshFloat(buf, readBufPt);
        System.out.println("marshelled: " + bytesToHex(buf));
        System.out.println("unmarshelled: " + unmarshelled);

        System.out.println("=== big endian ===");
        LITTLE_ENDIAN = false;
        marshFloat(x, buf, writeBufPt);
        unmarshelled = unmarshFloat(buf, readBufPt);
        System.out.println("marshelled: " + bytesToHex(buf));
        System.out.println("unmarshelled: " + unmarshelled);

        System.out.println();
    }

    private static void testString(String x){
        System.out.println("original String: " + x);
        byte[] buf = new byte[2 + getStringByteLen(x)];
        BufferPointer writeBufPt = new BufferPointer();
        BufferPointer readBufPt = new BufferPointer();
        
        marshString(x, buf, writeBufPt);
        String unmarshelled = unmarshString(buf, readBufPt);
        System.out.println("marshelled: " + bytesToHex(buf));
        System.out.println("unmarshelled: " + unmarshelled);

        System.out.println();
    }

    private static void testCharArray(char[] x){
        System.out.println("original char array: " + new String(x));
        byte[] buf = new byte[2 + getStringByteLen(x)];
        BufferPointer writeBufPt = new BufferPointer();
        BufferPointer readBufPt = new BufferPointer();
        
        marshCharArray(x, buf, writeBufPt);
        char[] unmarshelled = unmarshCharArray(buf, x.length, readBufPt);
        System.out.println("marshelled: " + bytesToHex(buf));
        System.out.println("unmarshelled: " + new String(unmarshelled));

        System.out.println();
    }

    private static void testCurrencyType(CurrencyType x){
        System.out.println("original currencyType: " + x);
        byte[] buf = new byte[1];
        BufferPointer writeBufPt = new BufferPointer();
        BufferPointer readBufPt = new BufferPointer();
        
        marshCType(x, buf, writeBufPt);
        CurrencyType unmarshelled = unmarshCType(buf, readBufPt);
        System.out.println("marshelled: " + bytesToHex(buf));
        System.out.println("unmarshelled: " + unmarshelled.toString());

        System.out.println();
    }

    public static void main(String[] args){
        testInt(65332789);
        testShort((short)10448);
        testFloat((float)0.12345);
        testCurrencyType(CurrencyType.SGD);
        testString("Hello world panda!");
        testCharArray("Hello world bamboo!".toCharArray());
    }
}