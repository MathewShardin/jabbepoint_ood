import java.util.ArrayList;

/**
 * This class is needed to keep track of different controllers.
 * It also provides functionality to attach them to the SlideViewerFrame to controll the app.
 * The highest class in the SlideViewerFrame-SlideViewerComponent-Presentation relationship.
 */
public class ControllerManager {
    private SlideViewerFrame viewerFrame;
    private ArrayList<ControllerInterface> controllerInterfaces;

    public ControllerManager(SlideViewerFrame viewerFrame) {
        this.viewerFrame = viewerFrame;
        this.controllerInterfaces = new ArrayList<>();
    }

    public void addControllerInterface(ControllerInterface interfaceTemp) {
        this.controllerInterfaces.add(interfaceTemp);
    }

    public void startApplication() {
        viewerFrame.setupWindow();
        for (ControllerInterface inter : this.controllerInterfaces) {
            inter.connectController(this.viewerFrame);
        }
    }
}
