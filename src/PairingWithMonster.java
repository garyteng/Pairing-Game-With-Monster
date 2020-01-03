import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Timer;      //timer
import java.util.TimerTask;  //timer

public class PairingWithMonster {

    static JFrame frm=new JFrame("Pairing Game With Monster");
    static Container cp=frm.getContentPane();

    static JDialog jd=new JDialog(frm);

    static Timer weapon_timer=new Timer("Hit");
    static Timer flash_timer=new Timer("flash");
    static Timer flip_timer=new Timer("flip");

    static int[] array={0,0,1,1,2,2,3,3,4,4,5,5,6,6,7,7}; // Icon Image array
    static String[] array2={"0.JPG", "1.JPG", "2.JPG", "3.JPG", "4.JPG", "5.JPG", "6.JPG", "7.JPG", "8.JPG"};
    static ImageIcon[] array3=new ImageIcon[16]; //儲存RANDOM後的排

    static ImageIcon question=new ImageIcon( PairingWithMonster.class.getResource("me2.png"));
    static ImageIcon f1=new ImageIcon(PairingWithMonster.class.getResource("p1.png")); //人物1
    static ImageIcon f1_ball=new ImageIcon(PairingWithMonster.class.getResource("images.png"));  //Coin that player is chasing
    static ImageIcon transparent=new ImageIcon(PairingWithMonster.class.getResource("transparent.png"));
    static ImageIcon transparent2=new ImageIcon(PairingWithMonster.class.getResource("transparent2.png"));
    static ImageIcon animal_Icon=new ImageIcon(PairingWithMonster.class.getResource("monster.png"));
    static ImageIcon weapon=new ImageIcon(PairingWithMonster.class.getResource("catch_ball.png"));
    //static ImageIcon hand=new ImageIcon(PairingWithMonster.class.getResource("hand.png")); //Cursor
    static ImageIcon finish=new ImageIcon(PairingWithMonster.class.getResource("finish.jpg")); //翻成功的圖
    static ImageIcon blood_Icon=new ImageIcon(PairingWithMonster.class.getResource("blood_color.png"));
    static ImageIcon animal_catch=new ImageIcon(PairingWithMonster.class.getResource("22.png"));
    static ImageIcon cry=new ImageIcon(PairingWithMonster.class.getResource("人物.png"));
    static ImageIcon winner=new ImageIcon(PairingWithMonster.class.getResource("WINNER.gif"));
    static ImageIcon loser=new ImageIcon(PairingWithMonster.class.getResource("gameover.png"));

    static int[] check={16,17};  // Record opened cards
    static int check_time=0;     // first or second opened card
    static int poker=0;

    static JPanel pnl=new JPanel(new GridLayout(4,4,1,1));
    static JPanel jd_pnl=new JPanel(new GridLayout(2,1) );

    static JLabel lab[]=new JLabel[16]; //要翻得牌
    static JLabel lab_1=new JLabel(f1); // player
    static JLabel ball_1=new JLabel(transparent); // player destination
    //static JLabel animal=new JLabel(animal_Icon); //animal的顯示JLabel
    static JLabel cage =new JLabel(transparent2); // weapon for player 1
    static JLabel animal=new JLabel(animal_Icon); // monster
    static JLabel blood=new JLabel(blood_Icon);

    static JButton start=new JButton("AGAIN??");
    static JButton end=new JButton("EXIT!!") ;


    static double sin=0,cos=1; // player moving direction
    static double f1_weapon_sin=0,f1_weapon_cos=1; // player attack direction
    static double blood_number=550;

    //Q: throw ball, W: teleport for a short distance, E: open a card
    static boolean Q_able=true ,W_able=true ,E_able=true;

