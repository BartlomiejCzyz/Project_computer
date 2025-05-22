package com.example.__projekt_komputer.computer.hardware.computer;

public enum MenuOption {

    USB_DEVICES_MENU("1", MenuIndicator.MAIN_MENU),
    FILES_MENU("2", MenuIndicator.MAIN_MENU),
    HARDWARE_MENU("3", MenuIndicator.MAIN_MENU),

    ADD_USB_DEVICE("1" , MenuIndicator.USB_MENU),
    REMOVE_USB_DEVICE("2" , MenuIndicator.USB_MENU),
    LIST_USB_DEVICES("3",MenuIndicator.USB_MENU),

    ADD_FILE("1" , MenuIndicator.FILE_MENU),
    REMOVE_FILE("2" , MenuIndicator.FILE_MENU),
    FIND_FILE_BY_NAME("3" , MenuIndicator.FILE_MENU),
    FIND_FILE_BY_CONTENT("4" , MenuIndicator.FILE_MENU),
    LIST_ALL_FILES("5",MenuIndicator.FILE_MENU),

    ADD_HARDWARE("1",MenuIndicator.HARDWARE_MENU),
    REMOVE_HARDWARE("2",MenuIndicator.HARDWARE_MENU),
    LIST_HARDWARE("3",MenuIndicator.HARDWARE_MENU),
    SET_HIGH_MONITOR_RESOLUTION("4",MenuIndicator.HARDWARE_MENU),
    SET_LOW_MONITOR_RESOLUTION("5",MenuIndicator.HARDWARE_MENU),
    CHANGE_HEADPHONE_VOLUME("6",MenuIndicator.HARDWARE_MENU),
    SHOW_CURRENT_HEADPHONE_VOLUME("7",MenuIndicator.HARDWARE_MENU),
    BACK("back",MenuIndicator.UNIVERSAL),
    END("end", MenuIndicator.UNIVERSAL),
    DEFAULT( "default", MenuIndicator.UNIVERSAL);

    private final String choice;
    private final String menuIndicator;

    MenuOption(String choice, String menuIndicator) {
        this.choice = choice;
        this.menuIndicator = menuIndicator;
    }



    public static MenuOption chosenAction(String userChoice, String chosenMenuIndicator){
        MenuOption result = MenuOption.DEFAULT;
        for (MenuOption option : values()){
            if (userChoice.equals("back")){
                result = MenuOption.BACK;
            }
            else if (userChoice.equals("end")){
                result = MenuOption.END;
            }
            else if (option.choice.equals(userChoice) && option.menuIndicator.equals(chosenMenuIndicator)){
                result = option;
            }
        }
        return result;
    }
}
