import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class SuperHeroGuess {
    private static Scanner stdin = new Scanner(System.in);



    public static void instruct( )  {
        System.out.println("Please think of a super hero.");
        System.out.println("I will ask some yes/no questions to try to figure");
        System.out.println("out what you are.");
    }

    public static void play(BTNode<String> current)  {
        while (!current.isLeaf( )) {
            if (query(current.getData( ))) {
                current = current.getLeft( );
            } else {
                current = current.getRight( );
            }
        }
        System.out.print("My guess is " + current.getData( ) + ". ");
        if (!query("Am I right?")) {
            learn(current);
        } else {
            System.out.println("I knew it all along!");
        }
    }

    public static BTNode<String> beginningTree( )  {
        BTNode<String> root;
        BTNode<String> child;
        root = new BTNode<String>("Are you an Avenger ?", null, null);
        // Create and attach the left subtree.
        child = new BTNode<String>("Can you shoot webs?", null, null);
        child.setLeft(new BTNode<String>("Spider man", null, null));
        child.setRight(new BTNode<String>("Iron Man", null, null));
        root.setLeft(child);
        // Create and attach the right subtree.
        child = new BTNode<String>("Do you fly in the Sky ?", null, null);
        child.setLeft(new BTNode<String>("Superman", null, null));
        child.setRight(new BTNode<String>("Batman", null, null));
        root.setRight(child);
        return root;
    }

    public static BTNode<String> loadTreeFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String readLine = null;
        BTNode<String> temp = null;
        BTNode<String> parent = null;
        int nullCounter = 0;

        ArrayList<BTNode<String>> arrParentNodes = new ArrayList<BTNode<String>>();

        while ((readLine = br.readLine()) != null){
            // Reading each line to see if "null"
            if (!readLine.equals("null")){
                temp = new BTNode<String>(readLine, null, null);
                if (nullCounter > 0){
                    // Right Side
                    while (nullCounter > 0){
                        arrParentNodes.remove(arrParentNodes.size()-1);
                        nullCounter--;
                    }
                    parent = arrParentNodes.get(arrParentNodes.size()-1);
                    parent.right = temp;
                    parent = temp;
                    arrParentNodes.add(temp);

                }
                else {
                    // Parent does not exist
                    if (parent == null) {
                        parent = temp;
                        arrParentNodes.add(temp);
                    } else {
                        parent.left = temp;
                        parent = temp;
                        arrParentNodes.add(temp);

                    }
                }
                nullCounter = 0;
            }
            else{
               nullCounter++;
            }

        }
        if (arrParentNodes.size() <=0)
            return null;

        return arrParentNodes.get(0);
    }



    public static void learn(BTNode<String> current) {
        String guessAvenger;   // The animal that was just guessed
        String correctAvenger; // The animal that the user was thinking of
        String newQuestion;   // A question to distinguish the two animals
        // Set Strings for the guessed animal, correct animal and a new question.
        guessAvenger = current.getData( );
        System.out.println("I give up. What super hero are you? ");
        correctAvenger = stdin.nextLine( );
        System.out.println("Please type a yes/no question that will distinguish a");
        System.out.println(correctAvenger + " from a " + guessAvenger + ".");
        newQuestion = stdin.nextLine( );
        // Put the new question in the current node, and add two new children.
        current.setData(newQuestion);
        System.out.println("As a " + correctAvenger + ", " + newQuestion);
        if (query("Please answer")) {
            current.setLeft(new BTNode<String>(correctAvenger, null, null));
            current.setRight(new BTNode<String>(guessAvenger, null, null));
        }
        else  {
            current.setLeft(new BTNode<String>(guessAvenger, null, null));
            current.setRight(new BTNode<String>(correctAvenger, null, null));
        }
    }
    public static boolean query(String prompt) {
        String answer;
        System.out.print(prompt + " [Y or N]: ");
        answer = stdin.nextLine( ).toUpperCase( );
        while (!answer.startsWith("Y") && !answer.startsWith("N")) {
            System.out.print("Invalid response. Please type Y or N: ");
            answer = stdin.nextLine( ).toUpperCase( );
        }
        return answer.startsWith("Y");
    }

    public static void main(String[ ] args) throws IOException {
        BTNode<String> root = null;
        instruct( );

        // laoding file
        try{
            System.out.print("Enter a file name to load here:");
            String loadFileName = "";

            loadFileName = stdin.nextLine();
            root = loadTreeFile(loadFileName);

        }
        catch (Exception E){
            System.out.println("Something seems wrong with the file entered. Please try again.");
            System.exit(0);
        }
        //root = beginningTree( );

        do {
            play(root);
        } while (query("Shall we play again?"));
        System.out.println("Thanks for teaching me a thing or two.");
        System.out.println ("Do You want to save the tree Y or N :");
         //if Y
        String enter;
        String fileName;
        enter = stdin.nextLine( ).toUpperCase( );
        if (enter.equals("Y")){
            System.out.println("Enter file name:");
            // Capture filename
            fileName = stdin.nextLine();
            root.saveTreeFile(fileName);
        }

        System.out.println ("Here is the preorder print :");
        root.preorderPrint();

        System.out.println ("Here is the post print :");
        root.postorderPrint();

        System.out.println ("Here is the inorder print :");
        root.inorderPrint();

        System.out.println ("Here is the tree:");
        root.print(1);

    }




}