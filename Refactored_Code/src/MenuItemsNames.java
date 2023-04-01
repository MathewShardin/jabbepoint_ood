public enum MenuItemsNames {
    ABOUT("About"),
    FILE("File"),
    EXIT("Exit"),
    GOTO("Go to"),
    HELP("Help"),
    NEW("New"),
    NEXT("Next"),
    OPEN("Open"),
    PAGENR("Page number?"),
    PREV("Prev"),
    SAVE("Save"),
    VIEW("View");

    private String name;

    MenuItemsNames(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
    }
