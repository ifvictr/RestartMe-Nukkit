package restartme.utils;

public class Utils{
    public static double calculateBytes(String toCheck){
        toCheck = toCheck.trim();
        int byteLimit = Integer.parseInt(toCheck.substring(0, toCheck.length() - 1));
        //Should I add support for both types of suffixes (G and GB) in the future?
        switch(toCheck.substring(toCheck.length() - 1).toUpperCase()){
            case "P": //petabyte
                return byteLimit * Math.pow(1024, 5);
            case "T": //terabyte
                return byteLimit * Math.pow(1024, 4);
            case "G": //gigabyte
                return byteLimit * Math.pow(1024, 3);
            case "M": //megabyte
                return byteLimit * Math.pow(1024, 2);
            case "K": //kilobyte
                return byteLimit * 1024;
            case "B": //byte
                return byteLimit;
            default:
                return byteLimit;
        }
    }
    public static boolean isOverloaded(String toCheck){
        return Utils.isOverloaded(Utils.calculateBytes(toCheck));
    }
    public static boolean isOverloaded(double toCheck){
        return Runtime.getRuntime().totalMemory() > toCheck;
    }
    public static int[] toArray(int time){
        return new int[]{
            (int) Math.floor(time / 3600),
            (int) Math.floor((time / 60) - (Math.floor(time / 3600) * 60)),
            (int) Math.floor(time % 60)
        };
    }
}