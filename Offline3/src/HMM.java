import java.text.DecimalFormat;

public class HMM {
    int m,n;
    double [][]grid;
    public HMM(int m,int n,int obstacles)
    {
        this.m=m;
        this.n=n;
        grid=new double[m][n];
        double prob=(1.0/(double)((n * m) -obstacles)) ;
        for(int i=0;i<m;i++)
            for(int j=0;j<n;j++)
                grid[i][j]=prob;
    }
    public void add_obstacle(int x, int y)
    {
        grid[x][y]=0.0;
    }
    public void print()
    {
        DecimalFormat df = new DecimalFormat("0.0000000");
        for(int i=0;i<m;i++)
        {

            for(int j=0;j<n;j++)
            {
                System.out.print(df.format(grid[i][j])+"  ");
            }
            System.out.println();
        }

    }
    public double sum()
    {
        double sum=0.0;
        for(int i=0;i<m;i++)
        {

            for(int j=0;j<n;j++)
            {
               sum+=grid[i][j];
            }

        }
        return sum;

    }
    public void print_result()
    {
        double max=-Double.MAX_VALUE;
        int x=0,y=0;
        for(int i=0;i<m;i++)
        {

            for(int j=0;j<n;j++)
            {
                if(max<grid[i][j])
                {
                    max=grid[i][j];
                    x=i;
                    y=j;
                }
            }

        }
        System.out.println("Casper is most probably at "+x+","+y);
    }
    double ang_prob(int x,int y)
    {
        int count=1;
        if(x-1>=0&&y-1>=0)
            if(grid[x-1][y-1]!=0)
            {
                count++;
            }
        if(x-1>=0&&y+1<n)
            if(grid[x-1][y+1]!=0.0)
            {
                count++;
            }
        if(x+1<m&&y-1>=0)
            if(grid[x+1][y-1]!=0.0)
            {
                count++;
            }
        if(x+1<m&&y+1<n)
            if(grid[x+1][y+1]!=0.0)
            {
                count++;
            }
       // System.out.println("0.1/"+count);
        return (0.1/(double)count);
    }
    double side_prob(int x,int y)
    {
        int count=0;
        if(x-1>=0)
            if(grid[x-1][y]!=0.0)
            {
                count++;
            }
        if(x+1<m)
            if(grid[x+1][y]!=0.0)
            {
                count++;
            }
        if(y-1>=0)
            if(grid[x][y-1]!=0.0)
            {
                count++;
            }
        if(y+1<n)
            if(grid[x][y+1]!=0.0)
            {
                count++;
            }
        //System.out.println("0.9/"+count);
        return (0.9/(double)count);
    }
    private double transition_prob(int x,int y,double [][] temp)
    {
        double sum=0.0;

        if(x-1>=0&&y-1>=0)
        {
            if(grid[x-1][y-1]!=0.0)
            sum+=ang_prob(x-1,y-1)*temp[x-1][y-1];
        }
        if(x-1>=0&&y+1<n)
            if(grid[x-1][y+1]!=0.0)
            sum+=ang_prob(x-1,y+1)*temp[x-1][y+1];
        if(x+1<m&&y-1>=0)
            if(grid[x+1][y-1]!=0.0)
            sum+=ang_prob(x+1,y-1)*temp[x+1][y-1];
        if(x+1<m&&y+1<n)
            if(grid[x+1][y+1]!=0.0)
            sum+=ang_prob(x+1,y+1)*temp[x+1][y+1];
        if(x-1>=0)
            if(grid[x-1][y]!=0.0)
            sum+=side_prob(x-1,y)*temp[x-1][y];
        if(x+1<m)
            if(grid[x+1][y]!=0.0)
            sum+=side_prob(x+1,y)*temp[x+1][y];
        if(y-1>=0)
            if(grid[x][y-1]!=0.0)
            sum+=side_prob(x,y-1)*temp[x][y-1];
        if(y+1<n)
            if(grid[x][y+1]!=0.0)
            sum+=side_prob(x,y+1)*temp[x][y+1];

        sum+=ang_prob(x,y)*temp[x][y];
       // System.out.println();
        return sum;
    }
    public void update(int ex, int ey, int val)
    {
        double temp[][]=new double[m][n];
        for(int i=0;i<m;i++)
        {

            for(int j=0;j<n;j++)
            {
               temp[i][j]=grid[i][j];
            }

        }
        for(int i=0;i<m;i++)
        {

            for(int j=0;j<n;j++)
            {
                if(grid[i][j]*1000!=0.0)
                     grid[i][j]=transition_prob(i,j,temp);
            }

        }
       // print();
        int x=ex,y=ey;

        for(int i=0;i<m;i++)
        {

            for(int j=0;j<n;j++)
            {

                    if(val==0)
                    {
                        if(adj(i,j,x,y))
                           grid[i][j]*=0.15;
                        else
                            grid[i][j]*=0.85;

                    }
                    else
                    {
                        if(adj(i,j,x,y))
                            grid[i][j]*=0.85;
                        else
                            grid[i][j]*=0.15;

                    }
                }




        }

        double sum=sum();

        for(int i=0;i<m;i++)
        {
            for(int j=0;j<n;j++)
            {
                grid[i][j]/=sum;
            }
        }
       // System.out.println(sum());


    }
    boolean adj(int i,int j,int x, int y)
    {
        if (i+1==x&&j==y)
            return true;
        if (i-1==x&&j==y)
            return true;
        if (i==x&&j+1==y)
            return true;
        if (i==x&&j-1==y)
            return true;
        if (i-1==x&&j-1==y)
            return true;
        if (i-1==x&&j+1==y)
            return true;
        if (i+1==x&&j+1==y)
            return true;
        if (i+1==x&&j-1==y)
            return true;
        if (i==x&&j==y)
            return true;
        return false;
    }
}
