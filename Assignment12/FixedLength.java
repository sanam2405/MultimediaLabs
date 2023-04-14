import java.util.HashMap;
import java.util.Map;

public class FixedLength {
    public static String getBinary(int num, double bits){
        String res = Integer.toBinaryString(num);
        int len = res.length();
        if(len == bits){
            return res;
        }
        else{
            StringBuffer s = new StringBuffer();
            for(int i =0;i<bits-len;i++) s.append("0");
            s.append(res);
            return s.toString();
        }
    }
    public static void main(String[] args) {
        String test = "arindam";
        char[] charArray = new char[26];
        int[] charFreq = new int[26];
        int count = 0;
        for(int i=0;i<test.length();i++){
            if(charFreq[test.charAt(i)-'a'] == 0){
                count++;
            }
            charFreq[test.charAt(i)-'a']++;
        }
        int k =0;
        for(int i =0;i<charFreq.length;i++){
			if(charFreq[i] != 0) charArray[k++] = (char)(i+'a');
		}
        int size = charArray.length;
        System.out.println();
        
        double bits =  Math.ceil(Math.log10(count)/Math.log10(2));
        System.out.println("Number of bits required = "+ Math.ceil(Math.log10(count)/Math.log10(2)));
        Map<Character,Integer> mp = new HashMap<>();
        for(int i =0;i<count;i++){
            mp.put(charArray[i],i);
        }
        for(char ch : charArray) System.out.print(ch+ " ");
        
        String[] bitarray = new String[count];
        for(int i =0;i<count;i++){
            String binary = getBinary(i,bits);
            bitarray[i] = binary;
        }
        System.out.println("count = "+ count);
        StringBuffer res = new StringBuffer();
        for(int i =0;i<test.length();i++){
            res.append(bitarray[mp.get(test.charAt(i))]);
        }
        System.out.println(res);
        int before = test.length(); System.out.println("before : "+ before);
        int after = res.length(); System.out.println("after : "+after);
        System.out.println("Compression Ratio " + (double)(before*1.0/after));
    }
    
}
