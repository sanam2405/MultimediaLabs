public class RunLengthCoding{

    public static String calculateResult(String s){
        StringBuffer ans = new StringBuffer();
        int count=1;
        char ch = s.charAt(0);
        for(int i=1; i<s.length(); i++){
            if(s.charAt(i) == ch){
                count++;
            }
            else{
                ans.append(ch);
                if(count>1){
                    ans.append(count);
                }
                ch = s.charAt(i);  count=1;
            }
        }
        ans.append(ch);
        if(count>1){
            ans.append(count);
        }
        return ans.toString();
    }
    public static void main(String[] args){
        String str = "aaaaaabbcccdd";
        String ans = calculateResult(str);
        System.out.println("Result: "+ans);
    }
}