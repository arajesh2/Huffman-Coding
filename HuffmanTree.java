// Aniruddha Rajesh
// 5/27/2017
// CSE 143
// TA: Kevin Liang
// Assignment #8

// This class will manipulate a HuffmanTree to assist in text file compression and decompression. 

import java.util.*;
import java.io.*;

public class HuffmanTree {
   HuffmanNode overallRoot;
   
   // Constructor to create a HuffmanTree
   // Takes in an array of character frequencies as parameter.
   // Creates a properly ordered HuffmanTree composed of HuffmanNodes and their respective 
   // frequencies and character values. Inserts EOF character to HuffmanTree.
   public HuffmanTree(int[] counts) {
      Queue<HuffmanNode> pq = new PriorityQueue<HuffmanNode>();
      for (int i = 0; i < counts.length; i++) {
         if (counts[i] != 0) {
            pq.add(new HuffmanNode(i, counts[i]));
         }
      }   
      pq.add(new HuffmanNode(null, null, counts.length, 1));
      while (pq.size() >= 2) {
         HuffmanNode one = pq.remove();
         HuffmanNode two = pq.remove();
         pq.add(new HuffmanNode(one, two, 0, one.frequency + two.frequency));
      }
      overallRoot = pq.remove();
   }
   
   // This class defines a HuffmanNode that can be used to construct a HuffmanTree
   // Implements the Comparable interface. 
   private class HuffmanNode implements Comparable<HuffmanNode> {
      
      public HuffmanNode left;
      public HuffmanNode right;
      public int val;
      public int frequency;
      
      // Constructor to create a leaf HuffmanNode.
      // Takes in the character value of the node as an int.
      // Takes in the frequency of the character as an int. 
      public HuffmanNode(int val, int frequency) {
         this(null, null, val, frequency);
      }
      
      // Constructor to create a branch HuffmanNode.
      // Takes in the left child of the node as a HuffmanNode.
      // Takes in the right child of the node as a HuffmanNode. 
      // Takes in the character value of the node as an int.
      // Takes in the frequency of the character as an int.
      public HuffmanNode(HuffmanNode left, HuffmanNode right, int val, int frequency) {
         this.left = left;
         this.right = right;
         this.val = val;
         this.frequency = frequency;
      }
      
      // Returns the String representaton of the HuffmanNode as the character value of the node
      // followed by its frequency. 
      public String toString() {
         return val + ": " + frequency;
      }
      
      // Takes in other HuffmanNode as parameter.
      // Compares the two HuffmanNodes and returns a positive number if the given node has a
      // frequency that is higher than the other node. Returns 0 if both nodes have equal
      // frequencies, or a negative number if the frequency of this node is less than the 
      //frequency of the other node. 
      public int compareTo(HuffmanNode other) {
         return frequency - other.frequency;
      }
   }
   
   // Writes the contents of the HuffmanTree to the output stream in the standard format. 
   // The standard format is defined as line pairs where the first line is the ASCII value of
   // the given character and the second line is the code for getting that character value. 
   // Takes in the output PrintStream object as a parameter. 
   public void write(PrintStream output) {
      write(output, "", overallRoot);
   }
   
   // Takes in the output printer PrintStream object.  
   // Takes in the code for a character as a String.
   // Takes in the current reference as a HuffmanNode.
   // Returns a HuffmanNode with the given code.
   private HuffmanNode write(PrintStream output, String code, HuffmanNode current) {
      if (current.left == null && current.right == null) {
         output.println(current.val);
         output.println(code);
      } else {
         write(output, code + "0", current.left);
         write(output, code + "1", current.right);
      }
      return current;
   }
   
   // Constructor to construct the HuffmanTree from a code file. 
   // Takes in a Scanner object to read in input from code file. 
   // Assumes that the input is in standard format as described in write method.   
   public HuffmanTree(Scanner input) {
      overallRoot = treeBuild(input, new HuffmanNode(0, 0));
   }
   
   // Takes in a Scanner object to read in input from code file. Takes in the temporary root of
   // the HuffmanTree as a HuffmanNode.
   private HuffmanNode treeBuild(Scanner input, HuffmanNode tempRoot) {
      while (input.hasNextInt()) {
         int n = Integer.parseInt(input.nextLine());
         String code = input.nextLine();
         HuffmanNode branch = new HuffmanNode(n, 0);
         tempRoot = addBranch(code, tempRoot, branch);
      }
      return tempRoot;
   }
   
   // Takes in a binary code from the code file and uses it to construct a portion of the HuffmanTree. 
   // Takes in a reference to the current HuffmanTree as a HuffmanNode.
   // Takes in a empty branch of the tree as a HuffmanNode. 
   // Returns a HuffmanNode as a reference to the appended tree. 
   private HuffmanNode addBranch(String code, HuffmanNode currentRoot, HuffmanNode branch) {
      if (currentRoot == null) {
         currentRoot = new HuffmanNode(0,0);
      }
      if (code.length() > 0) {
         String codeNum = code.substring(0,1);
         code = code.substring(1);
         if (codeNum.equals("1")) {
            currentRoot.right = addBranch(code, currentRoot.right, branch);
         } else {   
            currentRoot.left = addBranch(code, currentRoot.left, branch);
         }
      } else {
         currentRoot = branch;
      }
      return currentRoot;   
   }
   
   // Takes in a BitInputStream object to read in individual bits. 
   // Takes in a Prinstream object to output the decoded characters. 
   // Takes in an integer that is the ASCII value of the end of file character.
   // Reads in bits from the input stream and outputs all respective decoded characters before
   // the end of file character. Assumes that the input stream has only valid encoded characters 
   // that correspond to the encoding of this HuffmanTree.  
   public void decode(BitInputStream input, PrintStream output, int eof) {
      HuffmanNode temp = overallRoot;
      boolean endOfFile = false;
      while (!endOfFile) {
         int single = input.readBit();
         if (temp.val == eof) {
            endOfFile = true;
         } else if (temp.left == null && temp.right == null) {
            output.write(temp.val);
            temp = overallRoot;   
         }         
         if(single == 0) {
            temp = temp.left;
         } else if (single == 1) {
            temp = temp.right;
         }         
      }      
   }
}  