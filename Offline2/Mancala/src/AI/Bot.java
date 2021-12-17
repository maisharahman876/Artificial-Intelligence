package AI;

import game.mancala;

import java.util.Vector;

public class Bot {
    mancala m;
    int depth_limit;

    int max_player;
    int move;
    String name;
    int my_turn;
    int heuristic;
    public int [] copy(mancala m1)
    {
        int [] arr=new int[12];
        int[] arr1=m1.get_board();
        for(int i=0;i<12;i++)
        {
            arr[i]=arr1[i];
        }
    return arr;
    }
    public Bot(mancala m,int d,String name,int h)
    {
        this.m=m;
        this.name=name;
        depth_limit=d;
        max_player=1;
        move=-1;
        my_turn=m.my_turn(this.name);
        heuristic=h;

    }
    public int get_move()
    {
        int[] arr=copy(this.m);
        move=-1;
        int max_val=-Integer.MAX_VALUE;
        mancala m1=new mancala(m.p1,m.p2,0);
        m1.setBoard(arr,this.m.up_score,this.m.down_score,this.m.getTurn(),this.m.additional1,m.additional2,m.extra1,m.extra2);
        int alpha =-Integer.MAX_VALUE,beta=Integer.MAX_VALUE;
        //int n=mini_max_with_pruning(m1,depth_limit,max_player,alpha,beta);

        Vector<Integer> moves=m1.moves(my_turn);

        for(int i=0;i<moves.size();i++)
        {
            mancala m2=new mancala(m1.p1,m1.p2,0);
            m2.setBoard(copy(m1),m1.up_score,m1.down_score,m1.getTurn(),m1.additional1, m1.additional2,m1.extra1,m1.extra2);

            if(my_turn==0)
                m2.give_up_move(moves.get(i));
            else
                m2.give_down_move(moves.get(i));
            int n;
            if(m2.getTurn()==my_turn)
                n=mini_max_with_pruning(m2,depth_limit-1,1,alpha,beta);
            else
                n=mini_max_with_pruning(m2,depth_limit-1,0,alpha,beta);
            if(max_val<n)
            {
                max_val=n;
                move=moves.get(i);
                //System.out.println(move);
            }

            if(alpha<max_val)
            {
                alpha=max_val;
                //move=moves.get(i);
            }
        }
        return move;
    }
    public int mini_max_with_pruning(mancala m1,int depth,int max_player,int a,int b)
    {
        if(depth==0||m1.IsGameOver())
        {
            if(heuristic==1)
            return h1(m1,max_player);
            else if(heuristic==2)
                return h2(m1,max_player);
            else if(heuristic==3)
                return h3(m1,max_player);
            else if(heuristic==4)
                return h4(m1,max_player);
            else if(heuristic==5)
                return h5(m1,max_player);
            else if(heuristic==6)
                return h6(m1,max_player);
        }
        if(max_player==1)
        {
            int max_val=-Integer.MAX_VALUE;
            Vector<Integer> moves=m1.moves(my_turn);//AI=0,Player=1

            for(int i=0;i<moves.size();i++)
            {
                mancala m2=new mancala(m1.p1,m1.p2,0);
                m2.setBoard(copy(m1),m1.up_score,m1.down_score,m1.getTurn(),m1.additional1,m1.additional2,m1.extra1,m1.extra2);
                if(my_turn==0)
                    m2.give_up_move(moves.get(i));
                else
                    m2.give_down_move(moves.get(i));
                int n;
                if(m2.getTurn()==my_turn)
                     n=mini_max_with_pruning(m2,depth-1,1,a,b);
                else
                    n=mini_max_with_pruning(m2,depth-1,0,a,b);
                if(max_val<n)
                {
                    max_val=n;
                   // move=moves.get(i);
                    //System.out.println(move);
                }

                if(a<max_val)
                {
                    a=max_val;
                    //move=moves.get(i);
                }

                if(b<=a)
                    break;
            }
            return max_val;

        }
        else
        {
            int min_val=Integer.MAX_VALUE;
            Vector<Integer> moves=m1.moves(1-my_turn);//AI=0,Player=1
            for(int i=0;i<moves.size();i++)
            {
                mancala m2=new mancala(m1.p1,m1.p2,0);
                m2.setBoard(copy(m1),m1.up_score,m1.down_score,m1.getTurn(),m1.additional1,m1.additional2,m1.extra1,m1.extra2);
                if(my_turn==1)
                    m2.give_up_move(moves.get(i));
                else
                    m2.give_down_move(moves.get(i));

                int n;
                if(m2.getTurn()==1-my_turn)
                    n=mini_max_with_pruning(m2,depth-1,0,a,b);
                else
                    n=mini_max_with_pruning(m2,depth-1,1,a,b);
                if(min_val>n)
                {
                    min_val=n;
                   // move=moves.get(i);
                }

                if(b>min_val)
                    b=min_val;
                if(b<=a)
                    break;
            }
            return min_val;
        }

    }
    public int h1(mancala m1,int p)
    {
        if(p==1)
        {
            if(my_turn==0)
                return m1.up_score-m1.down_score;
            else
                return m1.down_score-m1.up_score;

        }

        else {
            if (1 - my_turn == 0)
                return m1.up_score - m1.down_score;
            else
                return m1.down_score - m1.up_score;

        }
    }
    public int h2(mancala m1, int p)
    {
        int s,s1;

        s=m1.total_stone_on_side(my_turn);
        s1=m1.total_stone_on_side(1-my_turn);
        if(p==1)
        {
            if(my_turn==0)
                return 7*(m1.up_score-m1.down_score)+3*(s-s1);
            else
                return 7*(m1.down_score-m1.up_score)+3*(s-s1);
        }

        else
        {
            if(my_turn==1)
                return 7*(m1.up_score-m1.down_score)+3*(s1-s);
            else
                return 7*(m1.down_score-m1.up_score)+3*(s1-s);
        }

    }
    public int h3(mancala m1, int p)
    {
        if(p==1)
        {
            if(my_turn==0)
                return h2(m1,p)+11*m1.additional1;
            else
                return h2(m1,p)+11*m1.additional2;
        }
        else {
            if(my_turn==0)
                return h2(m1,p)+11*m1.additional2;
            else
                return h2(m1,p)+11*m1.additional1;

        }


    }
    public int h4(mancala m1,int p)
    {
        if(p==1)
        {
            if(my_turn==0)
                return h2(m1,p)+11*(m1.additional1-m1.additional2);
            else
                return h2(m1,p)+11*(m1.additional2-m1.additional1);
        }
        else {
            if(my_turn==0)
                return h2(m1,p)+11*(m1.additional2-m1.additional1);
            else
                return h2(m1,p)+11*(m1.additional1-m1.additional2);

        }
    }
    public int h5(mancala m1,int p)
    {
        if(p==1)
        {
            if(my_turn==0)
                return h3(m1,p)+11*(m1.additional1-m1.additional2)+7*m1.extra1;
            else
                return h3(m1,p)+11*(m1.additional2-m1.additional1)+7*m1.extra2;
        }
        else {
            if(my_turn==0)
                return h3(m1,p)+11*(m1.additional2-m1.additional1)+7*m1.extra2;
            else
                return h3(m1,p)+11*(m1.additional1-m1.additional2)+7*m1.extra1;

        }
    }
    public int h6(mancala m1,int p)
    {
        if(p==1)
        {
            if(my_turn==0)
                return h3(m1,p)+11*(m1.additional1-m1.additional2)+7*(m1.extra1-m1.extra2);
            else
                return h3(m1,p)+11*(m1.additional2-m1.additional1)+7*(m1.extra2-m1.extra1);
        }
        else {
            if(my_turn==0)
                return h3(m1,p)+11*(m1.additional2-m1.additional1)+7*(m1.extra2-m1.extra1);
            else
                return h3(m1,p)+11*(m1.additional1-m1.additional2)+7*(m1.extra1-m1.extra2);

        }
    }


}
