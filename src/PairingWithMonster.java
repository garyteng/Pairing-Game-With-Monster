import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Timer;      //timer


public class PairingWithMonster {

    static JFrame frm=new JFrame("Pairing Game With Monster");
    static Container cp=frm.getContentPane();

    static JDialog jd=new JDialog(frm);

    static Timer weapon_timer=new Timer("Hit");
    static Timer flash_timer=new Timer("flash");
    static Timer flip_timer=new Timer("flip");

    static int[] hidden_cards_number ={0,0,1,1,2,2,3,3,4,4,5,5,6,6,7,7}; // Icon Image array
    static String[] image_names ={"0.JPG", "1.JPG", "2.JPG", "3.JPG", "4.JPG", "5.JPG", "6.JPG", "7.JPG", "8.JPG"};
    static ImageIcon[] hidden_cards =new ImageIcon[16]; // shuffle result

    static ImageIcon question=new ImageIcon( PairingWithMonster.class.getResource("question.png"));
    static ImageIcon f1=new ImageIcon(PairingWithMonster.class.getResource("p1.png")); // player
    static ImageIcon f1_ball=new ImageIcon(PairingWithMonster.class.getResource("images.png"));  //Coin that player is chasing
    static ImageIcon transparent=new ImageIcon(PairingWithMonster.class.getResource("transparent.png"));
    static ImageIcon transparent2=new ImageIcon(PairingWithMonster.class.getResource("transparent2.png"));
    static ImageIcon animal_Icon=new ImageIcon(PairingWithMonster.class.getResource("monster.png"));
    static ImageIcon weapon=new ImageIcon(PairingWithMonster.class.getResource("catch_ball.png"));
    //static ImageIcon hand=new ImageIcon(PairingWithMonster.class.getResource("hand.png")); //Cursor
    static ImageIcon finish=new ImageIcon(PairingWithMonster.class.getResource("finish.jpg"));
    static ImageIcon blood_Icon=new ImageIcon(PairingWithMonster.class.getResource("blood_color.png"));
    static ImageIcon animal_catch=new ImageIcon(PairingWithMonster.class.getResource("captured_monster.png"));
    static ImageIcon cry=new ImageIcon(PairingWithMonster.class.getResource("f1_failed.png"));
    static ImageIcon winner=new ImageIcon(PairingWithMonster.class.getResource("WINNER.gif"));
    static ImageIcon loser=new ImageIcon(PairingWithMonster.class.getResource("gameover.png"));

    static int[] check={16,17};  // Record opened cards
    static int first_or_second =0;     // first or second opened card
    static int found_cards =0;

    static JPanel pnl=new JPanel(new GridLayout(4,4,1,1));
    static JPanel jd_pnl=new JPanel(new GridLayout(2,1) );

    static JLabel cards[]=new JLabel[16]; // cards
    static JLabel player_1 =new JLabel(f1); // player
    static JLabel ball_1=new JLabel(transparent); // player destination
    static JLabel cage =new JLabel(transparent2); // weapon for player 1
    static JLabel animal=new JLabel(animal_Icon); // monster
    static JLabel blood=new JLabel(blood_Icon);

    static JButton start=new JButton("AGAIN??");
    static JButton end=new JButton("EXIT!!") ;


    static double sin=0,cos=1; // player moving direction
    static double f1_weapon_sin=0,f1_weapon_cos=1; // player attack direction
    static double blood_number=550;

    //Q: throw ball, W: teleport for a short distance, E: open a card
    static volatile boolean Q_able=true ,W_able=true ,E_able=true;

    public static void main(String args[]){

        System.out.println("Working Directory = " +
                System.getProperty("user.dir"));

        for(int i=0;i<100;i++){  // shuffle cards
            // swap(array[(int)(Math.random()*16) ],array[(int)(Math.random()*16)] );
            int aa=0, bb=0, cc=0;
            aa=(int)(Math.random()*16); bb=(int)(Math.random()*16);
            cc= hidden_cards_number[aa];
            hidden_cards_number[aa]= hidden_cards_number[bb];
            hidden_cards_number[bb]=cc;
        }
        for(int i=0;i<16;i++){   // update shuffle cards
            hidden_cards[i]=new ImageIcon(PairingWithMonster.class.getResource(image_names[hidden_cards_number[i] ]));
        }

        cp.setLayout( null );

        // pnl.addMouseListener(new MouseLis() );
        pnl.addMouseListener(new MyMouseEvents.MouseLis());
        // frm.addKeyListener(new KeyLis() );
        frm.addKeyListener(new MyKeyEvents.KeyLis());

        //  frm.setCursor(weapon);
        Toolkit toolkit = Toolkit.getDefaultToolkit();  //Cursor Change Start
        Image image = toolkit.getImage(PairingWithMonster.class.getResource("hand.png"));
        Point hotSpot = new Point(0,0);
        Cursor cursor = toolkit.createCustomCursor(image, hotSpot, "hand");
        frm.setCursor(cursor);  //Cursor Change End

        cp.add(blood);
        blood.setBounds(20,630,550,30);

        cp.add(animal);
        animal.setBounds(300,300,100,100);
        animal.setOpaque(false);

        cp.add(player_1); // player
        player_1.setBounds(70,70,100,100);
        player_1.setOpaque(false);

        cp.add(ball_1); // player chasing target
        ball_1.setBounds(70,70,100,96);
        ball_1.setOpaque(false);

        cp.add(cage); // player's weapon
        cage.setBounds(270,270,100,100);
        //cage.setOpaque(false);

        pnl.setBounds(0,0,603,603);
        /*pnl.setLocation(80,80);   pnl.setSize(640,640);*/

        cp.add(pnl/*,BorderLayout.CENTER*/);

        for(int i=0;i<16;i++){  //add JLabel to JPanel
            cards[i]=new JLabel();
            cards[i].setIcon(question);
            cards[i].setBackground(Color.WHITE);
            cards[i].setVerticalTextPosition(JLabel.BOTTOM);
            cards[i].setHorizontalTextPosition(JLabel.CENTER);
            pnl.add(cards[i]);
        }

        frm.setSize(625,725);
        frm.setVisible(true);
        frm.setResizable(false);

        Timer timer = new Timer("Move");  // player timer start
        timer.schedule(new Player.playerTimer(), 0, 30);

        Timer timer_a = new Timer("Animal");  //animal timer start
        // animal_move tt = new animal_move();
        timer_a.schedule(new Monster.move(), 17, 30);

        jd.setLocation(650,150);
        jd.setSize(150,150);

        jd_pnl.add(start);
        //start.setLocation(30,20);
        jd_pnl.add(end);
        //end.setLocation(30,100);

        //start.addMouseListener(new Mouse_start() );
        //end.addMouseListener(new Mouse_start() );
        start.addMouseListener(new MyMouseEvents.Mouse_start());
        end.addMouseListener(new MyMouseEvents.Mouse_start() );

        jd.getContentPane().add(jd_pnl);
        //jd.pack();   //縮成一長條
        jd.setVisible(true);
        //jd.show();
    }

}

