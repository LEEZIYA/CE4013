package Server;

// int (32-bit), short (16-bit), enum (8-bit), float (32-bit)
// bit size of these data types should be same for C
// TBD is the character encoding

class MarshUtil{
    static byte[] marshInt(Integer data, boolean littleEndian) {    
        byte[] result = new byte[4];
        if (littleEndian){
            result[3] = (byte) ((data & 0xFF000000) >> 24);
            result[2] = (byte) ((data & 0x00FF0000) >> 16);
            result[1] = (byte) ((data & 0x0000FF00) >> 8);
            result[0] = (byte) ((data & 0x000000FF) >> 0);
        } else {
            result[0] = (byte) ((data & 0xFF000000) >> 24);
            result[1] = (byte) ((data & 0x00FF0000) >> 16);
            result[2] = (byte) ((data & 0x0000FF00) >> 8);
            result[3] = (byte) ((data & 0x000000FF) >> 0);
        }
        return result;
    }

    static Integer unmarshInt(byte[] data, boolean littleEndian, int offset){
        Integer result = 0;
        if (littleEndian){
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
        return result;
    }

    static byte[] marshShort(short data, boolean littleEndian) {    
        byte[] result = new byte[2];
        if (littleEndian){
            result[1] = (byte) ((data & 0x0000FF00) >> 8);
            result[0] = (byte) ((data & 0x000000FF) >> 0);
        } else {
            result[0] = (byte) ((data & 0x0000FF00) >> 8);
            result[1] = (byte) ((data & 0x000000FF) >> 0);
        }
        return result;
    }

    static short unmarshShort(byte[] data, boolean littleEndian, int offset){
        short result = 0;
        if (littleEndian){
            result += (data[offset + 1] << 8) & 0x0000FF00;
            result += (data[offset + 0] << 0) & 0x000000FF;
        } else {
            result += (data[offset + 0] << 8) & 0x0000FF00;
            result += (data[offset + 1] << 0) & 0x000000FF;
        }
        return result;
    }

    static byte[] marshCType(CurrencyType data){
        byte[] result = new byte[1];
        result[0] = (byte) data.ordinal();
        return result;
    }

    static CurrencyType unmarshCType(byte[] data, int offset){
        return CurrencyType.values()[(int) data[offset]];
    }

    static byte[] marshString(String data, boolean littleEndian){
        short strLen = (short) data.length();
        byte[] result = new byte[2 + strLen];
        byte[] strLenBytes = marshShort(strLen, littleEndian);
        result[0] = strLenBytes[0];
        result[1] = strLenBytes[1];
        char[] charArr = data.toCharArray();
        for (int i = 0; i < charArr.length; i++){
            // what is the encoding?
        }
        return result;
    }

    static String unmarshString(byte[] data, boolean littleEndian, int offset){
        String result = "";
        short strLen = unmarshShort(data, littleEndian, offset);
        // encoding?
        return result;
    }

    static byte[] marshCharArray(char[] data, int length){
        byte[] result = new byte[length];
        // encoding?
        return result;
    }

    static char[] unmarshCharArray(byte[] data, int length, int offset){
        char[] result = new char[length];
        return result;
    }

    static byte[] marshFloat(float data, boolean littleEndian){
        int floatInt = Float.floatToRawIntBits(data);
        return marshInt(floatInt, littleEndian);
    }

    static float unmarshFloat(byte[] data, boolean littleEndian, int offset){
        int floatInt = unmarshInt(data, littleEndian, offset);
        return Float.intBitsToFloat(floatInt);
    }

    private static String byteToHex(byte b){
        return Integer.toHexString(Byte.toUnsignedInt(b));
    }

    private static String bytesToHex(byte[] bytes){
        String result = "";
        for (int i = 0; i < bytes.length; i++){
            result += byteToHex(bytes[i]) + " ";
        }
        return result;
    }

    private static void testInt(int x){
        System.out.println("original int: " + x);
        
        System.out.println("=== small endian ===");
        byte[] marshelled = marshInt(x, true);
        int unmarshelled = unmarshInt(marshelled, true, 0);
        System.out.println("marshelled: " + bytesToHex(marshelled));
        System.out.println("unmarshelled: " + unmarshelled);

        System.out.println("=== big endian ===");
        marshelled = marshInt(x, false);
        unmarshelled = unmarshInt(marshelled, false, 0);
        System.out.println("marshelled: " + bytesToHex(marshelled));
        System.out.println("unmarshelled: " + unmarshelled);

        System.out.println();
    }

    private static void testShort(short x){
        System.out.println("original short: " + x);
        
        System.out.println("=== small endian ===");
        byte[] marshelled = marshShort(x, true);
        short unmarshelled = unmarshShort(marshelled, true, 0);
        System.out.println("marshelled: " + bytesToHex(marshelled));
        System.out.println("unmarshelled: " + unmarshelled);

        System.out.println("=== big endian ===");
        marshelled = marshShort(x, false);
        unmarshelled = unmarshShort(marshelled, false, 0);
        System.out.println("marshelled: " + bytesToHex(marshelled));
        System.out.println("unmarshelled: " + unmarshelled);
     
        System.out.println();
    }

    private static void testFloat(float x){
        System.out.println("original float: " + x);
        
        System.out.println("=== small endian ===");
        byte[] marshelled = marshFloat(x, true);
        float unmarshelled = unmarshFloat(marshelled, true, 0);
        System.out.println("marshelled: " + bytesToHex(marshelled));
        System.out.println("unmarshelled: " + unmarshelled);

        System.out.println("=== big endian ===");
        marshelled = marshFloat(x, false);
        unmarshelled = unmarshFloat(marshelled, false, 0);
        System.out.println("marshelled: " + bytesToHex(marshelled));
        System.out.println("unmarshelled: " + unmarshelled);

        System.out.println();
    }

    private static void testString(String x){

    }

    private static void testCharArray(char[] x){

    }

    private static void testCurrencyType(CurrencyType x){
        System.out.println("original currencyType: " + x);
        
        byte[] marshelled = marshCType(x);
        CurrencyType unmarshelled = unmarshCType(marshelled, 0);
        System.out.println("marshelled: " + bytesToHex(marshelled));
        System.out.println("unmarshelled: " + unmarshelled);

        System.out.println();
    }

    public static void main(String[] args){
        testInt(65332789);
        testShort((short)10448);
        testFloat((float)0.12345);
        testCurrencyType(CurrencyType.SGD);
    }
}