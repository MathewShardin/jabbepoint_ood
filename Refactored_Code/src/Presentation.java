import java.util.ArrayList;


/**
 * <p>Presentations keeps track of the slides in a presentation.</p>
 * <p>Only one instance of this class is available.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class Presentation {
	private String presTitle; //The title of the presentation
	private ArrayList<Slide> slidesList = null; //An ArrayList with slides
	private int currentSlideNumber = 0; //The number of the current slide

	public Presentation() {
		clear();
	}

	public int getNumOfSlides() {
		return slidesList.size();
	}

	public String getTitle() {
		return presTitle;
	}

	public void setTitle(String nt) {
		presTitle = nt;
	}


	//Returns the number of the current slide
	public int getCurrentSlideNumber() {
		return currentSlideNumber;
	}

	//Change the current slide number and report it to the window
	public void setSlideNumber(int number) {
		//Out of bounds slide number check
		if (number < 0 || number >= getNumOfSlides()) {
			return;
		}
		currentSlideNumber = number;
	}

	//Navigate to the previous slide unless we are at the first slide
	public void prevSlide() {
		if (currentSlideNumber > 0) {
			setSlideNumber(currentSlideNumber - 1);
	    }
	}

	//Navigate to the next slide unless we are at the last slide
	public void nextSlide() {
		if (currentSlideNumber < (slidesList.size()-1)) {
			setSlideNumber(currentSlideNumber + 1);
		}
	}

	//Remove the presentation
	void clear() {
		slidesList = new ArrayList<Slide>();
		setSlideNumber(0);
	}

	//Add a slide to the presentation
	public void appendSlide(Slide slide) {
		slidesList.add(slide);
	}

	//Return a slide with a specific number
	public Slide getSlideByNumber(int number) {
		if (number < 0 || number >= getNumOfSlides()){
			return null;
	    }
			return (Slide) slidesList.get(number);
	}

	//Return the current slide
	public Slide getCurrentSlide() {
		return getSlideByNumber(getCurrentSlideNumber());
	}

	public void exit(int n) {
		System.exit(n);
	}
}
