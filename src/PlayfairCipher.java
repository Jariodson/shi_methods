public class PlayfairCipher {
    private String key;
    private char[][] playfairTable;

    public PlayfairCipher(String key) {
        this.key = key.toUpperCase().replaceAll("J", "I");
        playfairTable = generatePlayfairTable();
    }

    private char[][] generatePlayfairTable() {
        boolean[] usedLetters = new boolean[26];
        char[][] table = new char[5][5];
        int row = 0, col = 0;

        for (char letter : key.toCharArray()) {
            if (!usedLetters[letter - 'A']) {
                table[row][col] = letter;
                usedLetters[letter - 'A'] = true;
                col++;
                if (col == 5) {
                    col = 0;
                    row++;
                }
            }
        }

        for (char letter = 'A'; letter <= 'Z'; letter++) {
            if (letter == 'J') continue;
            if (!usedLetters[letter - 'A']) {
                table[row][col] = letter;
                col++;
                if (col == 5) {
                    col = 0;
                    row++;
                }
            }
        }

        return table;
    }

    private String prepareText(String text) {
        text = text.toUpperCase().replaceAll("J", "I");
        StringBuilder preparedText = new StringBuilder();

        for (int i = 0; i < text.length(); i += 2) {
            preparedText.append(text.charAt(i));
            if (i + 1 < text.length()) {
                if (text.charAt(i) == text.charAt(i + 1)) {
                    preparedText.append('X');
                }
                preparedText.append(text.charAt(i + 1));
            } else {
                preparedText.append('X');
            }
        }

        return preparedText.toString();
    }

    private String encrypt(String text) {
        StringBuilder ciphertext = new StringBuilder();
        for (int i = 0; i < text.length(); i += 2) {
            char firstLetter = text.charAt(i);
            char secondLetter = text.charAt(i + 1);
            int[] firstLetterPosition = getLetterPosition(firstLetter);
            int[] secondLetterPosition = getLetterPosition(secondLetter);
            int firstRow = firstLetterPosition[0];
            int firstCol = firstLetterPosition[1];
            int secondRow = secondLetterPosition[0];
            int secondCol = secondLetterPosition[1];

            if (firstRow == secondRow) {
                firstCol = (firstCol + 1) % 5;
                secondCol = (secondCol + 1) % 5;
            } else if (firstCol == secondCol) {
                firstRow = (firstRow + 1) % 5;
                secondRow = (secondRow + 1) % 5;
            } else {
                int temp = firstCol;
                firstCol = secondCol;
                secondCol = temp;
            }

            ciphertext
                    .append(playfairTable[firstRow][firstCol])
                    .append(playfairTable[secondRow][secondCol]);
        }

        return ciphertext.toString();
    }

    private int[] getLetterPosition(char letter) {
        int[] position = new int[2];
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                if (playfairTable[row][col] == letter) {
                    position[0] = row;
                    position[1] = col;
                    return position;
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        String keyword = "KEYWORD"; // Ключ для шифра Плейфера
        String plaintext = "YOUR NAME"; // Фамилия, имя, отчество для шифрования

        PlayfairCipher cipher = new PlayfairCipher(keyword);
        String ciphertext = cipher.encrypt(cipher.prepareText(plaintext));
        System.out.println("Ciphertext: " + ciphertext);
    }
}

