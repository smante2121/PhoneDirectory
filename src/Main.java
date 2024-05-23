package prog02;

import javax.swing.*;

/**
 * A program to query and modify the phone directory stored in csc220.txt.
 *
 * @author vjm
 */
public class Main {

    /**
     * Processes user's commands on a phone directory.
     *
     * @param fn The file containing the phone directory.
     * @param ui The UserInterface object to use
     *           to talk to the user.
     * @param pd The PhoneDirectory object to use
     *           to process the phone directory.
     */
    public static void processCommands(String fn, UserInterface ui, PhoneDirectory pd) {

        pd.loadData(fn);
        boolean changed = false;

        String[] commands = {"Add/Change Entry", "Look Up Entry", "Remove Entry", "Save Directory", "Exit"};

        String name, number, oldNumber;

        while (true) {
            int c = ui.getCommand(commands);
            switch (c) {
                case -1:
                    ui.sendMessage("You shut down the program, restarting.  Use Exit to exit.");
                    break;

                case 0: //add or change
                    name = ui.getInfo("Enter name: ");
                    if (name == null) {break;}

                    if (name.isEmpty()) {
                        ui.sendMessage("Blank is not allowed!");
                        break;
                    }

                    number= ui.getInfo("Enter number: ");

                    if (number == null) {break;}

                    oldNumber= pd.addOrChangeEntry(name,number);

                    if (oldNumber!= null){
                        ui.sendMessage(name + "'s number has been changed from " + oldNumber+ " to " +number + ".");
                        changed=true;
                        break;
                    }

                    if (number.isBlank()){
                        ui.sendMessage(name + " has been added to the directory.");
                    }

                    else {
                    ui.sendMessage(name + " has been added to directory with number "+ number + ".");
                    changed = true;
                    }

                    break;

                case 1: //look up

                    name = ui.getInfo("Enter the name ");
                    if (name == null) {break;}
                    if (name.isEmpty()) {
                        ui.sendMessage("Blank is not allowed!");
                        break;
                    }

                    number = pd.lookupEntry(name);

                    if (number !=null) {
                        ui.sendMessage(name + " has number " + number);
                    }

                    if (number == null){break;}
                    else {


                        ui.sendMessage("Name is not in Directory.");
                    }

                    break;

                case 2: //remove

                    name = ui.getInfo("Enter name to remove: ");
                    if (name == null) {break;}

                    if (name.isEmpty()) {
                        ui.sendMessage("Blank is not allowed!");
                        break;
                    }

                    number= pd.removeEntry(name);

                    if (number !=null) {
                        ui.sendMessage(name + " has been removed from directory.");
                        changed = true;
                    }

                    else {ui.sendMessage("Name is not in Directory.");}

                    break;

                case 3: //save

                    pd.save();
                    ui.sendMessage("Changes have been saved to the Directory.");
                    changed= false;
                    break;

                case 4: //exit

                  if (changed){
                      ui.sendMessage("Changes have been made to the directory. I will now ask if you want to exit without saving.");
                      String[] exitOptions = {"Yes", "No"};
                      int exitChoice= ui.getCommand(exitOptions);
                      if (exitChoice == 1) {break;}
                      else {return;}
                  }
                  return;
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String fn = "csc220.txt";
        PhoneDirectory pd = new SortedPD();
        //PhoneDirectory pd = new ArrayBasedPD();
        //UserInterface ui = new ConsoleUI();
       UserInterface ui = new GUI("Phone Directory");
       // UserInterface ui = new TestUI("Phone Directory");
        processCommands(fn, ui, pd);
    }
}
