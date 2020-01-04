import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyMouseEvents {

    static class MouseLis extends MouseAdapter {

        JLabel ball_1;
        JLabel player_1;
        double distance;

        public MouseLis(){
            // Get Data
            ball_1   = PairingWithMonster.ball_1;
            player_1 = PairingWithMonster.player_1;
        }

        public void mousePressed(MouseEvent e){

            ball_1.setLocation(e.getX()-50 ,e.getY()-48 );
            ball_1.setIcon(PairingWithMonster.f1_ball);

            distance = Math.sqrt(Math.pow(ball_1.getX()- player_1.getX(),2)+
                                 Math.pow(ball_1.getY()- player_1.getY(),2) );

            PairingWithMonster.f1_weapon_cos=(
                    ball_1.getX()- player_1.getX()) / distance;

            PairingWithMonster.f1_weapon_sin=(
                    ball_1.getY()- player_1.getY()) / distance;

        }
    }

    static class Mouse_start extends MouseAdapter{

        int hidden_cards_number[];
        ImageIcon[] hidden_cards;

        JLabel player_1;
        JLabel blood;

        public Mouse_start(){
            hidden_cards_number = PairingWithMonster.hidden_cards_number;
            hidden_cards        = PairingWithMonster.hidden_cards;
            player_1            = PairingWithMonster.player_1;
            blood               = PairingWithMonster.blood;
        }

        public void mouseClicked(MouseEvent z){
            if(PairingWithMonster.start==(JButton)z.getSource() ){

                for(int i=0;i<100;i++){  // shuffle cards
                    //swap(array[(int)(Math.random()*16) ],array[(int)(Math.random()*16)] );
                    int aa=0,bb=0,cc=0;
                    aa=(int)(Math.random()*16); bb=(int)(Math.random()*16);
                    cc= hidden_cards_number[aa];
                    hidden_cards_number[aa]= hidden_cards_number[bb];
                    hidden_cards_number[bb]=cc;
                }

                for(int i=0;i<16;i++){   // update shuffle cards
                    hidden_cards[i]=new ImageIcon(PairingWithMonster.class.getResource(PairingWithMonster.image_names[hidden_cards_number[i] ]));
                }

                for(int i=0;i<16;i++){
                    PairingWithMonster.cards[i].setIcon(PairingWithMonster.question);
                }

                player_1.setIcon(PairingWithMonster.f1); //reset player
                player_1.setLocation(70,70);   // reset player location
                PairingWithMonster.animal.setLocation(300,300);// reset monster location
                PairingWithMonster.ball_1.setLocation(70,70);  // reset coin location

                blood.setBounds(20,630,550,30); // reset remaining blood
                blood.setIcon(PairingWithMonster.blood_Icon); //reset blood
                PairingWithMonster.blood_number=550; // refill blood

                PairingWithMonster.found_cards =0; // reset number of finded pairs
            }else{
                System.exit(0);
            }
        }
    }


}
