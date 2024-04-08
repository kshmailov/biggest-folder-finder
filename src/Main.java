import java.io.File;
import java.util.HashMap;
import java.util.concurrent.ForkJoinPool;


public class Main {
    public static void main(String[] args) {

        String folderPath = "/home/kirill/IdeaProjects";
        File file = new File(folderPath);
        long start = System.currentTimeMillis();
        FolderSizeCalculator calculator = new FolderSizeCalculator(file);
        ForkJoinPool pool = new ForkJoinPool();
        long size=pool.invoke(calculator);
        System.out.println(size);
//        System.out.println(getFolderSize(file));
        long duration = System.currentTimeMillis() - start;
        System.out.println(duration);
        System.out.println(getSizeFromHumanReadable("42T"));
        System.out.println(getHumanReadableSize(getSizeFromHumanReadable("42T")));

    }
    public static long getFolderSize(File folder){
        if (folder.isFile()){
            return folder.length();
        }
        long sum = 0;
        File[] files = folder.listFiles();
        assert files != null;
        for(File file: files){
            sum+=getFolderSize(file);
        }
        return sum;
    }
    public static String getHumanReadableSize(long size){
        long value = size;
        int factor =0;
        while (value>=1024){
            value/=1024;
            factor++;
        }
        HashMap<Integer, String> indexMap =getIndex();
        String index = indexMap.get(factor);
        long modernSize =Math.round(size/Math.pow(1024, factor));
        return modernSize +index+"b";
    }
    public static long getSizeFromHumanReadable(String size){
        char sizeFactor = size.replaceAll("[0-9\\s+]","").charAt(0);
        HashMap<Character, Long> char2Multipliers = getMultipliers();
        long factor = char2Multipliers.get(sizeFactor);
        long length = Long.parseLong(size.replaceAll("[^0-9]",""));
        return Math.multiplyExact(length,factor);
    }
    private static HashMap<Character, Long> getMultipliers(){
        char[] multipliiers = {'B', 'K', 'M', 'G', 'T'};
        HashMap<Character, Long> char2multiplier = new HashMap<>();
        for (int i=0; i<multipliiers.length; i++){
            char2multiplier.put(multipliiers[i],(long) Math.pow(1024,i));
        }
        return char2multiplier;
    }
    private static HashMap<Integer, String> getIndex(){
        String[] indexes = {"0", "1", "2", "3", "4"};
        HashMap<Integer, String> indexes2multiplier = new HashMap<>();
        for (int i=0; i<indexes.length; i++){
            if (i==0){
                indexes2multiplier.put(i, "B");
            } else if (i==1) {
                indexes2multiplier.put(i, "K");
            }
            else if (i==2) {
                indexes2multiplier.put(i, "M");
            }else if (i==3) {
                indexes2multiplier.put(i, "G");
            }else {
                indexes2multiplier.put(i, "T");
            }

        }
        return indexes2multiplier;
    }
}
