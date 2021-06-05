package org.naimuri;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class Main {
    static int n;
    public static void main(String argv[]) throws IOException, URISyntaxException {
        long startTime = System.nanoTime();

        String[] args = "4 aaccdeeeemmnnnoo".split(" ");
        n = Integer.parseInt(args[0]);
        char[] charset = args[1].toCharArray();

        Trie wordTrie = new Trie();
        int[] charFreq = new int[26];
        for (char c : charset)
            charFreq[c - 'a']++;

        List<String> words = WordSquareSolver.createDictionary("enable1.txt", args[1], Integer.parseInt(args[0]));

        //extracted(wordTrie, words);
        for (String word : words) {
            wordTrie.insert(word);
        }

        char[][] result = getWordSquare(wordTrie, charFreq);
        long endTime = System.nanoTime();

        if (result != null)
            for (int i = 0; i < result.length; i++)
                System.out.println(new String(result[i]));
        else
            System.out.println("No valid word square could be made.");

        System.out.println("\nExecution Time: " + Double.toString((endTime - startTime) / 1000000000.0)+"s");
    }


    private static void extracted(Node wordTrie, List<String> words) {
        for (String word : words){
            Node curNode = wordTrie;
            for (int i = 0; i < word.length(); i++) {
                int c = word.charAt(i) - 'a';
                if (curNode.children[c] == null)
                    curNode.children[c] = new Node(c);
                curNode = curNode.children[c];
            }
        }
    }

    static char[][] getWordSquare(Node -, int[] charFreq) {
        Node[][] mat = new Node[n][n+1];
        for (int i = 0; i < mat.length; i++)
            mat[i][0] = trieRoot;
        int[] bank = charFreq.clone();
        if (rec(0, 1, mat, bank)) {
            char[][] result = new char[n][n];
            for (int r = 0; r < n; r++)
                for (int c = 0; c < n; c++)
                    result[r][c] = (char) (mat[r][c + 1].val + 'a');
            return result;
        } else {
            return null;
        }
    }

    // fills out the word bank, one character at a time. Keep in mind that the first column of every row
    // contains the root node, so indexes needed to be adjusted accordingly.
    static boolean rec(int r, int c, Node[][] mat, int[] bank)  {
        int incrAmt = r==c-1 ? 1 : 2; // need 1 for a diagonal, 2 otherwise (since it's mirrored)

        for (int l = 0; l < 26; l++) {
            Node node = mat[r][c-1].children[l];
            Node nodeMirrorSide = mat[c-1][r].children[l];
            if (node != null && nodeMirrorSide != null && bank[l] >= incrAmt) {

                mat[r][c] = node;
                mat[c - 1][r + 1] = nodeMirrorSide;
                bank[l] -= incrAmt; // remove letter from bank

                // try next position
                if (c == n) { // no more columns in this row
                    if (r == n - 1 // no more rows either; end of word square (SUCCESS)
                            || rec(r + 1, r + 2, mat, bank)) { // move to next row
                        return true;
                    }
                } else if (rec(r, c + 1, mat, bank)) { // move to next column
                    return true;
                }

                bank[l] += incrAmt; // add letter back to bank
            }
        }
        return false; // current branch cannot produce a valid word square
    }

    // returns whether the word can be used, given the letters provided. Takes into account the fact that all
    // but one of the letters (the one on the diagonal) in the word must occur twice in the final word square.
    private static boolean fitsInLetterBank(String word, int[] charFreq) {
        int[] charsUsed = new int[26];
        boolean diagonalUsed = false;
        for (int i = 0; i < word.length(); i++) {
            int c = word.charAt(i) - 'a';

            int spaceLeft = charFreq[c] - charsUsed[c];
            if (spaceLeft > 1) { // try fitting letter in a non-diagonal slot
                charsUsed[c] += 2;
            } else if (spaceLeft == 1 && !diagonalUsed) { // else, try fitting it in the diagonal slot
                charsUsed[c] += 1;
                diagonalUsed = true;
            } else { // no space for letter
                return false;
            }
        }
        return true;
    }

    // Used to build the trie. Sacrifices space for lightning fast lookups.
    static class Node {
        int val;
        Node[] children;

        Node() {
            children = new Node[26];
        }
        Node(int val){
            this();
            this.val = val;
        }
    }
}
