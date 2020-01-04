import javafx.util.Pair;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MyKeyEvents {

    static class KeyLis extends KeyAdapter {
        double key_sin, key_cos;
        int id;

        JLabel cage;
        JLabel player_1;
        int[] check;
        JLabel cards[];
        ImageIcon[] hidden_cards;
        double f1_weapon_sin, f1_weapon_cos;

        public KeyLis(){
            // Get Data
            player_1 = PairingWithMonster.player_1;
            cage     = PairingWithMonster.cage;

            check = PairingWithMonster.check; // flipped cards
            cards = PairingWithMonster.cards;
            hidden_cards = PairingWithMonster.hidden_cards;
        }

        public void keyPressed(KeyEvent G){
            id=G.getKeyCode();


            key_sin= PairingWithMonster.sin;
            key_cos= PairingWithMonster.cos;

            f1_weapon_sin = PairingWithMonster.f1_weapon_sin;
            f1_weapon_cos = PairingWithMonster.f1_weapon_cos;


            if(id==KeyEvent.VK_T){  // shut down program
                PairingWithMonster.frm.dispose();
                System.exit(0);
            }

            if(id==KeyEvent.VK_Q && PairingWithMonster.Q_able){ // press "Q"
                cage.setLocation(player_1.getX(), player_1.getY() );
                cage.setIcon(PairingWithMonster.weapon);
                PairingWithMonster.weapon_timer.schedule(new Player.throwBall(),10,31 );//timer start
                PairingWithMonster.Q_able=false; // disable "Q"
            }  //key Q end

            if(id==KeyEvent.VK_W && PairingWithMonster.W_able){// "W" is pressed, teleport

                if(player_1.getX()+150*(f1_weapon_cos)+50<=10 || player_1.getX()+150*(f1_weapon_cos)+50>=590){
                    // Will go out of border
                    for(int i=0;i<=150;i+=3){ // use for to find location within border

                        if( player_1.getX()+i*(f1_weapon_cos)+50<=10 || player_1.getX()+i*(f1_weapon_cos)+50>=590){
                            player_1.setLocation(
                                    (int)(player_1.getX()+i*(f1_weapon_cos) ),
                                    (int)(player_1.getY()+i*(f1_weapon_sin) ) );
                            break;
                        }else if(player_1.getY()+i*(f1_weapon_sin)+50<=10 || player_1.getY()+i*(f1_weapon_sin)+50>=590){
                            player_1.setLocation(
                                    (int)(player_1.getX()+i*(f1_weapon_cos) ),
                                    (int)(player_1.getY()+i*(f1_weapon_sin) ) );
                            break;
                        }
                    } // for
                }else if(player_1.getY()+150*(f1_weapon_sin)+50<=10 || player_1.getY()+150*(f1_weapon_sin)+50>=590){
                    // Will go out of border
                    for(int i=0;i<=150;i+=3){ // use for to find location within border

                        if(player_1.getX()+i*(f1_weapon_cos)+50<=10 || player_1.getX()+i*(f1_weapon_cos)+50>=590){
                            player_1.setLocation(
                                    (int)(player_1.getX()+i*(f1_weapon_cos) ),
                                    (int)(player_1.getY()+i*(f1_weapon_sin) ) );
                            break;
                        }else if(player_1.getY()+i*(f1_weapon_sin)+50<=10 || player_1.getY()+i*(f1_weapon_sin)+50>=590){
                            player_1.setLocation(
                                    (int)(player_1.getX()+i*(f1_weapon_cos) ),
                                    (int)(player_1.getY()+i*(f1_weapon_sin) ) );
                            break;
                        }
                    } // for
                }else{
                    player_1.setLocation(
                            (int)(player_1.getX()+150* PairingWithMonster.cos),
                            (int)(player_1.getY()+150* PairingWithMonster.sin)  );
                }

                PairingWithMonster.ball_1.setLocation(player_1.getX(), player_1.getY() );
                PairingWithMonster.flash_timer.schedule(new Player.teleport(),2,1000 ) ;
                PairingWithMonster.W_able=false; // disable "W"
            }//key W end

            if(id==KeyEvent.VK_E && PairingWithMonster.E_able){

                int one=0;  // Get flipped card
                one=(int)( (player_1.getY()+50)/150*4+(player_1.getX()+50)/150 );

                PairingWithMonster.check[PairingWithMonster.first_or_second]=one; // record

                if(PairingWithMonster.blood_number==0){ // already dead
                }else if(cards[one].getIcon() == PairingWithMonster.finish ){  // already found
                }else if( PairingWithMonster.first_or_second ==0 ){   // first one in pair
                    cards[one].setIcon(hidden_cards[one]);

                    player_1.setLocation( player_1.getX()+1  , player_1.getY() ); // handle cover issue

                    PairingWithMonster.first_or_second++;
                    PairingWithMonster.first_or_second %=2;

                }else if( PairingWithMonster.first_or_second ==1 && check[0]==check[1]){
                    // click the same card => Do nothing
                }else if( check[1]==17 ){  // beginning
                    cards[one].setIcon(hidden_cards[one]);

                    player_1.setLocation( player_1.getX()+1 , player_1.getY() ); // handle cover issue

                    PairingWithMonster.first_or_second++;
                    PairingWithMonster.first_or_second %=2;
                }else if( PairingWithMonster.first_or_second ==1 ){  // second one in the pair
                    cards[one].setIcon(hidden_cards[one]);

                    player_1.setLocation( player_1.getX()+1 , player_1.getY() ); // handle cover issue

                    PairingWithMonster.E_able=false; // disable "E"
                    PairingWithMonster.flip_timer.schedule(new Player.flipCard(),500,700);

                    PairingWithMonster.first_or_second++;
                    PairingWithMonster.first_or_second %=2;
                }

            }//key E end

        }//LeyPressed
    }//KeyLis

}
