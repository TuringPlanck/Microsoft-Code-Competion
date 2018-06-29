/*
 * SearchBinaryTree
 * Copyright (C) 2015, Logan Martel
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class SearchBinaryTree {

	public class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int x) {
			val = x;
		}
	}

	Queue<TreeNode> queue = new LinkedList<TreeNode>();

	// breadth-first search implemented by queue
	public void BFS(TreeNode root) {
		if (root == null)
			return;
		queue.clear();
		queue.add(root);
		while (!queue.isEmpty()) {
			TreeNode node = queue.remove();
			System.out.print(node.val + ",");
			if (node.left != null)
				queue.add(node.left);
			if (node.right != null)
				queue.add(node.right);
		}
	}

	// build binary tree from inOrder and postOrder traversal input
	public TreeNode buildTree(int[] inOrder, int inOrdStart, int inOrdEnd,
			int[] postOrder, int postOrdStart, int postOrdEnd) {

		if (inOrdStart > inOrdEnd || postOrdStart > postOrdEnd) {
			return null;
		}

		// root = last key in postOrderTraversal
		int rootKey = postOrder[postOrdEnd];
		TreeNode root = new TreeNode(rootKey);

		// find rootKey in inOrderTraversal
		int r = 0;
		for (int i = 0; i < inOrder.length; i++) {
			if (inOrder[i] == rootKey) {
				r = i;
				break;
			}
		}

		// build left subtree at root's left child
		root.left = buildTree(inOrder, inOrdStart, r - 1, postOrder,
				postOrdStart, postOrdStart + r - (inOrdStart + 1));

		// build right subtree at root's right child
		root.right = buildTree(inOrder, r + 1, inOrdEnd, postOrder,
				postOrdStart + r - (inOrdStart + 1) + 1, postOrdEnd - 1);

		return root;
	}

	public SearchBinaryTree() {

		// line1=inOrderTraversal, line2=postOrderTraversal
		String line1 = "";
		String line2 = "";
		
		// read traversal results from text file
		try {
			File file = new File("PracticeInput.txt");
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			line1 = bufferedReader.readLine();
			line2 = bufferedReader.readLine();
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// parse inOrderTraversal to int array
		String[] ascii = line1.split(",");
		int[] inOrder = new int[ascii.length];
		for (int i = 0; i < ascii.length; i++) {
			inOrder[i] = Integer.parseInt(ascii[i]);
		}

		// parse postOrderTraversal to int array
		ascii = line2.split(",");
		int[] postOrder = new int[ascii.length];
		for (int i = 0; i < ascii.length; i++) {
			postOrder[i] = Integer.parseInt(ascii[i]);
		}

		// build binary tree from inOrder and postOrder traversal
		TreeNode root = buildTree(inOrder, 0, inOrder.length - 1, postOrder, 0,
				postOrder.length - 1);
		
		// Run BFS on binary tree and print nodes as visited
		BFS(root);
	}

	public static void main(String args[]) {
		// test SearchBinaryTree class
		SearchBinaryTree test = new SearchBinaryTree();
	}
}
