import javax.swing.*;
import java.util.TimerTask;

public class Player {

    static class playerTimer extends TimerTask {
        //times member represent calling times.
        // private int times = 0;

        double distance;  //calculate distance
        double x_diff, y_diff;

        JLabel ball_1;
        JLabel player_1;

        public void run() {

            // get data
            ball_1   = PairingWithMonster.ball_1;
            player_1 = PairingWithMonster.player_1;

            x_diff =ball_1.getX()- player_1.getX(); // x-axis diff
            y_diff =ball_1.getY()- player_1.getY(); // y-axis diff

            distance=Math.sqrt(Math.pow(ball_1.getX()- player_1.getX(),2)+Math.pow(ball_1.getY()- player_1.getY(),2)) ; //calculate distance

            PairingWithMonster.sin= y_diff /distance;  // get sin
            PairingWithMonster.cos= x_diff /distance;  // get cos

            if(PairingWithMonster.found_cards ==16){ // end
    		    /*try{Thread.sleep(1800);}
    	        catch(Exception e){}
    	        System.exit(0);*/
            }else if(PairingWithMonster.blood_number==0){
                /* out of blood */
            }else if(distance<=20){
                PairingWithMonster.ball_1.setIcon(PairingWithMonster.transparent);
            }else {// going
                x_diff = PairingWithMonster.player_1.getX()+5* PairingWithMonster.cos;
                y_diff = PairingWithMonster.player_1.getY()+5* PairingWithMonster.sin;
                PairingWithMonster.player_1.setLocation( (int) x_diff,(int) y_diff);
            }

            if(PairingWithMonster.blood_number<=0){  // already die

                PairingWithMonster.player_1.setIcon(PairingWithMonster.cry);

    	        /*try{Thread.sleep(2800);}
    	        catch(Exception e){}
    		    System.exit(0);*/
            }

        }//run() end
    }//f1timer end

    // timer for player throwing weapon (ball), Press "Q"
    static class throwBall extends TimerTask{
        int time=30;
        double f_x, f_y;

        public void run(){

            f_x= PairingWithMonster.f1_weapon_sin;
            f_y= PairingWithMonster.f1_weapon_cos;

            if(time<0){
                PairingWithMonster.Q_able=true; // enable weapon
                PairingWithMonster.cage.setLocation(50,700);
                PairingWithMonster.cage.setIcon(PairingWithMonster.transparent);
                this.cancel();
            }else if(PairingWithMonster.cage.getY()>550){ // out of boundary
                time--;
            }else{  // keep going
                time--;
                PairingWithMonster.cage.setLocation(
                        (int)(PairingWithMonster.cage.getX()+10*f_y) ,
                        (int)(PairingWithMonster.cage.getY()+10*f_x) );
            } // if
        }
    }

    // player's teleport timer, Press "E"
    static class teleport extends TimerTask {
        int time=5; //use once per fi ve seconds

        public void run(){
            time--;
            if(time<=0){
                PairingWithMonster.W_able=true; // enable to use teleport
                this.cancel();
            }
        }
    }

    // player flip card, Press "E"
    static class flipCard extends TimerTask{
        int time=1; // disable one second
        int[] check;
        int[] hidden_cards_number;

        public void run(){
            // get data
            check = PairingWithMonster.check; // flipped cards
            hidden_cards_number = PairingWithMonster.hidden_cards_number;

            time--;
            if(time<=0){

                if( hidden_cards_number[check[0]] == hidden_cards_number[check[1]] ){  // same card
                    PairingWithMonster.cards[check[0]].setIcon(PairingWithMonster.finish);
                    PairingWithMonster.cards[check[1]].setIcon(PairingWithMonster.finish);

                    if((PairingWithMonster.found_cards +2)==16){
                        PairingWithMonster.blood.setBounds(3,603,600,75); // reset bounds size
                        PairingWithMonster.blood.setIcon(PairingWithMonster.winner);
                        PairingWithMonster.player_1.setIcon(PairingWithMonster.f1); // reset player
                        PairingWithMonster.animal.setIcon(PairingWithMonster.animal_Icon); // reset monster
                    }

                    PairingWithMonster.found_cards +=2;

                }else{  // different card
                    PairingWithMonster.cards[check[0]].setIcon( PairingWithMonster.question );
                    PairingWithMonster.cards[check[1]].setIcon( PairingWithMonster.question );
                }

                PairingWithMonster.E_able=true; // enable "E"
                this.cancel();
            }
        } // run()
    }
}
