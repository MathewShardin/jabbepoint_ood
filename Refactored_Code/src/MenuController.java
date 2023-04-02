import java.awt.MenuBar;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JOptionPane;

/** <p>The controller for the menu</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */
public class MenuController extends MenuBar {
	
	private Frame parent; //The frame, only used as parent for the Dialogs
	private Presentation presentation; //Commands are given to the presentation
	
	private static final long serialVersionUID = 227L;
	
	protected static final String TESTFILE = "testPresentation.xml";
	protected static final String SAVEFILE = "savedPresentation.xml";
	
	protected static final String IOEX = "IO Exception: ";
	protected static final String LOADERR = "Load Error";
	protected static final String SAVEERR = "Save Error";

	public MenuController(SlideViewerFrame frame) {
		SlideViewerFrame parent = frame;
		presentation = frame.getViewerComponent().getPresentation();
		MenuItem menuItem;
		Menu fileMenu = new Menu(MenuItemsNames.FILE.getName());
		fileMenu.add(menuItem = addMenuItem(MenuItemsNames.OPEN.getName()));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				presentation.clear();
				Accessor xmlAccessor = new XMLAccessor();
				try {
					xmlAccessor.loadFile(presentation, TESTFILE);
					presentation.setSlideNumber(0);
				} catch (IOException exc) {
					JOptionPane.showMessageDialog(parent, IOEX + exc, 
         			LOADERR, JOptionPane.ERROR_MESSAGE);
				}
				parent.updateSlide();
				parent.repaint();
			}
		} );
		fileMenu.add(menuItem = addMenuItem(MenuItemsNames.NEW.getName()));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				presentation.clear();
				parent.repaint();
				parent.updateSlide();
			}
		});
		fileMenu.add(menuItem = addMenuItem(MenuItemsNames.SAVE.getName()));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Accessor xmlAccessor = new XMLAccessor();
				try {
					xmlAccessor.saveFile(presentation, SAVEFILE);
				} catch (IOException exc) {
					JOptionPane.showMessageDialog(parent, IOEX + exc, 
							SAVEERR, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		fileMenu.addSeparator();
		fileMenu.add(menuItem = addMenuItem(MenuItemsNames.EXIT.getName()));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				presentation.exit(0);
			}
		});
		add(fileMenu);
		Menu viewMenu = new Menu(MenuItemsNames.VIEW.getName());
		viewMenu.add(menuItem = addMenuItem(MenuItemsNames.NEXT.getName()));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				presentation.nextSlide();
				parent.updateSlide();
			}
		});
		viewMenu.add(menuItem = addMenuItem(MenuItemsNames.PREV.getName()));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				presentation.prevSlide();
				parent.updateSlide();
			}
		});
		viewMenu.add(menuItem = addMenuItem(MenuItemsNames.GOTO.getName()));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				String pageNumberStr = JOptionPane.showInputDialog((Object)MenuItemsNames.PAGENR.getName());
				int pageNumber = Integer.parseInt(pageNumberStr);
				presentation.setSlideNumber(pageNumber - 1);
				parent.updateSlide();
			}
		});
		add(viewMenu);
		Menu helpMenu = new Menu(MenuItemsNames.HELP.getName());
		helpMenu.add(menuItem = addMenuItem(MenuItemsNames.ABOUT.getName()));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				AboutBox.show(parent);
			}
		});
		setHelpMenu(helpMenu);		//Needed for portability (Motif, etc.).
	}

	//Creating a menu-item
	public MenuItem addMenuItem(String name) {
		return new MenuItem(name, new MenuShortcut(name.charAt(0)));
	}
}