    public static void main(String args[]){

        System.out.println("Working Directory = " +
                System.getProperty("user.dir"));

        for(int i=0;i<100;i++){  // shuffle cards
//            swap(array[(int)(Math.random()*16) ],array[(int)(Math.random()*16)] );
            int aa=0, bb=0, cc=0;
            aa=(int)(Math.random()*16); bb=(int)(Math.random()*16);
            cc=array[aa];
            array[aa]=array[bb];
            array[bb]=cc;
        }
        for(int i=0;i<16;i++){   // update shuffle cards
            array3[i]=new ImageIcon(PairingWithMonster.class.getResource(array2[array[i] ]));
        }

        cp.setLayout( null );

        pnl.addMouseListener(new MouseLis() );
        frm.addKeyListener(new KeyLis() );

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

        cp.add(lab_1); // player
        lab_1.setBounds(70,70,100,100);
        lab_1.setOpaque(false);

        cp.add(ball_1); // player chasing target
        ball_1.setBounds(70,70,100,96);
        ball_1.setOpaque(false);

        cp.add(cage); // player's weapon
        cage.setBounds(270,270,100,100);
        //cage.setOpaque(false);

        pnl.setBounds(0,0,603,603);             /*pnl.setLocation(80,80);   pnl.setSize(640,640);*/

        cp.add(pnl/*,BorderLayout.CENTER*/);


        for(int i=0;i<16;i++){  //add JLabel to JPanel
            lab[i]=new JLabel();//初始化
            lab[i].setIcon(question);
            lab[i].setBackground(Color.WHITE);
            lab[i].setVerticalTextPosition(JLabel.BOTTOM);
            lab[i].setHorizontalTextPosition(JLabel.CENTER);
            pnl.add(lab[i]);
        }


        frm.setSize(625,725);
        frm.setVisible(true);
        //frm.setResizable(false);

        Timer timer = new Timer("Move");  //f1 timer start
        //f1timer t = new f1timer();
        timer.schedule(new f1timer()/*t*/, 0, 30);

        Timer timer_a = new Timer("Animal");  //animal timer start
        animal_move tt = new animal_move();
        timer_a.schedule(tt, 17, 30);

        jd.setLocation(650,150);
        jd.setSize(150,150);

        jd_pnl.add(start);
        //start.setLocation(30,20);
        jd_pnl.add(end);
        //end.setLocation(30,100);

        start.addMouseListener(new Mouse_start() );
        end.addMouseListener(new Mouse_start() );

        jd.getContentPane().add(jd_pnl);
        //jd.pack();   //縮成一長條
        jd.setVisible(true);
        //jd.show();

    }

    static class f1_flip extends TimerTask{ //f1翻牌用
        private int time=1; //會有1秒鐘不能用
        public void run(){
            time--;
            if(time<=0){

                if( array[check[0]] == array[check[1]] ){  //牌相同
                    lab[check[0]].setIcon(finish);
                    lab[check[1]].setIcon(finish);

                    if((poker+2)==16){
                        blood.setBounds(3,603,600,75); //贏了(血條換)
                        blood.setIcon(winner);
                        lab_1.setIcon(f1);//人物便會原狀
                        animal.setIcon(animal_Icon);//怪獸便會原狀
                    }
                    poker+=2;
                }
                else{  //牌不同
                    lab[check[0]].setIcon( question );
                    lab[check[1]].setIcon( question );
                }

                E_able=true; //E又可以用了;
                this.cancel(); }
        }
    }


    static class KeyLis extends KeyAdapter{
        double key_sin=sin,key_cos=cos;
        int id;



