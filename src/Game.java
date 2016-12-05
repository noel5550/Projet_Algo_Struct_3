import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.*;
import java.util.*;
import java.lang.String;


/**
 * Classe dont les instances sont des fenêtres graphiques
 * dérivées de JFrame.
 */
class Game extends JFrame{

	private Grid grille;
	private JPanel menu;
	private JLabel affScoreJ1, affScoreJ2;
	private JButton b1, b2, b3, b4, b5, b6, b7, b8, b9, b10;
	private int joueur, scoreJ1, scoreJ2, nbBase, taille, choix, compt, posTmpX, posTmpY;
	private ArrayList<Integer> listeCoup, evaluer;
	private boolean vJ1, vJ2;

	public Game(String titre, int nbB, int t, int c) {

		//------------------------------------------------------------------- Instanciation de la fenêtre principale et de son contenu
		super(titre);
		grille = new Grid(t, nbB);
		menu = new JPanel();
		scoreJ1 = 0;
		scoreJ2 = 0;
		affScoreJ1 = new JLabel("Score Player 1 : "+ scoreJ1);
		affScoreJ2 = new JLabel("Score Player 2 : "+ scoreJ2);
		joueur = 1;
		nbBase = nbB;
		taille = t;
		compt = 0;
		posTmpX = 0;
		posTmpY = 0;
		vJ1 = false;
		vJ2 = false;
		choix = c;
		listeCoup = new ArrayList<Integer>();
		evaluer = new ArrayList<Integer>();

		//------------------------------------------------------------------- Paramétrage de la fenêtre principale
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);


		//------------------------------------------------------------------- Paramétrage du menu
		menu.setLayout(new GridLayout(8,1));
		menu.setVisible(true);
		menu.setPreferredSize(new Dimension(100,grille.getDim()));

		//------------------------------------------------------------------- Instanciation et paramétrage du contenu du menu
		b1 = new JButton("Afficher Composante");
		b2 = new JButton("Connaitre Composante");
		b3 = new JButton("Connaitre Val");
		b4 = new JButton("Nombre d'étoiles");
		b5 = new JButton("Distance Case");
		b6 = new JButton("Relie Composante");
		b7 = new JButton("existe Chemin Cases");
		b8 = new JButton("Abandonner");
		b9 = new JButton("relieCaseMin");
		b10 = new JButton("Fils");

