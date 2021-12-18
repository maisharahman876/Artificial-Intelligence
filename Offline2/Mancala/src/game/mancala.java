package game;

import AI.Bot;

import java.util.Collections;
import java.util.Scanner;
import java.util.Vector;

public class mancala {

   int[] board;
   public int up_score,down_score;
    int turn;
    public String p1,p2;
    public int extra1,extra2;
   public int additional1;
    public int additional2;
   public mancala(String p1, String p2,int turn)
    {
    up_score=0;
    down_score=0;
    board=new int[12];
    for(int i=0;i<12;i++)
        board[i]=4;
        this.turn=turn;         //turn 1 hole player,0 hole AI.
        this.p1=p1;
        this.p2=p2;
        additional1=0;
        additional1=0;
    }
    public int my_turn(String s)
    {
        if(s.equalsIgnoreCase(p1))
            return 0;
        else
            return 1;
    }
    public int getTurn()
    {
        return turn;
    }
    public int[] get_board()
    {
        return board;
    }
    public void print()
    {
        System.out.println();

        for (int i = 0; i < 7; i++)
        {

            System.out.print("**************");

            if(i==6)
            {
                System.out.print("*************\n");
            }
        }

        System.out.print("|");

        for (int i = 0; i < 7; i++)
        {
            if(i!=0)
            {
                System.out.print("     " +(this.board[i-1] < 10 ? "0" : "")+ this.board[i-1] + "      |");
            }
            else
            {
                System.out.print("            |");
            }

            if(i==6)
            {
                System.out.println("            |");
            }
        }

        for (int i = 0; i < 7; i++)
        {
            if(i==0)
            {
                System.out.print("|    "+(up_score < 10 ? "0" : "")+up_score+"      ");
            }
            else if(i==6)
            {
                System.out.print("|*************|");
            }
            else
            {
                System.out.print("|*************");
            }


            if(i==6)
            {
                //System.out.print("=============\"\n");
                System.out.print("      "+(down_score < 10 ? "0" : "")+down_score+"    |\n");
            }
        }

        System.out.print("|");

        for (int i = 5; i < 12; i++)
        {
            if(i!=5)
            {
                System.out.print("     "+(this.board[i] < 10 ? "0" : "")+ this.board[i] +"      |");
            }
            else
            {
                System.out.print("            |");
            }


            if(i==11)
            {
                System.out.println("            |");
            }
        }

        for (int i = 0; i < 7; i++)
        {

            System.out.print("**************");

            if(i==6)
            {
                System.out.print("*************\n");
            }
        }
    }

public int total_stone_on_side(int p)
{
    int sum=0;
    if(p==0)
    {
        for(int i=0;i<6;i++)
        {
            sum+=board[i];
        }
    }
    else
    {

        for(int i=6;i<12;i++)
        {
            sum+=board[i];
        }
    }
    return sum;
}
public void setBoard(int[] arr,int up_score,int down_score,int turn,int add1,int add2,int ex1,int ex2)
{
    this.extra1=ex1;
    this.extra2=ex2;

    for(int i=0;i<12;i++)
    {
       this.board[i]=arr[i];
    }
    this.up_score=up_score;
    this.down_score=down_score;
    this.turn=turn;
    this.additional1=add1;
    this.additional2=add2;

}
public Vector<Integer> moves(int p)
{
    Vector<Integer> arr=new Vector<>();
    if(p==0)
    {
        for(int i=0;i<6;i++)
        {
            if(this.board[i]!=0)
            {
                arr.addElement(i+1);
            }
        }
    }
    else
    {
        for(int i=0;i<6;i++)
        {
            if(this.board[6+i]!=0)
            {
                arr.addElement(i+1);
            }
        }
    }
    Collections.shuffle(arr);
    return arr;
}
    public void give_up_move(int u)
    {
        int pos=u-1,factor=-1;
        int size=board[u-1];
        //System.out.println(board.size());
        board[u-1]=0;
        for(int i=0;i<size;i++)
        {
            pos+=factor;
            if(pos==-1) {
                up_score++;

                if(i!=size-1)
                    pos=5;
                factor=1;
                turn=0;
            }
            else if(pos==12)
            {
                factor=-1;
                pos=5;
                int n=this.board[pos]+1;
                this.board[pos]=n;
                turn=1;

            }
            else
            {
                int n=this.board[pos]+1;
                this.board[pos]=n;
                turn=1;
            }

        }
        if(pos!=-1) {

            if (pos / 6 == 0 && this.board[pos] == 1 && this.board[pos + 6] != 0) {
                up_score += this.board[6 + pos] + 1;
                extra1 += this.board[pos + 6] + 1;
                this.board[pos] = 0;
                this.board[6 + pos] = 0;
            }
        }
        else
            additional1++;
    }
    public void give_down_move(int u)
    {
        int pos=5+u,factor=1;
        int size=board[pos];
        //System.out.println(board.size());
        board[pos]=0;
        for(int i=0;i<size;i++)
        {
            pos+=factor;
            if(pos==12) {
                down_score++;

                if(i!=size-1) {
                    pos = 6;
                    factor = -1;
                }
                turn=1;
            }
            else if(pos==-1)
            {
                factor=1;

                pos=6;
                int n=this.board[pos]+1;
                this.board[pos]=n;
                turn=0;

            }
            else
            {
                int n=this.board[pos]+1;
                this.board[pos]=n;
                turn=0;
            }

        }
        if(pos==12)
            additional2++;
        if(pos/6==1&&this.board[pos]==1&&this.board[pos-6]!=0&&pos!=12)
        {
            down_score+=this.board[pos-6]+1;
            extra2+=this.board[pos-6]+1;
            this.board[pos]=0;
            this.board[pos-6]=0;
        }


    }
    public boolean IsGameOver()
    {
        int s=0;
        for(int i=0;i<6;i++)
            s+=this.board[i];
        if(s==0)
            return true;
        s=0;
        for(int i=0;i<6;i++)
            s+=this.board[6+i];
        if(s==0)
            return true;

        return false;
    }
    public void verdict()
    {
        for(int i=0;i<6;i++)
        {
            up_score+=board[i];
            board[i]=0;
        }

        for (int i=6;i<12;i++)
        {
            down_score+=board[i];
            board[i]=0;
        }


        if(up_score>down_score)
            System.out.println(p1+" Wins!");
        else if(up_score==down_score)
            System.out.println("Draw Happened");
        else
            System.out.println(p2+" Wins!");
        print();
    }


}