        public void keyPressed(KeyEvent G){
            id=G.getKeyCode();

            if(id==KeyEvent.VK_T){  // 直接關閉鍵
                frm.dispose();
                System.exit(0);
            }

            if(id==KeyEvent.VK_Q && Q_able){//按下Q時 (fi攻擊)
                cage.setLocation(lab_1.getX(),lab_1.getY() );
                cage.setIcon(weapon);
                weapon_timer.schedule(new f1_weapon(),10,31 );//timer start
                Q_able=false; //讓Q暫時不能用
            }  //key Q end

            if(id==KeyEvent.VK_W && W_able){//按下W時 (f1閃現)

                if(lab_1.getX()+150*(f1_weapon_cos)+50<=10 || lab_1.getX()+150*(f1_weapon_cos)+50>=590){//會出界
                    for(int i=0;i<=150;i+=3){ //用FOR迴圈找到一個,適當的位址

                        if( lab_1.getX()+i*(f1_weapon_cos)+50<=10 || lab_1.getX()+i*(f1_weapon_cos)+50>=590){
                            lab_1.setLocation( (int)(lab_1.getX()+i*(f1_weapon_cos) ),(int)(lab_1.getY()+i*(f1_weapon_sin) ) );
                            break;}
                        else if(lab_1.getY()+i*(f1_weapon_sin)+50<=10 || lab_1.getY()+i*(f1_weapon_sin)+50>=590){
                            lab_1.setLocation( (int)(lab_1.getX()+i*(f1_weapon_cos) ),(int)(lab_1.getY()+i*(f1_weapon_sin) ) );
                            break;}

                    }/*for*/ }
                else if(lab_1.getY()+150*(f1_weapon_sin)+50<=10 || lab_1.getY()+150*(f1_weapon_sin)+50>=590){//會出界
                    for(int i=0;i<=150;i+=3){ //用FOR迴圈找到一個,適當的位址

                        if(lab_1.getX()+i*(f1_weapon_cos)+50<=10 || lab_1.getX()+i*(f1_weapon_cos)+50>=590){
                            lab_1.setLocation( (int)(lab_1.getX()+i*(f1_weapon_cos) ),(int)(lab_1.getY()+i*(f1_weapon_sin) ) );
                            break;}
                        else if(lab_1.getY()+i*(f1_weapon_sin)+50<=10 || lab_1.getY()+i*(f1_weapon_sin)+50>=590){
                            lab_1.setLocation( (int)(lab_1.getX()+i*(f1_weapon_cos) ),(int)(lab_1.getY()+i*(f1_weapon_sin) ) );
                            break;}

                    }/*for*/  }
                else{//不會超出邊界
                    lab_1.setLocation( (int)(lab_1.getX()+150*cos),(int)(lab_1.getY()+150*sin)  );}

                ball_1.setLocation(lab_1.getX(),lab_1.getY() );
                flash_timer.schedule(new f1_flash(),2,1000 ) ;
                W_able=false; //讓W暫時不能用
            }//key W end
            if(id==KeyEvent.VK_E && E_able){

                int one=0;  //看是翻到哪一張牌
                one=(int)( (lab_1.getY()+50)/150*4+(lab_1.getX()+50)/150 );

                check[check_time]=one; //讀入

                if(blood_number==0){}
                else if(lab[one].getIcon()==finish ){}  //不能選
                else if( check_time==0 ){   //翻第一張
                    lab[one].setIcon(array3[one]);

                    lab_1.setLocation( lab_1.getX()+1  , lab_1.getY() ); //解決覆蓋問題

                    check_time++;
                    check_time%=2;
                }
                else if( check_time==1 && check[0]==check[1]){}  //點同一張2下
                else if( check[1]==17 ){  //剛開始
                    lab[one].setIcon(array3[one]);

                    lab_1.setLocation( lab_1.getX()+1 , lab_1.getY() ); //解決覆蓋問題

                    check_time++;
                    check_time%=2;
                }
                else if( check_time==1 ){  //點了2張牌
                    lab[one].setIcon(array3[one]);

                    lab_1.setLocation( lab_1.getX()+1 , lab_1.getY() ); //解決覆蓋問題

                    E_able=false; //E不可以用了;
                    flip_timer.schedule(new f1_flip() ,500,700);

                    check_time++;
                    check_time%=2;
                }

            }//key E end

        }//LeyPressed
    }//KeyLis

    static class Mouse_start extends MouseAdapter{
        //private Timer ff=new Timer("ff");
        //private Timer aa=new Timer("aa");

        public void mouseClicked(MouseEvent z){
            if(start==(JButton)z.getSource() ){

                //ff.schedule(new f1timer(),0,30 );
                //aa.schedule(new animal_move(),17,30 );



                for(int i=0;i<100;i++){  // shuffle cards
                    //swap(array[(int)(Math.random()*16) ],array[(int)(Math.random()*16)] );
                    int aa=0,bb=0,cc=0;
                    aa=(int)(Math.random()*16); bb=(int)(Math.random()*16);
                    cc=array[aa];
                    array[aa]=array[bb];
                    array[bb]=cc;
                }
                for(int i=0;i<16;i++){   // update shuffle cards
                    array3[i]=new ImageIcon(array2[array[i] ]);
                    //lab[one].setIcon(array3[one]);
                }
                for(int i=0;i<16;i++){
                    lab[i].setIcon(question);}


                lab_1.setIcon(f1); //reset player
                lab_1.setLocation(70,70);   // reset player location
                animal.setLocation(300,300);// reset monster location
                ball_1.setLocation(70,70);  // reset coin location

                blood.setBounds(20,630,550,30); // reset remaining blood
                blood_number=550; // refill blood
                blood.setIcon(blood_Icon); //reset blood

                poker=0; // reset number of finded pairs
            }
            else{
                System.exit(0);}
        }
    }

