// do not remove imports
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

import static java.util.Collections.swap;

class ArrayUtils {
    // define invert method here
    public static <T> T[] invert(T[] t ){
        int s=0;
        int e=t.length-1;
        while(s<e){
            T temp=t[s];
            t[s]=t[e];
            t[e]=temp;
            s++;
            e--;
        }
        return t;
    }


}