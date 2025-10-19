package com.example.__projekt_komputer.casing.hardware.computer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MenuOptionsTest {

    @Test
    void shouldReturnBack(){

        MenuOption choice1 = MenuOption.chosenAction("back", MenuIndicator.HARDWARE_MENU);
        MenuOption choice2 = MenuOption.chosenAction("back", MenuIndicator.MAIN_MENU);
        MenuOption choice3 = MenuOption.chosenAction("back", MenuIndicator.FILE_MENU);

        assertEquals(MenuOption.BACK, choice1);
        assertEquals(MenuOption.BACK, choice2);
        assertEquals(MenuOption.BACK, choice3);

    }

    @Test
    void shouldReturnEnd(){

        MenuOption choice1 = MenuOption.chosenAction("end", MenuIndicator.HARDWARE_MENU);
        MenuOption choice2 = MenuOption.chosenAction("end", MenuIndicator.MAIN_MENU);
        MenuOption choice3 = MenuOption.chosenAction("end", MenuIndicator.FILE_MENU);

        assertEquals(MenuOption.END, choice1);
        assertEquals(MenuOption.END, choice2);
        assertEquals(MenuOption.END, choice3);
    }

    @Test
    void shouldReturnDefault(){
        MenuOption choice1 = MenuOption.chosenAction("default", MenuIndicator.HARDWARE_MENU);
        MenuOption choice2 = MenuOption.chosenAction("default", MenuIndicator.MAIN_MENU);
        MenuOption choice3 = MenuOption.chosenAction("default", MenuIndicator.FILE_MENU);

        assertEquals(MenuOption.DEFAULT, choice1);
        assertEquals(MenuOption.DEFAULT, choice2);
        assertEquals(MenuOption.DEFAULT, choice3);
    }

    @Test
    void shouldReturnDefaultOnIncorrectOption(){
        MenuOption choice1 = MenuOption.chosenAction("incorrect option", MenuIndicator.HARDWARE_MENU);
        MenuOption choice2 = MenuOption.chosenAction("incorrect option", MenuIndicator.MAIN_MENU);
        MenuOption choice3 = MenuOption.chosenAction("incorrect option", MenuIndicator.FILE_MENU);

        assertEquals(MenuOption.DEFAULT, choice1);
        assertEquals(MenuOption.DEFAULT, choice2);
        assertEquals(MenuOption.DEFAULT, choice3);
    }

    @Test
    void shouldReturnFilesMenu() {
        MenuOption choice = MenuOption.chosenAction("2", MenuIndicator.MAIN_MENU);
        assertEquals(MenuOption.FILES_MENU, choice);
    }

    @Test
    void shouldReturnFindFileByName() {
        MenuOption choice = MenuOption.chosenAction("3", MenuIndicator.FILE_MENU);
        assertEquals(MenuOption.FIND_FILE_BY_NAME, choice);
    }

    @Test
    void shouldReturnDefaultWhenChoiceIsValidButMenuWrong() {
        MenuOption choice = MenuOption.chosenAction("4", MenuIndicator.MAIN_MENU);
        assertEquals(MenuOption.DEFAULT, choice); // bo "4" istnieje w FILE_MENU, ale nie w MAIN_MENU
    }

    @Test
    void shouldReturnSelfForEveryMenuOption() {
        for (MenuOption option : MenuOption.values()) {
            // przypadki specjalne które są obsłużone osobno
            if (option == MenuOption.DEFAULT || option == MenuOption.BACK || option == MenuOption.END) {
                continue;
            }

            MenuOption result = MenuOption.chosenAction(option.getChoice(), option.getMenuIndicator());
            assertEquals(option, result, "Błąd dla opcji: " + option.name());
        }
    }


}
