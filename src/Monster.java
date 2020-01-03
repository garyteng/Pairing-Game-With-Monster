import javax.swing.*;
import java.util.TimerTask;

public class Monster {

    static class move extends TimerTask {  //for animal move
        double distance;
        JLabel player_1;
        JLabel animal;
        JLabel cage;

        public void run(){
            // get data
            player_1 = PairingWithMonster.player_1;
            animal   = PairingWithMonster.animal;
            cage     = PairingWithMonster.cage;

            distance=Math.sqrt(Math.pow(player_1.getX()-animal.getX(),2) +  Math.pow(player_1.getY()-animal.getY(),2));

            if(PairingWithMonster.found_cards ==16){
                // all pairs are found
                // this.cancel();
            }else if(distance>50){
                PairingWithMonster.animal.setLocation(
                        (int)( (animal.getX())+(player_1.getX()-animal.getX() )/distance*3.5 ) ,
                        (int)( (animal.getY())+(player_1.getY()-animal.getY() )/distance*3.5 ) );
            }else if(PairingWithMonster.blood_number>=2) {
                PairingWithMonster.blood_number-=2;
                PairingWithMonster.blood.setSize((int) PairingWithMonster.blood_number ,30);
            }

            if(PairingWithMonster.blood_number<=0){  // dead
                /*this.cancel();*/
                PairingWithMonster.blood.setBounds(3,603,600,75);
                PairingWithMonster.blood.setIcon(PairingWithMonster.loser);
            }

            if(Math.sqrt(Math.pow(animal.getX() - cage.getX(), 2)+Math.pow(animal.getY() - cage.getY(), 2) ) < 30
                    && cage.getIcon() == PairingWithMonster.weapon ){ // hit monster
                try{
                    PairingWithMonster.animal.setIcon(PairingWithMonster.animal_catch);
                    PairingWithMonster.cage.setIcon(PairingWithMonster.transparent);
                    Thread.sleep(3000);
                    PairingWithMonster.animal.setIcon(PairingWithMonster.animal_Icon);}
                catch(Exception w){}//catch

            }
        } // run()
    } // class move()
}
