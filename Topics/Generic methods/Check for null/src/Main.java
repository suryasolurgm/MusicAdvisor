// do not remove imports
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

class ArrayUtils {
    // define hasNull method here
    public static <T> boolean hasNull(T t[]){
        for(int i=0;i<t.length;i++){
            if(t[i]==null){
                return true;
            }
        }
        return false;
    }
}
