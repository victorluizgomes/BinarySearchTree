import java.io.*;
import java.util.Scanner;

/*
 * Java class name: BinarySearchTree.java
 * 
 * Class and assignment name: CSc 345 Spring 17 - Project 2
 * 
 * Programmer: Victor Gomes
 * 
 * Description of class: A Binary Search Tree class that creates a BST and
 * performs the operations of the commands for Proj02_BST.java
 */

public class BinarySearchTree {
	
	// Initialize a BST node
	class Node{
		int data;
		Node left;
		Node right;
		
		public Node(int data){
			this.data = data;
			left = null;
			right = null;
		}
	}
	
	public String nodesPreOrder; // holds a string with the pre-order of the tree (without root)
	public int count; // holds a count of elements in the tree
	
	public Node root;
	
	public BinarySearchTree(){
		this.root = null;
		this.count = 0;
		this.nodesPreOrder = "";
	}
	
	// Method that inserts a value on the binary search tree,
	// prints out a error message if it was unsuccessful.
	public void insertBST(int key){
		
		if(searchBSThelper(root, key) != null){
			System.out.println("ERROR: Could not insert " + key + 
					" because it is already in the tree.");
			return;
		}
		
		// if there is no duplicate, insert key without printing anything.
		root = insertBSThelper(root, key);
		
		count++; // increment count
	}
	
	// helper recursive method to insert the key into the tree.
	private Node insertBSThelper(Node root, int key){
		
		// if there are no nodes, newNode becomes root
		if(root == null){
			Node newNode = new Node(key);
			return newNode;
		}
		
		// recurse to the left if key is less than data
		// and recurse to the right if key is bigger than data
		if(key < root.data){
			root.left = insertBSThelper(root.left, key);
		}else if(key > root.data){
			root.right = insertBSThelper(root.right, key);
		}
		
		return root;
	}
	
	// Method that searches for a value on the binary search tree,
	// prints out a message if found or another if wasn't found.
	public void searchBST(int key){
		Node result = searchBSThelper(root, key);
		
		if(result == null){
			System.out.println("MISS: The value " + key + " was *NOT* found in the tree.");
		}else{
			System.out.println("HIT: The value " + key + " was found in the tree.");
		}
	}
	
	// helper recursive method to search the key into the tree.
	private Node searchBSThelper(Node root, int key){
		
		// if root = null, means we did not found, so returns null.
		// if root.data = key, means we found, return the reference to that node.
		if(root == null || root.data == key){
			return root;
		}
		
		// recurse to the right if key is greater than the data
		if(key > root.data){
			return searchBSThelper(root.right, key);
		}
		
		// otherwise recurse left
		return searchBSThelper(root.left, key);
	}
	
	// Method that takes a file name and prints out a .dot file, 
	// to the file name given.
	public void debugBST(String fileName){
		
		// have a try catch, to catch any errors writing to the file.
		try{
			
			// Initialize printWriter
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
			
			nodesPreOrder = "";
			
			// call method so nodesPreOrder has the node's values in pre-order
			preOrderBSTstring(root.left);
			preOrderBSTstring(root.right);
			
			// start writing digraph
			pw.println("digraph {");
			
			// if root is not null, do something, otherwise don't write anything else
			if(root != null){
				
				// write the 3 first lines with a arrow pointing at the root
				pw.println("  DUMMY [style=invis];");
				pw.println("  DUMMY -> " + root.data + ";");
				pw.println("  " + root.data + "[penwidth=2];");
				
				pw.println(); // newline
				
				pw.println("  " + root.data + ";");
				
				Scanner sc = new Scanner(nodesPreOrder); // initialize scanner
				
				int key = -1;
				// while the String nodesPreOrder still have elements on it, loop.
				while(sc.hasNext()){
					pw.println(); // newline
					
					key = Integer.parseInt(sc.next());

					// sets newNode to the parent of key
					Node newNode = findNodeParent(key, root);
					
					if (key < newNode.data){
						
						// if key = to the data on the left make a graph for it with a label L
						pw.println("  " + newNode.data + " -> " + key + " [label=\"L\"];");
						pw.println("  " + key);
						pw.println();
						
						// if right = to null, make an invisible arrow
						if(newNode.right == null){
							
							pw.println("  " + newNode.data + " -> R_" + newNode.data + " [style=invis];");
							pw.println("  R_" + newNode.data + " [style=invis];");
							pw.println();
						}
					}
					
					if (key > newNode.data){
						
						// if left = to null, make an invisible arrow
						if(newNode.left == null){
							
							pw.println("  " + newNode.data + " -> L_" + newNode.data + " [style=invis];");
							pw.println("  L_" + newNode.data + " [style=invis];");
							pw.println();
						}
						
						// if key = to the data on the right make a graph for it with a label R
						pw.println("  " + newNode.data + " -> " + key + " [label=\"R\"];");
						pw.println("  " + key);
						pw.println();
					}
				}
				
				sc.close(); // closes scanner
			}
			
			
			pw.println("}");
			pw.close(); // closes print writer
			
		// print if errors occurred writing the file
		}catch(FileNotFoundException e){
			System.out.println("Error: can't open file '" + fileName + "'");
			
		}catch(IOException e){
			System.out.println("Error: can't write to file '" + fileName + "'");
			e.printStackTrace();
		}
	}
	
