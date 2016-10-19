package ud.prog3.pr02;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.*;

import javax.swing.*;

/** Clase principal de minijuego de coche para Práctica 02 - Prog III
 * Ventana del minijuego.
 * @author Andoni Eguíluz
 * Facultad de Ingeniería - Universidad de Deusto (2014)
 */
public class VentanaJuego extends JFrame {
	private static final long serialVersionUID = 1L;  // Para serialización
	JPanel pPrincipal;         // Panel del juego (layout nulo)
	MundoJuego miMundo;        // Mundo del juego
	CocheJuego miCoche;        // Coche del juego
	MiRunnable miHilo = null;  // Hilo del bucle principal de juego	
	Boolean [] Teclas=new Boolean[4];
	Boolean andando=false;
	double aceleracion=0;

	/** Constructor de la ventana de juego. Crea y devuelve la ventana inicializada
	 * sin coches dentro
	 */
	public VentanaJuego() {
		//Inicializar Teclas
		Teclas[0]=false;
		Teclas[1]=false;
		Teclas[2]=false;
		Teclas[3]=false;
		// Liberación de la ventana por defecto al cerrar
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		// Creación contenedores y componentes
		pPrincipal = new JPanel();
		JPanel pBotonera = new JPanel();
		JButton bAcelerar = new JButton( "Acelera" );
		JButton bFrenar = new JButton( "Frena" );
		JButton bGiraIzq = new JButton( "Gira Izq." );
		JButton bGiraDer = new JButton( "Gira Der." );
		// Formato y layouts
		pPrincipal.setLayout( null );
		pPrincipal.setBackground( Color.white );
		// Añadido de componentes a contenedores
		add( pPrincipal, BorderLayout.CENTER );
//		pBotonera.add( bAcelerar );
//		pBotonera.add( bFrenar );
//		pBotonera.add( bGiraIzq );
//		pBotonera.add( bGiraDer );
		add( pBotonera, BorderLayout.SOUTH );
		// Formato de ventana
		setSize( 1000, 750 );
		setResizable( false );
		// Escuchadores de botones
//		bAcelerar.addActionListener( new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				miCoche.acelera( +10, 1 );
//				// System.out.println( "Nueva velocidad de coche: " + miCoche.getVelocidad() );
//			}
//		});
//		bFrenar.addActionListener( new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				miCoche.acelera( -10, 1 );
//				// System.out.println( "Nueva velocidad de coche: " + miCoche.getVelocidad() );
//			}
//		});
//		bGiraIzq.addActionListener( new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				miCoche.gira( +10 );
//				// System.out.println( "Nueva dirección de coche: " + miCoche.getDireccionActual() );
//			}
//		});
//		bGiraDer.addActionListener( new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				miCoche.gira( -10 );
//				// System.out.println( "Nueva dirección de coche: " + miCoche.getDireccionActual() );
//			}
//		});
		
		// Añadido para que también se gestione por teclado con el KeyListener
//		pPrincipal.addKeyListener( new KeyAdapter() {
//			@Override
//			public void keyPressed(KeyEvent e) {
//				switch (e.getKeyCode()) {
//					case KeyEvent.VK_UP: {
//						miCoche.acelera( +5, 1 );
//						break;
//					}
//					case KeyEvent.VK_DOWN: {
//						miCoche.acelera( -5, 1 );
//						break;
//					}
//					case KeyEvent.VK_LEFT: {
//						miCoche.gira( +10 );
//						break;
//					}
//					case KeyEvent.VK_RIGHT: {
//						miCoche.gira( -10 );
//						break;
//					}
//				}
//			}
//		});
		pPrincipal.addKeyListener( new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
			
				if(e.getKeyCode()==KeyEvent.VK_UP){
					Teclas[0]=true;
					
				}
				if(e.getKeyCode()==KeyEvent.VK_DOWN){
					Teclas[1]=true;
					
				}
				if(e.getKeyCode()==KeyEvent.VK_LEFT){
					Teclas[2]=true;
					
				}
				if(e.getKeyCode()==KeyEvent.VK_RIGHT){
					Teclas[3]=true;
					
				}
				
			}
			public void keyReleased(KeyEvent f){
				if(f.getKeyCode()==KeyEvent.VK_UP){
					Teclas[0]=false;
					andando=true;
					
				}
				if(f.getKeyCode()==KeyEvent.VK_DOWN){
					Teclas[1]=false;
					andando=true;
					
				}
				if(f.getKeyCode()==KeyEvent.VK_LEFT){
					Teclas[2]=false;
					
				}
				if(f.getKeyCode()==KeyEvent.VK_RIGHT){
					Teclas[3]=false;
					
				}
		
			}
		});

		
		pPrincipal.setFocusable(true);
		pPrincipal.requestFocus();
		pPrincipal.addFocusListener( new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				pPrincipal.requestFocus();
			}
		});
		// Cierre del hilo al cierre de la ventana
		addWindowListener( new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (miHilo!=null) miHilo.acaba();
			}
		});
	}
	
	/** Programa principal de la ventana de juego
	 * @param args
	 */
	public static void main(String[] args) {
		// Crea y visibiliza la ventana con el coche
		try {
			final VentanaJuego miVentana = new VentanaJuego();
			SwingUtilities.invokeAndWait( new Runnable() {
				@Override
				public void run() {
					miVentana.setVisible( true );
				}
			});
			miVentana.miMundo = new MundoJuego( miVentana.pPrincipal );
			miVentana.miMundo.creaCoche( 150, 100 );
			miVentana.miCoche = miVentana.miMundo.getCoche();
			miVentana.miCoche.setPiloto( "Fernando Alonso" );
			// Crea el hilo de movimiento del coche y lo lanza
			miVentana.miHilo = miVentana.new MiRunnable();  // Sintaxis de new para clase interna
			Thread nuevoHilo = new Thread( miVentana.miHilo );
			nuevoHilo.start();
		} catch (Exception e) {
			System.exit(1);  // Error anormal
		}
	}
	
	/** Clase interna para implementación de bucle principal del juego como un hilo
	 * @author Andoni Eguíluz
	 * Facultad de Ingeniería - Universidad de Deusto (2014)
	 */
	class MiRunnable implements Runnable {
		boolean sigo = true;
		@Override
		public void run() {
			// Bucle principal forever hasta que se pare el juego...
			while (sigo) {
				// Mover coche
				miCoche.mueve( 0.040 );
				// Chequear choques
				// (se comprueba tanto X como Y porque podría a la vez chocar en las dos direcciones (esquinas)
				if (miMundo.hayChoqueHorizontal(miCoche)) // Espejo horizontal si choca en X
					miMundo.rebotaHorizontal(miCoche);
				if (miMundo.hayChoqueVertical(miCoche)) // Espejo vertical si choca en Y
					miMundo.rebotaVertical(miCoche);
				//Mirar que teclas de control estan pulsadas y actuar
				
				if (Teclas[0]==true){
					//miCoche.acelera( +5, 1 );
					miMundo.aplicarFuerza( miCoche.fuerzaAceleracionAdelante(),miCoche );
					miCoche.setVelocidad(miMundo.calcVelocidadConAceleracion(miCoche.getVelocidad(), miMundo.calcAceleracionConFuerza(miCoche.fuerzaAceleracionAdelante(), miCoche.masa), 0.040));
					
					
				}
				if (Teclas[1]==true) {
					miMundo.aplicarFuerza( miCoche.fuerzaAceleracionAtras(),miCoche );
					miCoche.setVelocidad(-1*miMundo.calcVelocidadConAceleracion(miCoche.getVelocidad(), miMundo.calcAceleracionConFuerza(miCoche.fuerzaAceleracionAtras(), miCoche.masa), 0.040));
					
					
					
				}
				if (Teclas[2]==true) {
					miCoche.gira( +10 );
					
					
					
				}
				if (Teclas[3]==true) {
					miCoche.gira( -10 );
					
					
					
				}

				if ((Teclas[0]==false && Teclas[1]==false) && andando==true){
					miMundo.aplicarFuerza( 0,miCoche );
					if(miCoche.getVelocidad()==0){
						andando=false;
					}
				}
				
			
				
				// Dormir el hilo 40 milisegundos
				try {
					Thread.sleep( 40 );
				} catch (Exception e) {
				}
			}
		}
		/** Ordena al hilo detenerse en cuanto sea posible
		 */
		public void acaba() {
			sigo = false;
		}
	};
	
}
