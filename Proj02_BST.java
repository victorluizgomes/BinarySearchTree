import java.io.*;
import java.util.Scanner;
/*
 * Java class name: Proj02_BST.java
 * 
 * Class and assignment name: CSc 345 Spring 17 - Project 2
 * 
 * Programmer: Victor Gomes
 * 
 * Description of class: A Binary Search Tree program that takes a file as an argument
 * and reads the file to perform different commands as: 
 * insert, search, debug, delete, count, preOrder and inOrder.
 * Each command does something specific to the Tree and some of them will have parameters.
 * The program assumes all commands in the file are in correct form.
 */
public class Proj02_BST {
	
	public static void main(String[] args){
		// will take an argument which is the name of the file to read.
		// if argument does not exist, print error and exit program.
		if(args.length < 1){
			System.out.println("Error: file name not given as argument!");
			return;
		}
		String fileName = args[0];
		String line = null;
		
		// Initialize the Binary Search Tree
		BinarySearchTree BST = new BinarySearchTree();
		
		// have a try catch, to catch any errors reading the file.
		try{
			// read the file
			FileReader fr = new FileReader(fileName);
			BufferedReader buffr = new BufferedReader(fr);
			
			while((line = buffr.readLine()) != null){
				Scanner sc = new Scanner(line);
				
				if(sc.hasNext()){ // if there is something in this line
					
					// gets the command and the parameter if any
					String command = sc.next();
					int parameter = -1;
					String fileNamePar = "";
					
					if(sc.hasNextInt()){
						parameter = sc.nextInt();
					}else if(sc.hasNext()){
						fileNamePar = sc.next();
					}
					
					if(command.equals("insert")){ // insert <int>
						BST.insertBST(parameter);
					}
					
					if(command.equals("search")){ // search <int>
						BST.searchBST(parameter);
					}
					
					if(command.equals("debug")){ // debug <filename>
						BST.debugBST(fileNamePar);
					}
					
					if(command.equals("delete")){ // delete <int>
						BST.deleteBST(parameter);
					}
					
					if(command.equals("count")){ // count
						BST.countBST();
					}

					if(command.equals("preOrder")){ // preOrder
						BST.preOrderBST();
					}

					if(command.equals("inOrder")){ // inOrder
						BST.inOrderBST();
					}
				}
				
				sc.close(); // close scanner
			}
			
			buffr.close(); // close file
		
		// print if errors occurred reading the file
		}catch(FileNotFoundException e){
			System.out.println("Error: can't open file '" + fileName + "'");
			
		}catch(IOException e){
			System.out.println("Error: can't read file '" + fileName + "'");
			e.printStackTrace();
		}
	}
}