    static class MouseLis extends MouseAdapter{

//	private double xxx,yyy,zzz;

        public void mousePressed(MouseEvent e){



            ball_1.setLocation(e.getX()-50 ,e.getY()-48 );
            ball_1.setIcon(f1_ball);

            f1_weapon_cos=(ball_1.getX()-lab_1.getX())/
                    Math.sqrt(Math.pow(ball_1.getX()-lab_1.getX(),2)+
                            Math.pow(ball_1.getY()-lab_1.getY(),2) );

            f1_weapon_sin=(ball_1.getY()-lab_1.getY())/
                    Math.sqrt(Math.pow(ball_1.getX()-lab_1.getX(),2)+
                            Math.pow(ball_1.getY()-lab_1.getY(),2) );

        }

    }


    static class animal_move extends TimerTask{  //for animal move
        private double distance;
        public void run(){
            distance=Math.sqrt( Math.pow(lab_1.getX()-animal.getX(),2) +  Math.pow(lab_1.getY()-animal.getY(),2) );

            if(poker==16){/* (牌已翻完) this.cancel();*/}

            else if(distance>50){
                animal.setLocation( (int)( (animal.getX())+(lab_1.getX()-animal.getX() )/distance*3.5 ) , (int)( (animal.getY())+(lab_1.getY()-animal.getY() )/distance*3.5 ) ); }
            else if(blood_number>=2) {blood_number-=2;
                blood.setSize((int)blood_number ,30);}

            if(blood_number<=0){  // dead
                /*this.cancel();*/
                blood.setBounds(3,603,600,75);  blood.setIcon(loser);}

            if(Math.sqrt(Math.pow(animal.getX()-cage.getX(),2)+Math.pow(animal.getY()-cage.getY(),2) )<30
                    && cage.getIcon()==weapon ){ // hit monster
                try{
                    animal.setIcon(animal_catch);
                    cage.setIcon(transparent);
                    Thread.sleep(3000);
                    animal.setIcon(animal_Icon);}
                catch(Exception w){}//catch

            }

        }

    }

    // timer for player teleport CD
    static class f1_flash extends TimerTask{
        private int time=5; //use once per five seconds
        public void run(){
            time--;
            if(time<=0){
                W_able=true; // enable to use teleport
                this.cancel(); }
        }
    }

    // timer for player throwing weapon (ball)
    static class f1_weapon extends TimerTask{
        private int time=30;
        private double f_x=f1_weapon_sin,f_y=f1_weapon_cos;
        public void run(){

            if(time<0){
                Q_able=true; // enable weapon
                cage.setLocation(50,700);
                cage.setIcon(transparent);
                this.cancel();}
            else if(cage.getY()>550){time--; }// out of boundary
            else{  // keep going
                time--;
                cage.setLocation( (int)(cage.getX()+10*f_y) ,(int)(cage.getY()+10*f_x) );}

        }
    }

    // timer for player
    static class f1timer extends TimerTask {
        //times member represent calling times.
        // private int times = 0;

        private double distance;  //calculate distance
        private double x_x,y_y;

        //public void setTime(int x){time=x;}


        public void run() {

            x_x=ball_1.getX()-lab_1.getX(); // x-axis diff
            y_y=ball_1.getY()-lab_1.getY(); // y-axis diff

            distance=Math.sqrt(Math.pow(ball_1.getX()-lab_1.getX(),2)+Math.pow(ball_1.getY()-lab_1.getY(),2)) ; //calculate distance

            sin=y_y/distance;  // get sin
            cos=x_x/distance;  // get cos

            if(poker==16){ // end
    		/*try{Thread.sleep(1800);}
    	    catch(Exception e){}
    	    System.exit(0);*/}
            else if(blood_number==0){/* out of blood */}

            else if(distance<=20){ball_1.setIcon(transparent);}
            else {// going
                x_x=lab_1.getX()+5*cos;
                y_y=lab_1.getY()+5*sin;
                lab_1.setLocation( (int)x_x,(int)y_y );}

            if(blood_number<=0){  //翹了

                lab_1.setIcon(cry);

    	   /*try{Thread.sleep(2800);}
    	   catch(Exception e){}

    		System.exit(0);*/    }

        }//run() end
    }//f1timer end

}//class PairingWithMonster end

