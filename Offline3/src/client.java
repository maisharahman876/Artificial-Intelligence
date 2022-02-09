import java.util.Scanner;

public class client {
    public static void main(String[] args) {
        int m,n,obs;
        Scanner sc=new Scanner(System.in);
        m=sc.nextInt();
        n=sc.nextInt();
        obs=sc.nextInt();
        HMM grid=new HMM(m,n,obs);
        for(int i=0;i<obs;i++)
        {
            int x,y;
            x=sc.nextInt();
            y=sc.nextInt();
            grid.add_obstacle(x,y);
        }
        System.out.println("Initial Probability:");
        int i=1;
        grid.print();
        while(true)
        {
            String s;
            s=sc.next();

            if(s.equalsIgnoreCase("R"))
            {
                int ex,ey,val;
                ex=sc.nextInt();
                ey=sc.nextInt();
                val=sc.nextInt();

                System.out.println("Probability Update ("+i+" no Reading):");
                i++;
                grid.print();

            }
            else if(s.equalsIgnoreCase("C"))
                grid.print_result();
            else  if(s.equalsIgnoreCase("Q"))
            {
                System.out.println("Bye Casper!");
                break;
            }

        }

    }
}
