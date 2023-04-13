import java.io.*;
import java.util.*;

public class ShannonFano
{
    Map<String,String>map=new HashMap<>();
    double avg_length=0;

    class Pair implements Comparable<Pair>
    {
      String ch;
      float pro;
      int top;
      int arr[];

    Pair()
    {
        top=-1;
        arr=new int[20];
    }

    Pair(String c,float p)
    {
        ch=c;
        pro=p;
        top=-1;
        arr=new int[20];
    }

    public int compareTo(Pair obj)
    {
        if(this.pro<obj.pro)
        return -1;
        
        return 1;
    }
}

void shannon(int l, int h, Pair p[])
{
    double pack1=0.0,pack2=0.0,diff1=0.0,diff2=0.0;

    int i,d,k=0,j;

    if((l+1)==h || l==h || l>h) 
    {
        if(l==h || l>h)
        return;

        p[h].arr[++(p[h].top)]=0;
        p[l].arr[++(p[l].top)]=1;
        return;
    }

    else 
    {
        for(i=l;i<=h-1;i++)
        {
            pack1=pack1+p[i].pro;
        }

        pack2=pack2+p[h].pro;

        diff1=pack1-pack2;

        if(diff1<0)
        diff1=diff1*-1;

        j=2;

        while (j!=h-l+1) 
        {
            k=h-j;

            pack1=0.0;
            pack2=0.0;

            for(i=l;i<=k;i++)
            pack1=pack1+p[i].pro;

            for (i=h;i>k;i--)
            pack2=pack2+p[i].pro;

            diff2=pack1-pack2;

            if (diff2<0)
            diff2=diff2*-1;

            if(diff2>=diff1)
            break;

            diff1=diff2;
            j++;
        }

        k++;
        for (i=l;i<=k;i++)
        p[i].arr[++(p[i].top)]=1;

        for(i=k+1;i<=h;i++)
        p[i].arr[++(p[i].top)]=0;
 
        // Invoke shannon function
        shannon(l,k,p);
        shannon(k+1,h,p);
    }
}

public void display(int n,Pair p[])
{
    int i, j;
    System.out.println("\n\n\n\tSymbol\t\tProbability\tCode");

    for(i=n-1; i>= 0; i--) 
    {
        String x="";
        System.out.print("\t" + p[i].ch + " \t\t" + p[i].pro  + "\t");
        
        for (j=0;j<=p[i].top;j++)
        x+=p[i].arr[j];

        double avg=p[i].pro*x.length();
        avg_length+=avg;

        map.put(p[i].ch,x);

        System.out.print(x);
        
        System.out.println();
    }
}

public String helpRead2() throws IOException
{
    List<String>list=new ArrayList<>();
    BufferedReader br=new BufferedReader(new FileReader("ShannonInput2.txt")) ;
    String line=br.readLine();

    while (line != null) 
    {
        list.add(line);
        line = br.readLine();
    }
   
    // closing bufferreader object
    br.close();

    String ans="";
    for(int i=0;i<list.size();i++)
    ans+=list.get(i);

    return ans;
}


public Pair[] helpRead1() throws IOException
{
    int n=0,i;
    int freq[]=new int[26];

    List<String>list=new ArrayList<>();
    BufferedReader br=new BufferedReader(new FileReader("ShannonInput1.txt")) ;
    String line=br.readLine();

    while (line != null) 
    {
        list.add(line);
        line = br.readLine();
    }
   
    // closing bufferreader object
    br.close();


    String arr[]= list.toArray(new String[0]);
    for (int w=0;w<arr[0].length();w++) 
    {
        if(arr[0].charAt(w)!=' ')
        {
            char c=arr[0].charAt(w);
            freq[c-'A']++;
        }
    }

   int totalChar=0;

   for(int o=0;o<26;o++)
   {
       if(freq[o]>0)
       {
          totalChar+=freq[o];
          n++;
       }
   }
    
   Pair a[]=new Pair[n];
   int y=0;

   for (i = 0;i<26;i++) 
    {
       if(freq[i]>0){
        a[y]=new Pair();

        String temp="";
       
        temp+=(char)(i+'A');
        a[y++].ch=temp;
       }
    }

    // Input probability of symbols
        y=0;
        for (i = 0;i<26;i++) 
        {
            if(freq[i]>0)
            {
                a[y++].pro =((float)freq[i])/(totalChar);
            }
        }

        return a;

}

public void helper() throws IOException
{
    
    Pair a[]=helpRead1();
    String s=helpRead2();
    
    int n=a.length;

    Arrays.sort(a);
    
    shannon(0,n-1,a);
    display(n,a);

    String ans="";
    for(int q=0;q<s.length();q++)
    {
        ans+=map.get(s.charAt(q)+"");
    }
    
    System.out.println();
    System.out.println();
    System.out.println("Compressed Code = "+ans);
    
    int initial_memory_req=8*s.length();
    double final_memory_req=avg_length*s.length();
    double compression_ratio=initial_memory_req/final_memory_req;


    System.out.println("Bits required to represent original data = "+initial_memory_req);
    System.out.println("Bits required to represent compressed data = "+final_memory_req);
    System.out.println("Compressed Ratio = "+compression_ratio);

}


/**
 * @throws IOException
 */
public void createCSV() throws IOException
{
    String csvFile = "025_symbol_table.csv";
    FileWriter writer = new FileWriter(csvFile);

    writer.append("Symbol,Code");
    writer.append("\n");

    for (String symbol : map.keySet()) 
    {
        writer.append(symbol);
        writer.append(",");
        writer.append(map.get(symbol));
        writer.append("\n");
    }

    writer.close();
}

    public static void main(String[] args) throws IOException
    {
        ShannonFano obj=new ShannonFano();
        obj.helper();
        obj.createCSV();
    }
}