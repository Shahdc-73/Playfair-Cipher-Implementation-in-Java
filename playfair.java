package playfair;

import java.util.Arrays;

public class PlayfairCipher {

    private static boolean isFound(char[][] letters, char c) {
        for (int i = 0; i < letters.length; i++) {
            for (int j = 0; j < letters.length; j++) {
                if (letters[i][j] == c) {
                    return true;
                }
            }
        }
        return false;
    }

    public static char[][] fillMatrixWithKey(String key) {
        key = key.toLowerCase();
        key = key.replaceAll("i", "j");
        char[][] letters = new char[5][5];
        int i = 0, j = 0;
        for (char c : key.toCharArray()) {
            if (!isFound(letters, c)) {
                letters[i][j] = c;
                j++;
                if (j == 5) {
                    i++;
                    j = 0;
                }
            }
        }
        return letters;
    }

    private static int[] findIJ(char[][] letters) {
        for (int i = 0; i < letters.length; i++) {
            for (int j = 0; j < letters.length; j++) {
                if (letters[i][j] == '\0') {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    public static String addXToText(String text) {

        char[] characters = text.toCharArray();
        for (int i = 1; i < characters.length; i++) {
            if (characters[i] == characters[i - 1]) {
                String s1 = text.substring(0, i);
                String s2 = text.substring(i);
                text = s1 + "x" + s2;
            }
        }
        if (text.length() % 2 == 1) {
            text += "x";
        }
        return text;
    }

    public static void fillMatrixWithAlphabets(char[][] letters) {
        int[] ij = findIJ(letters);
        int i = ij[0], j = ij[1];
        for (char ch = 'a'; ch <= 'z'; ch++) {
            if (ch == 'i') {
                continue;
            }
            if (!isFound(letters, ch)) {
                letters[i][j] = ch;
                j++;
                if (j == 5) {
                    i++;
                    j = 0;
                }
            }
        }
    }

    private static int[] searchForChar(char ch, char[][] letters) {
        for (int i = 0; i < letters.length; i++) {
            for (int j = 0; j < letters.length; j++) {
                if (letters[i][j] == ch) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    public static String encrypt(String text, char[][] letters) {
        String cipher = "";
        text = text.replaceAll("i", "j");
        text = addXToText(text);
        System.out.println(text);

        for (int i = 0; i < text.length(); i += 2) {
            char x = text.charAt(i);
            char y = text.charAt(i + 1);
            int[] p1 = searchForChar(x, letters);
            int[] p2 = searchForChar(y, letters);
            if (p1[0] == p2[0]) // same row
            {
                cipher += letters[p1[0]][(p1[1] + 1) % 5] + "" + letters[p2[0]][(p2[1] + 1) % 5];
            } else if (p1[1] == p2[1])// same col
            {
                cipher += letters[(p1[0] + 1) % 5][p1[1]] + "" + letters[(p2[0] + 1) % 5][p2[1]];
            } else {
                cipher += letters[p1[0]][p2[1]] + "" + letters[p2[0]][p1[1]];
            }
        }
        return cipher;
    }

    public static String decrypt(String cipher, char[][] letters) {
        String text = "";
        cipher = cipher.replaceAll("i", "j");

        for (int i = 0; i < cipher.length(); i += 2) {
            char x = cipher.charAt(i);
            char y = cipher.charAt(i + 1);
            int[] p1 = searchForChar(x, letters);
            int[] p2 = searchForChar(y, letters);
            System.out.println(x + " " + Arrays.toString(p1));
            System.out.println(y + " " + Arrays.toString(p2));

            if (p1[0] == p2[0]) // same row
            {
                int c = p1[1] - 1;
                if (c < 0) {
                    c += 5;
                }
                int t = p2[1] - 1;
                if (t < 0) {
                    t += 5;
                }
                text += letters[p1[0]][c] + "" + letters[p2[0]][t];
            } else if (p1[1] == p2[1])// same col
            {
                int c = p1[0] - 1;
                if (c < 0) {
                    c += 5;
                }
                int t = p2[0] - 1;
                if (t < 0) {
                    t += 5;
                }
                text += letters[c][p1[1]]
                        + "" + letters[t][p2[1]];
            } else {
                text += letters[p1[1]][p2[0]]
                        + "" + letters[p2[1]][p1[0]];
            }
        }
        return text;
    }

    public static void main(String[] args) {
        String k = "monarchy";
        String text = "mosque";
        char[][] m = fillMatrixWithKey(k);
        fillMatrixWithAlphabets(m);
        System.out.println(encrypt(text, m));
        System.out.println(decrypt("ontsml", m));
    }
}