		// afficheComposante
        b1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent a) {
				suppr();
				grille.addMouseListener(new MouseAdapter(){
		        		public void mousePressed(MouseEvent e){
		    				grille.afficheComposante((e.getX()-1)/50, (e.getY()-1)/50);
		    				suppr();
		    				if (choix == 1) 
		    					joueDeuxHumains();

		    				
		        		}
		        });
			}
        });

        // CONNAITRE COMP
        b2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent a) {
				suppr();
				grille.addMouseListener(new MouseAdapter(){
		        		public void mousePressed(MouseEvent e){
		    				System.out.println(grille.getCompression((e.getX()-1)/50, (e.getY()-1)/50));
		    				suppr();
		    				if (choix == 1) 
		    					joueDeuxHumains();

		    				
		        		}
		        });
			}
        });

        // CONNAITRE VAL
        b3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent a) {
				suppr();
				grille.addMouseListener(new MouseAdapter(){
		        		public void mousePressed(MouseEvent e){
		    				System.out.println(grille.getValue((e.getX()-1)/50, (e.getY()-1)/50));
		    				suppr();
		    				if (choix == 1) 
		    					joueDeuxHumains();
   				
		        		}
		        });
			}
        });

		// nombreEtoile
		b4.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent a) {
				suppr();
				grille.addMouseListener(new MouseAdapter(){
		        		public void mousePressed(MouseEvent e){
		    				System.out.println(grille.nombreEtoiles((e.getX()-1)/50, (e.getY()-1)/50));
		    				suppr();
		    				if (choix == 1) 
		    					joueDeuxHumains();
		    				//else if(choix == 2)
		    					//joueOrdiHumain();
		    				
		        		}
		        });
			}
        });


		// distanceCase
		b5.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent a) {
				suppr();
				grille.addMouseListener(new MouseAdapter(){
	        		public void mousePressed(MouseEvent e){
    					if (compt == 0){
    						posTmpX = (e.getX()-1)/50;
    						posTmpY = (e.getY()-1)/50;
    						++compt;
    					}
    					else{
    						System.out.println(grille.distanceCases(posTmpX, posTmpY, (e.getX()-1)/50, (e.getY()-1)/50));
							suppr();
							if (choix == 1)
								joueDeuxHumains();
							//else if(choix == 2)
								//joueOrdiHumain();
							--compt;
    					}
	    			}
		        });
			}
        });


		// RELIE COMPOSANTE
        b6.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent a) {
				suppr();
				grille.addMouseListener(new MouseAdapter(){
		        		public void mousePressed(MouseEvent e){

		        			ArrayList<Integer> tmp = new ArrayList<Integer>();
		        			tmp.addAll(grille.relieComposantesProxy((e.getX()-1)/50, (e.getY()-1)/50, joueur));
		        			System.out.println(tmp.size() + " : le nombre de composante reliables");
		        			for (int i = 0; i < tmp.size(); ++i) {
		        				System.out.print(tmp.get(i) + " ");
		        			}
		    				suppr();
		    				if (choix == 1) 
		    					joueDeuxHumains();
		    				//else if(choix == 2)
		    				//	joueOrdiHumain();
		    				
		        		}
		        });
			}
        });

        // existeCheminCases
        b7.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent a) {
				suppr();
				grille.addMouseListener(new MouseAdapter(){
		        		public void mousePressed(MouseEvent e){
	    					if (compt == 0){
	    						posTmpX = (e.getX()-1)/50;
	    						posTmpY = (e.getY()-1)/50;
	    						++compt;
	    					}
	    					else{
	    						System.out.println(grille.existeCheminCases(posTmpX, posTmpY, (e.getX()-1)/50, (e.getY()-1)/50));
								suppr();
								if (choix == 1)
									joueDeuxHumains();
							//	else if(choix == 2)
									//joueOrdiHumain();
								--compt;
	    					}
		        		}
		        });
			}
        });

        //Abandonner
        b8.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent a){

        		JFrame aband = new JFrame("Le joueur n°"+(joueur == 1 ? 1 : 2) + "a voulu arrêter de jouer !");

				aband.setSize(600,100);
				aband.setLocationRelativeTo(null);
				aband.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);   
				aband.setVisible(true);

				JButton oui = new JButton("Oui");
				JButton non = new JButton("Non");
				oui.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						dispose();
						aband.dispose();
						Menu newF = new Menu();
					}
				});

				non.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent arg0) {aband.dispose();}});

				GridLayout layout = new GridLayout(1, 2,2 ,2);
				aband.setLayout(layout);

				aband.add(oui);
				aband.add(non);
        	}
        });

        //relieCaseMin
        b9.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent a) {
				suppr();
				grille.addMouseListener(new MouseAdapter(){
		        		public void mousePressed(MouseEvent e){
	    					if (compt == 0){
	    						posTmpX = (e.getX()-1)/50;
	    						posTmpY = (e.getY()-1)/50;
	    						++compt;
	    					}
	    					else{
	    						System.out.println(grille.relierCasesMin(posTmpX, posTmpY, (e.getX()-1)/50, (e.getY()-1)/50));
								suppr();
								if (choix == 1)
									joueDeuxHumains();
								//else if(choix == 2)
									//joueOrdiHumain();
								--compt;
	    					}
		        		}
		        });
			}
        });

        //getTousFils
        b10.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent a) {
				suppr();
				grille.addMouseListener(new MouseAdapter(){
		        		public void mousePressed(MouseEvent e){
		        			grille.afficher((e.getX()-1)/50,(e.getY()-1)/50);
		    				suppr();
		    				if (choix == 1) 
		    					joueDeuxHumains();
		    				//else if(choix == 2)
		    						//joueOrdiHumain();
		        		}
		        });
			}
        });




        // Ajout des boutons au menu
        menu.add(b1);
		menu.add(b2);
		menu.add(b3);
		menu.add(b4);
		menu.add(b5);
		menu.add(b6);
		menu.add(b7);
		menu.add(b8);
		menu.add(b9);
		menu.add(b10);

		//------------------------------------------------------------------- Affichage graphique de la fenêtre 

		GroupLayout layout = new GroupLayout(this.getContentPane());
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup( 
			layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(affScoreJ1)
					.addComponent(affScoreJ2)
					.addComponent(grille))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(menu))
		);


		layout.setVerticalGroup( 
			layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(affScoreJ1))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(affScoreJ2))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(grille) 
					.addComponent(menu))
		);

		getContentPane().setLayout(layout);
		pack();


		if (choix == 1) {
			joueDeuxHumains();
		}
		//else if (choix == 2) {
			//joueOrdiHumain();
		//}
	}


	/*
	* @brief afficher score, méthode qui affiche le score entre les deux joueurs
	*/
	public void afficheScores(){
		affScoreJ1.setText("Score Player 1 : "+ scoreJ1);
		affScoreJ2.setText("Score Player2 : "+ scoreJ2);
	}


	//!\brief Méthode n°8 joueDeuxHumains
	public void joueDeuxHumains(){
		grille.addMouseListener(new MouseAdapter(){
    		public void mousePressed(MouseEvent e){	
    			int valueTile = grille.getTileSmall(e.getX(),e.getY()).getTilePlayer();
    			if (valueTile == 0 && (joueur == 1 || joueur == 2)){
    				if(joueur == 1){
    					grille.getTileSmall(e.getX(),e.getY()).colorerCase("red");
    				}else if(joueur == 2){
    					grille.getTileSmall(e.getX(),e.getY()).colorerCase("blue");
    				}
					
    				listeCoup.add((e.getX()-1)/50+((e.getY()-1)/50)*taille);

    				ArrayList<Integer> tmp = new ArrayList<Integer>();
    				tmp.addAll(grille.relieComposantesProxy((e.getX()-1)/50, (e.getY()-1)/50, joueur));

    				for (int i = 0; i < tmp.size(); ++i)
    					grille.union((e.getX()-1)/50, (e.getY()-1)/50, tmp.get(i)%taille, tmp.get(i)/taille);

 					int scoreTmp = grille.nombreEtoiles((e.getX()-1)/50, (e.getY()-1)/50);

        			if (joueur == 1){

        				if(scoreTmp > 1 && scoreTmp > scoreJ1){
        					scoreJ1 = scoreTmp;
        					afficheScores();
        					if (scoreJ1 > scoreJ2) {
        						vJ1 = true;
        						vJ2 = false;
        					}
        				}

        				++joueur;
        			}
        			else if(joueur == 2){

        				if(scoreTmp > 1 && scoreTmp > scoreJ2){
        					scoreJ2 = scoreTmp;
        					afficheScores();
        					    if (scoreJ2 > scoreJ2) {
        						vJ1 = false;
        						vJ2 = true;
        					}
        				}

        				--joueur;
        			}

        			if (scoreJ1 == nbBase || scoreJ2 == nbBase || (listeCoup.size() == taille*taille - nbBase*2 && (scoreJ1 != 0 || scoreJ2 != 0))) {

        				JFrame fin = new JFrame("Bravo !!");

        				fin.setSize(400,100);
        				fin.setLocationRelativeTo(null);
        				fin.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);   
        				fin.setVisible(true);

        				JButton bouton = new JButton("Bravo le joueur n°"+(vJ1 ? 1 : 2) +" a remporté la partie !");
        				bouton.addActionListener(new ActionListener(){
        					public void actionPerformed(ActionEvent arg0) {
        						dispose();
        						fin.dispose();
        						Menu newF = new Menu();
        					}
        				});
        				fin.add(bouton);
        			}
        			else if(listeCoup.size() == taille*taille - nbBase*2){
        				JFrame fin = new JFrame("...");

        				fin.setSize(400,100);
        				fin.setLocationRelativeTo(null);
        				fin.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);   
        				fin.setVisible(true);

        				JButton bouton = new JButton("Oops, on a un match nul !");
        				bouton.addActionListener(new ActionListener(){
        					public void actionPerformed(ActionEvent arg0) {
        						dispose();
        						fin.dispose();
        						Menu newF = new Menu();
        					}
        				});
        				fin.add(bouton);
        			}
        		}
        		else{
        			JFrame fenetre = new JFrame("Erreur");

					fenetre.setSize(200,100);
					fenetre.setLocationRelativeTo(null);
					fenetre.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);   
					fenetre.setVisible(true);
					fenetre.setAlwaysOnTop(true);
					setEnabled(false);

					JButton bouton = new JButton("La case est déjà coloré !");
					bouton.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent arg0) {fenetre.dispose(); setEnabled(true);}});
					fenetre.add(bouton);
				}
    		}
   		});
	}

	public void suppr(){
		MouseListener m[] = grille.getMouseListeners(); 
		if(m.length > 0)
			grille.removeMouseListener(m[0]);
	}
}
