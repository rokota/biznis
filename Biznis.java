/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

/**
 *
 * @author Rok
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Rectangle;
import java.awt.Point;


import javax.swing.JPanel;
import javax.swing.Timer;
    
//$$$ Fuck bitches get moneyz (tokenz) $$$
//
public class Biznis extends JPanel implements ActionListener, KeyListener{

	public boolean igra = false;
	public boolean start = false;
	public boolean konec = false;
	public boolean levo = false;
	public boolean desno = false;

	public int ploscaX = 0;
	public int ploscaY = 0;
	public int ploscaSirina = 160;
	public int ploscaVisina = 20;

	public int ploscaHitrost = 6;

	public int zogaX = 0;
	public int zogaY = 0;
	public int zogaR = 20;

	public int vX = 5;
	public int vY = -10;

	public int ura = 0;
	public int stevec1 = 0;

	public int zivljenja = 3;
        
        public Kocka[] kocke = new Kocka[63];
        
        public static class Kocka {
            
            public int x;
            public int y;
            public int sirina = 90;
            public int visina = 20;
            public boolean vidna = true;
            
            public Kocka (int x, int y) {
                this.x = x;
                this.y = y;
            }
            
            public int vrniX() {
                return x;
            }
            public int vrniY() {
                return y;
            }
            public int vrniSirino() {
                return sirina;
            }
            public int vrniVisino() {
                return visina;
            }
            
            public boolean vrniVidna () {
                return vidna;
            }
            public void unicena(boolean vidna) {
                this.vidna = false;
            }
            Rectangle getRect() {
                return new Rectangle(x, y, sirina, visina); 
            }
        }
        
        public static class zogica {
            public int x;
            public int y;
            public int sirina = 20;
            public int visina = 20;
            
            public zogica(int x, int y) {
                this.x = x;
                this.y = y;
            }
            
            Rectangle getRect() {
                return new Rectangle(x, y, sirina, visina); 
            }
            
        }

	public Biznis(){
		setBackground(Color.WHITE);

		setFocusable(true);
                addKeyListener(this);

                Timer timer = new Timer(1000/60, this);
                timer.start();
        }

	public void actionPerformed(ActionEvent e) {
		magic();
	}

	public void magic(){
		if (ura == 0) {
                    nastaviPlosco();
                    nastaviKocke();
                }
		ura++;
		if (start) {

                    int ploscaXlevo = ploscaX - ploscaHitrost;
                    int ploscaXdesno = ploscaX + ploscaSirina + ploscaHitrost;
                    int tempXlevo = zogaX + vX;
                    int tempXdesno = zogaX + zogaR + vX;
                    int tempYgor = zogaY + vY;
                    int tempYdol = zogaY + zogaR + vY;


                    if (!igra) {
                            if (levo && stevec1 == 0) {
                                    if (ploscaXlevo >= 0) {
                                            ploscaX -= ploscaHitrost;
                                            zogaX = ploscaX + ploscaSirina/2 - zogaR/2;
                                    }
                            }
                            if (desno && stevec1 == 0) {
                                    if (ploscaXdesno <= 884) {
                                            ploscaX += ploscaHitrost;
                                            zogaX = ploscaX + ploscaSirina/2 - zogaR/2;
                                    }
                            }
                    }
                    else {
                            if (levo) {
                                    if (ploscaXlevo >= 0) {
                                            ploscaX -= ploscaHitrost;
                                    }
                            }
                            if (desno) {
                                    if (ploscaXdesno <= 884) {
                                            ploscaX += ploscaHitrost;
                                    }
                            }

                            if (tempYdol > ploscaY) {
                                    if (tempXdesno < ploscaXlevo || tempXlevo > ploscaXdesno) {
                                            nastaviPlosco1();
                                            zogaX = ploscaX + ploscaSirina/2 - zogaR/2;
                                            zogaY = (661 - ploscaVisina) - 30;
                                            stevec1 = 0;
                                            zivljenja--;
                                            if (zivljenja == 0) {
                                                    ura = 0;
                                                    konec = true;
                                            }
                                            igra = false;
                                    }
                                    else vY *= -1;
                            }

                            if (tempXlevo < 0 || tempXdesno > 884) {
                    vX *= -1;
            	}
            	if (tempYgor < 0) vY *= -1;
                
                zogica zoga = new zogica(zogaX, zogaY);
                
                for (int i = 0; i < 63; i++) {
                    if (kocke[i].getRect().intersects(zoga.getRect())) {
                        Point levo = new Point(zogaX - 1, zogaY+zogaR/2);
                        Point gor = new Point(zogaX+zogaR/2, zogaY - 1);
                        Point desno = new Point(zogaX+zogaR + 1, zogaY+zogaR/2);
                        Point dol = new Point(zogaX+zogaR/2, zogaY+zogaR + 1);
                        
                        if(kocke[i].vrniVidna()) {
                            if (kocke[i].getRect().contains(levo)) vX *= -1;
                            else if (kocke[i].getRect().contains(desno)) vX *= -1;
                            else if (kocke[i].getRect().contains(gor)) vY *= -1;
                            else if (kocke[i].getRect().contains(dol)) vY *= -1;
                            kocke[i].unicena(kocke[i].vrniVidna());
                        }
                    }
                }
                

            	zogaX += vX;
            	zogaY += vY;
			}
		}
                //System.out.println(884+" "+661);
		repaint();
	}

	public void nastaviPlosco() {
		ploscaX = 884 / 2 - ploscaSirina/2;
		ploscaY = 661 - ploscaVisina;
		zogaX = ploscaX + ploscaSirina/2 - zogaR/2;
		zogaY = ploscaY - zogaR;
		repaint();
	}
        

        
	public void nastaviPlosco1() {
		ploscaX = 884 / 2 - ploscaSirina/2;
		ploscaY = 661 - ploscaVisina;
		repaint();
	}
        
        public void nastaviKocke() {
            int k = 0;
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 9; j++) {
                    kocke[k] = new Kocka(i*95+100, j*30+100);
                    //System.out.println("dela");
                    k++;
                }
            }
        }

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
		if (!konec) {
			if (!start) {
                            g.drawString(String.valueOf("ENTER za zacetek"), 290, 310);
			}
			else {
				g.setColor(Color.GREEN);
				g.fillRect(ploscaX, ploscaY, ploscaSirina, ploscaVisina);
                                //System.out.println(ploscaX+" "+ploscaY);
				g.setColor(Color.BLACK);
				g.fillOval(zogaX, zogaY, zogaR, zogaR);
				g.setColor(Color.RED);
				if (zivljenja == 3) {
					g.fillOval(15, 15, 20, 20);
					g.fillOval(45, 15, 20, 20);
					g.fillOval(75, 15, 20, 20);
				}
				else if (zivljenja == 2) {
					g.fillOval(15, 15, 20, 20);
					g.fillOval(45, 15, 20, 20);
				}
				else if (zivljenja == 1) {
					g.fillOval(15, 15, 20, 20);
				}
                                for (int i = 0; i < 63; i++) {
                                    if (kocke[i].vrniVidna()) {
                                        g.setColor(Color.YELLOW);
                                        g.fillRect(kocke[i].vrniX(), kocke[i].vrniY(), kocke[i].vrniSirino(), kocke[i].vrniVisino());
                                        g.setColor(Color.BLACK);
                                        g.drawRect(kocke[i].vrniX(), kocke[i].vrniY(), kocke[i].vrniSirino(), kocke[i].vrniVisino());
                                    }
                                }
			}
		}
		else {
                    g.setColor(Color.BLACK);
                    g.drawString(String.valueOf("Konec igre!"), 290, 310);
                }
	}

	public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
       		start = true;
       		if (konec) {
       			start = false;
       			konec = false;
       			zivljenja = 3;
       			igra = false;
       		}
        }
        else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
        	if (start) {
	        	stevec1++;
	            if (!igra) igra = true;
	            else igra = false;
	        }
        }        
        else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            levo = true;
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            desno = true;
        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            levo = false;
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            desno = false;
        }
    }
}