	// A method that writes a string with all* the nodes of the current tree in pre-order.
	// The root is not included, because we write that manually to the file.
	private void preOrderBSTstring(Node root){
		
		if(root != null){
			nodesPreOrder = nodesPreOrder + root.data + " ";
			preOrderBSTstring(root.left);
			preOrderBSTstring(root.right);
		}
	}
	
	// Method that given a key and a Node returns the key's parent Node recursively.
	private Node findNodeParent(int key, Node root){
		
		// if root.left not null and key less than data
		if(root.left != null && key < root.data){
			// see if its left child is the key
			if(root.left.data == key){
				return root;
			}else{
				return findNodeParent(key, root.left); // recurse left
			}
		}
		
		// if root.right not null and key bigger than data
		if(root.right != null && key > root.data){
			// see if its right child is the key
			if(root.right.data == key){
				return root;
			}else{
				return findNodeParent(key, root.right); // recurse right
			}
		}
		
		return null;
	}
	
	// Method that searches for a value and deletes it if it exists.
	// prints out error message if it doesn't exist.
	public void deleteBST(int key){
		
		if(searchBSThelper(root, key) == null){
			System.out.println("ERROR: Could not delete " + key + 
					" because it was not in the tree.");
			return;
		}
		
		// if the key exists, delete the node without printing anything.
		root = deleteBSThelper(root, key);
		
		count--; // decrement count
	}
	
	// helper recursive method to delete the key from the tree.
	private Node deleteBSThelper(Node root, int key){
		
		// recurse until find root.data = key
		if(key < root.data){
			root.left = deleteBSThelper(root.left, key);
		}else if(key > root.data){
			root.right = deleteBSThelper(root.right, key);
		}else{ // if key = data, then we need to delete it
			
			// if left of node is null, return right node.
			// if right of node is null, return left node.
			// if right node was null too, return null.
			if(root.left == null){
				return root.right;
			}else if(root.right == null){
				return root.left;
			}
			
			// if neither right or left are null, we delete for node with two children.
			root.data = maxValue(root.left);
			
			// delete node with the max value of root.left that we just copied.
			root.left = deleteBSThelper(root.left, root.data);
		}
		
		return root;
	}
	
	// Method to find the biggest key, given a root
	// mostly used to find the predecessor.
	private int maxValue(Node root){
		
		// initialize max value to the root.data
		int max = root.data;
		
		// goes as deep as it can to the right
		// and sets max to the deepest value to the right.
		while(root.right != null){
			max = root.right.data;
			root = root.right;
		}
		
		return max;
	}
	
	// Method that counts the number of elements in the binary search tree
	// and prints out the answer.
	public void countBST(){
		System.out.println("Current tree size: " + count + " nodes");
	}
	
	// Method that prints out the BST in pre-order traversal
	// all in one line, separated by spaces.
	public void preOrderBST(){
		preOrderBSThelper(root);
		System.out.println("");
	}
	
	// helper recursive method, that prints the elements of the tree in pre-order.
	private void preOrderBSThelper(Node root){
		
		if(root != null){
			System.out.print(root.data + " ");
			preOrderBSThelper(root.left);
			preOrderBSThelper(root.right);
		}
	}
	
	// Method that prints out the BST in in-order traversal
	// all in one line, separated by spaces.
	public void inOrderBST(){
		inOrderBSThelper(root);
		System.out.println("");
	}

	// helper recursive method, that prints the elements of the tree in in-order.
	private void inOrderBSThelper(Node root) {

		if (root != null) {
			inOrderBSThelper(root.left);
			System.out.print(root.data + " ");
			inOrderBSThelper(root.right);
		}
	}
}
