
import java.io.*;
import java.util.*;
 
class Huffman_Coding 
{
    
    // main function
    
    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException
    {
 
        Scanner s = new Scanner(System.in);
 
        // number of characters.
        char[] charArray =readingFileOne();
        int n =charArray.length;
        int[] charfreq =readingFileTwo();
        double prob[]=probability(charfreq);
 
        // creating a priority queue q. makes a min-priority queue(min-heap).
        PriorityQueue<HuffmanNode> q = new PriorityQueue<HuffmanNode>(n, new MyComparator());
 
        for (int i = 0; i < n; i++) 
        {
            // creating a Huffman node object and add it to the priority queue.
            HuffmanNode node=new HuffmanNode();
 
            node.c=charArray[i];
            node.data=charfreq[i];
 
            node.left=null;
            node.right=null;
 
            // add functions adds the huffman node to the queue.
            q.add(node);
        }
 
        // create a root node
        HuffmanNode root=null;
 
        // We extract the two minimum value from the heap each time until its size reduces to 1, extract until
        // all the nodes are extracted.
        
        while (q.size() > 1) 
        {
            HuffmanNode min1 = q.peek();
            q.poll();
 
            HuffmanNode min2 = q.peek();
            q.poll();
 
            HuffmanNode toAdd = new HuffmanNode();
 
            // to the sum of the frequency of the two nodes assigning values to the f node.
            toAdd.data = min1.data + min2.data;
            toAdd.c = '-';
 
            // first extracted node as left child.
            toAdd.left = min1;
 
            // second extracted node as the right child.
            toAdd.right = min2;
 
            // marking the f node as the root node.
            root = toAdd;
 
            // add this node to the priority-queue.
            q.add(toAdd);
        }
 
        // print the codes by traversing the tree
        printCode(root, "");

        double avg=avg_length(prob,charArray);
        double e=entropy(prob);

        System.out.println("Efficiency = \t"+e/avg);

    }

    static Map<Character,String>map=new HashMap<>();
    
    public static void printCode(HuffmanNode root, String s)
    {
        //if the left and right are null then its a leaf node and we print the code s generated by traversing the tree.
        if (root.left == null && root.right == null && Character.isLetter(root.c)) 
        {
            map.put(root.c,s);
            System.out.println(root.c + ":" + s);
            return;
        }
 
        // if we go to left then add "0" to the code if we go to the right add"1" to the code
        //recursive calls for left and right sub-tree of the generated tree.
        
        printCode(root.left, s + "0");
        printCode(root.right, s + "1");
    }

    public static char[] readingFileOne() throws IOException
    {
        List<String>list=new ArrayList<>();
        BufferedReader br=new BufferedReader(new FileReader("character.txt")) ;
        String line=br.readLine();

        while (line != null) 
        {
          list.add(line);
          line = br.readLine();
        }
   
        // closing bufferreader object
        br.close();

        int n=list.get(0).length();

        char ch[]=new char[n-(n/2)];
        int pos=0;

        for(int i=0;i<ch.length;i++)
        {
            ch[i]=list.get(0).charAt(pos);
            pos+=2;
        }

        return ch;
    }

    public static int[] readingFileTwo() throws IOException
    {
        List<String>list=new ArrayList<>();
        BufferedReader br=new BufferedReader(new FileReader("frequency.txt")) ;
        String line=br.readLine();

        while (line != null) 
        {
          list.add(line);
          line = br.readLine();
        }
   
        // closing bufferreader object
        br.close();

        List<Integer>num=new ArrayList<>();
        
        String s=list.get(0);
        int start=0;
        System.out.println(s);
        

        while(start!=-1)
        {
            int end=s.indexOf(' ',start);
            end=end==-1?s.length():end;

            num.add(Integer.parseInt(s.substring(start,end)));
            start=s.indexOf(' ',start)+1;

            if(end==s.length())
            start=-1;
        }

        int f[]=new int[num.size()];

        for(int i=0;i<f.length;i++)
        f[i]=num.get(i);

        return f;
    }

    public static double[] probability(int f[])
    {
        int n=f.length; 
        double ans[]=new double[n];

        int sum=0;
        for(int i=0;i<n;i++)
        sum+=f[i];

        for(int i=0;i<n;i++)
        ans[i]=(double)f[i]/sum;

        return ans;
    }

    public static double avg_length(double prob[],char ch[])
    {
        double avg=0;

        for(int i=0;i<prob.length;i++)
        {
            
            int x=map.get(ch[i]).length();
            double p=prob[i];
            avg+=x*p;
        }

        System.out.println("Average Length = \t"+avg);

        return avg;
    }

    public static double entropy(double prob[])
    {
        double ans=0;

        for(int i=0;i<prob.length;i++)
        {
            double p=prob[i];
            double x=1/(p);
            double y=Math.log(x)/Math.log(2);
            ans+=y*p;
        }

        System.out.println("Entropy = \t"+ans);

        return ans;
    }

}


//Basic structure of each node present in the Huffman - tree.
class HuffmanNode 
{
 
    int data;
    char c;
 
    HuffmanNode left;
    HuffmanNode right;

}

// Compares the node on the basis of one of its attribute. Here comparison will be  on the basis of data values of the nodes.
class MyComparator implements Comparator<HuffmanNode> 
{
    public int compare(HuffmanNode x, HuffmanNode y)
    {
 
        return x.data - y.data;
    }
}
