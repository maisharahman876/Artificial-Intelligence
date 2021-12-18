package Client;

import AI.Bot;
import game.mancala;

import java.util.Scanner;

public class match {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.println("1.Player vs AI");
        System.out.println("2.AI vs AI");
        System.out.print("Enter Your choice:");
        int ch=sc.nextInt();
        int turn;
        mancala m;
        if(ch==1)
        {
            System.out.println("Who will move First?");

            System.out.println("1.AI");
            System.out.println("2.Player");

            System.out.print("Enter Your choice:");
            turn=sc.nextInt();

            m=new mancala("AI","Player",turn-1);
            //m.print();
            System.out.print("Enter the depth of AI: " );
            int d= sc.nextByte();
            System.out.println("1. (stones_in_my_storage – stones_in_opponents_storage)");
            System.out.println("2. 3 * (stones_in_my_storage – stones_in_opponents_storage) + (stones_on_my_side – stones_on_opponents_side)");
            System.out.println("3. 3 * (stones_in_my_storage – stones_in_opponents_storage) + (stones_on_my_side – stones_on_opponents_side)\n        + 5 * (additional_move_earned)");
            System.out.println("4. 3 * (stones_in_my_storage – stones_in_opponents_storage) + (stones_on_my_side – stones_on_opponents_side)\n      + 2 * (additional_move_earned-additional_move_earnd_by opponent)");
            System.out.println("5. 3 * (stones_in_my_storage – stones_in_opponents_storage) + (stones_on_my_side – stones_on_opponents_side)\n      + 2 * (additional_move_earned-additional_move_earnd_by opponent) +  earned_capture");
            System.out.println("6. 3 * (stones_in_my_storage – stones_in_opponents_storage) +  (stones_on_my_side – stones_on_opponents_side)\n      + 2 (additional_move_earned-additional_move_earnd_by opponent) + 2 * (earned_capture_by me - earned_capture_by opponent");
            System.out.print("Enter heuristic of AI1: " );
            System.out.print("Enter heuristic of AI: " );

            Bot AI=new Bot(m,d,"AI",sc.nextInt());
            m.print();
            int up_move,down_move;
            while(true) {
                if (m.IsGameOver()) {

                    m.verdict();
                    break;
                }
                else {
                    if (m.getTurn() == 0) {

                        up_move = AI.get_move();
                        System.out.println("AI Move: "+up_move);
                        m.give_up_move(up_move);
                        m.print();
                    } else {
                        System.out.println("Please Give your move(1-6): ");
                        down_move = sc.nextInt();
                        m.give_down_move(down_move);
                        m.print();
                    }

                }
            }
        }
        else
        {
            System.out.println("Who will move First?");

            System.out.println("1.AI 1");
            System.out.println("2.AI 2");

            System.out.print("Enter Your choice:");
            turn=sc.nextInt();

            m=new mancala("AI1","AI2",turn-1);

            System.out.print("Enter the depth of AI1: " );
            int d1= sc.nextByte();
            System.out.print("Enter the depth of AI2: " );
            int d2= sc.nextByte();
            System.out.println("1. (stones_in_my_storage – stones_in_opponents_storage)");
            System.out.println("2. 3 * (stones_in_my_storage – stones_in_opponents_storage) + (stones_on_my_side – stones_on_opponents_side)");
            System.out.println("3. 3 * (stones_in_my_storage – stones_in_opponents_storage) + (stones_on_my_side – stones_on_opponents_side)\n        + 5 * (additional_move_earned)");
            System.out.println("4. 3 * (stones_in_my_storage – stones_in_opponents_storage) + (stones_on_my_side – stones_on_opponents_side)\n      + 2 * (additional_move_earned-additional_move_earnd_by opponent)");
            System.out.println("5. 3 * (stones_in_my_storage – stones_in_opponents_storage) + (stones_on_my_side – stones_on_opponents_side)\n      + 2 * (additional_move_earned-additional_move_earnd_by opponent) +  earned_capture");
            System.out.println("6. 3 * (stones_in_my_storage – stones_in_opponents_storage) +  (stones_on_my_side – stones_on_opponents_side)\n      + 2 (additional_move_earned-additional_move_earnd_by opponent) + 2 * (earned_capture_by me - earned_capture_by opponent");
            System.out.print("Enter heuristic of AI1: " );
            int h1=sc.nextInt();
            System.out.print("Enter heuristic of AI2: " );
            int h2=sc.nextInt();

            Bot AI=new Bot(m,d1,"AI1",h1);
            Bot AI2=new Bot(m,d2,"AI2",h2);
            m.print();
            int up_move,down_move;
            while(true) {
                if (m.IsGameOver()) {

                    m.verdict();
                    break;
                }
                else {
                    if (m.getTurn() == 0) {

                        up_move = AI.get_move();
                        System.out.println("AI1 Move: "+up_move);
                        m.give_up_move(up_move);
                        m.print();
                    } else {
                        down_move = AI2.get_move();;
                        System.out.println("AI2 Move: "+down_move);

                        m.give_down_move(down_move);
                        m.print();
                    }

                }
            }

        }



    }
}
