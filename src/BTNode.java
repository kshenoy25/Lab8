import javax.xml.crypto.Data;
import java.io.*;
import java.util.*;
public class BTNode<E> {

    protected E data;
    protected BTNode<E> left, right;
    private static BufferedWriter bw;


    public BTNode(E initialData, BTNode<E> initialLeft, BTNode<E> initialRight)  {
        data = initialData;
        left = initialLeft;
        right = initialRight;

    }
    public E getData( ) { return data; }

    public BTNode<E> getLeft( ) { return left; }

    public E getLeftmostData( ) {
        if (left == null) return data;
        else return left.getLeftmostData( );
    }
    public BTNode<E> getRight( ) { return right; }

    public E getRightmostData( ) {
        if (right == null) return data;
        else return right.getRightmostData( );
    }

    public void setData(E newData) {  data = newData; }

    public void setLeft(BTNode<E> newLeft) { left = newLeft; }

    public void setRight(BTNode<E> newRight) { right = newRight; }

    public BTNode<E> removeLeftmost( ) {
        if (left == null) return right;
        else {
            left = left.removeLeftmost( );
            return this;
        }
    }
    public BTNode<E> removeRightmost( ) {
        if (right == null) return left;
        else  {
            right = right.removeRightmost( );
            return this;
        }
    }


    public void saveTreeFile (String sFileName) throws IOException {
        bw = new BufferedWriter(new FileWriter(sFileName));

        preorderFile();
        bw.flush();
        bw.close();

    }

    public boolean isLeaf( ) { return (left == null) && (right == null); }


    public void preorderFile( ) throws IOException {
       // System.out.println(data);
        bw.write((String) data);
        bw.newLine();

        if (left != null){
            left.preorderFile();
        }
        if (right != null){
            right.preorderFile();
        }
        bw.write("null");
        bw.newLine();

       // if (left != null) left.preorderFile( );
       // if (right != null) right.preorderFile( );
    }


    public void inorderPrint( ) {
        if (left != null) left.inorderPrint( );
        System.out.println(data);
        if (right != null) right.inorderPrint( );
    }

    public void preorderPrint( ) {
        System.out.println(data);
        if (left != null) left.preorderPrint( );
        if (right != null) right.preorderPrint( );
    }

    public void postorderPrint( ) {
        if (left != null) left.postorderPrint( );
        if (right != null) right.postorderPrint( );
        System.out.println(data);
    }



    public void print(int depth) {
        int i;
        // Print the left subtree (or LL if there is a right child & no left child)
        if (left != null) left.print(depth+1);
        else if (right != null){
            for (i = 1; i <= depth+1; i++)
                System.out.print("    ");
            System.out.println("LL");
        }
        // Print the indentation and the data from the current node:
        for (i = 1; i <= depth; i++)
            System.out.print("    ");
        System.out.println(data);
        // Print the right subtree (or RR if there is a left child & no right child) 
        if (right != null)
            right.print(depth+1);
        else if (left != null) {
            for (i = 1; i <= depth+1; i++)
                System.out.print("    ");
            System.out.println("RR");
        }
    }

    public static <E> BTNode<E> treeCopy(BTNode<E> source) {
        BTNode<E> leftCopy, rightCopy;
        if (source == null) return null;
        else {
            leftCopy = treeCopy(source.left);
            rightCopy = treeCopy(source.right);

            return new BTNode<E>(source.data, leftCopy, rightCopy);
        }
    }

    public static <E> long treeSize(BTNode<E> root)  {
        if (root == null) return 0;
        else
            return 1 + treeSize(root.left) + treeSize(root.right);
    }
//    public void readFile(){
//        FileInputStream fin = new FileInputStream("c:\\temp\\address.ser");
//        ObjectInputStream ois = new ObjectInputStream(fin);
//        address = (Address) ois.readObject();
//    }
} 