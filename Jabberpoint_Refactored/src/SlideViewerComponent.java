import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import javax.swing.JComponent;
import javax.swing.JFrame;


/** <p>SlideViewerComponent is a graphical component that ca display Slides.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class SlideViewerComponent extends JComponent {
	private Font labelFont = null; //The font for labels
	private Presentation presentation = null; //The presentation
	
	private static final long serialVersionUID = 227L;
	
	private static final Color BGCOLOR = Color.white;
	private static final Color COLOR = Color.black;
	private static final String FONTNAME = "Dialog";
	private static final int FONTSTYLE = Font.BOLD;
	private static final int FONTHEIGHT = 10;
	private static final int XPOS = 1100;
	private static final int YPOS = 20;

	public SlideViewerComponent() {
		setBackground(BGCOLOR); 
		this.presentation = new Presentation();
		labelFont = new Font(FONTNAME, FONTSTYLE, FONTHEIGHT);
	}

	public Presentation getPresentation() {
		return this.presentation;
	}

	//Draw the slide itself + slide counter
	public void paintComponent(Graphics g) {
		this.repaint();
		Slide slideTemp = presentation.getCurrentSlide();
		g.setColor(BGCOLOR);
		g.fillRect(0, 0, getSize().width, getSize().height);
		if (presentation.getSlideNumber() < 0 || slideTemp == null) {
			return;
		}
		g.setFont(labelFont);
		g.setColor(COLOR);
		g.drawString("Slide " + (1 + this.presentation.getSlideNumber()) + " of " +
                 this.presentation.getSize(), XPOS, YPOS);
		Rectangle area = new Rectangle(0, YPOS, getWidth(), (getHeight() - YPOS));
		this.draw(g, area, slideTemp);
	}

	//Draw contents of the slide
	public void draw(Graphics g, Rectangle area, Slide slide) {
		float scale = getScale(area);
		int y = area.y;

		//The title is treated separately
		SlideItem slideItemTitle = new TextItem(0, slide.getTitle());
		Style style = JabberPoint.getStyle(slideItemTitle.getLevel());
		slideItemTitle.draw(area.x, y, scale, g, style);
		y += slideItemTitle.getBoundingBox(g, scale, style).height;

		// Draw other contents of the slide
		for (SlideItem slideItemLoop : slide.getSlideItems()) {
			//Check if a slideItem is an image to give it an ImageObserver
			if (slideItemLoop instanceof BitmapItem) {
				BitmapItem bitmapItem = (BitmapItem) slideItemLoop;
				bitmapItem.setImageObserver(this);
			}
			style = JabberPoint.getStyle(slideItemLoop.getLevel());
			slideItemLoop.draw(area.x, y, scale, g, style);
			y += slideItemLoop.getBoundingBox(g, scale, style).height;
		}
	}

	//Returns the scale to draw a slide
	private float getScale(Rectangle area) {
		return Math.min(((float)area.width) / ((float)SlideViewerFrame.WIDTH), ((float)area.height) / ((float)SlideViewerFrame.HEIGHT));
	}

}
