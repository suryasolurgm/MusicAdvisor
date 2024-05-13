// do not remove imports
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;


class ArrayUtils {
    // define info method here
    public static <T> String info(T t[]){
        StringBuilder sb = new StringBuilder();
        if(t.length==0){
            sb.append("[]");
        }else{
            for(int i=0;i<t.length;i++){
                if(i==0){
                    sb.append("[");
                }
                if(i==t.length-1){
                    sb.append(t[i]).append("]");
                }else{
                    sb.append(t[i]).append(",").append(" ");
                }
            }
        }
       return sb.toString();
    }
}
