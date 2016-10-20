package ud.prog3.pr02;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class JLabelEstrella extends JLabel {
	private static final long serialVersionUID = 1L;  // Para serialización
	public static final int TAMANYO_ESTRELLA = 40;  // píxels (igual ancho que algo)
	public static final int RADIO_ESFERA_ESTRELLA = 17;  // Radio en píxels del bounding circle del coche (para choques)
	private static final boolean DIBUJAR_ESFERA_ESTRELLA = true;  // Dibujado (para depuración) del bounding circle de choque del coche
	private long fechaCreacion;
	private double miGiro = Math.PI/2;
	protected static double miDireccionActual=0;
	public boolean girando=true;
	/** Construye y devuelve el JLabel del coche con su gráfico y tamaño
	 */
	public JLabelEstrella() {
		this.fechaCreacion=System.currentTimeMillis();
		// Esto se haría para acceder por sistema de ficheros
		// 		super( new ImageIcon( "bin/ud/prog3/pr00/coche.png" ) );
		// Esto se hace para acceder tanto por recurso (jar) como por fichero
		try {
			setIcon( new ImageIcon( JLabelEstrella.class.getResource( "img/estrella.png" ).toURI().toURL() ) );
		} catch (Exception e) {
			System.err.println( "Error en carga de recurso: coche.png no encontrado" );
			e.printStackTrace();
		}
		setBounds( 0, 0, TAMANYO_ESTRELLA, TAMANYO_ESTRELLA );
		// Esto sería útil cuando hay algún problema con el gráfico: borde de color del JLabel
		// setBorder( BorderFactory.createLineBorder( Color.yellow, 4 ));
	}
	public static int getRadioEsferaEstrella() {
		return RADIO_ESFERA_ESTRELLA;
	}
	@Override
	protected void paintComponent(Graphics g) {		
//		super.paintComponent(g);   // En este caso no nos sirve el pintado normal de un JLabel
		Image img = ((ImageIcon)getIcon()).getImage();
		Graphics2D g2 = (Graphics2D) g;  // El Graphics realmente es Graphics2D
		// Escalado más fino con estos 3 parámetros:
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);	
		// Prepara rotación (siguientes operaciones se rotarán)
       	g2.rotate( miGiro,  getIcon().getIconWidth()/2, getIcon().getIconHeight()/2 ); // getIcon().getIconWidth()/2, getIcon().getIconHeight()/2 );
        // Dibujado de la imagen       
        g2.drawImage( img, 0, 0, TAMANYO_ESTRELLA, TAMANYO_ESTRELLA, null );
        if (DIBUJAR_ESFERA_ESTRELLA) g2.drawOval( TAMANYO_ESTRELLA/2-RADIO_ESFERA_ESTRELLA, TAMANYO_ESTRELLA/2-RADIO_ESFERA_ESTRELLA,RADIO_ESFERA_ESTRELLA*2, RADIO_ESFERA_ESTRELLA*2 );
        
	}
	
	public long getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(long fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	

	public static void setDireccionActual( double dir ) {
		// if (dir < 0) dir = 360 + dir;
		if (dir > 360) dir = dir - 360;
		miDireccionActual = dir;
	}
	
	public   void gira( double giro ) {
		double grado=Math.PI/6;
		miGiro=miGiro+10;
		
		
		
	}
}



