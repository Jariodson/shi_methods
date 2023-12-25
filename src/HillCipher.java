import java.util.Scanner;

public class HillCipher {

    private static final int MATRIX_SIZE = 3; // Размер матрицы ключа

    public static void main(String[] args) {
        // Ввод ключа (матрицы)
        int[][] keyMatrix = inputKey();

        // Ввод текста для шифрования
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите фамилию имя отчество для шифрования: ");
        String plaintext = scanner.nextLine();

        // Добавляем символы заполнения, если длина текста не делится нацело на размер матрицы
        while (plaintext.length() % MATRIX_SIZE != 0) {
            plaintext += "X";
        }

        // Шифрование текста
        String ciphertext = encrypt(plaintext, keyMatrix);

        // Вывод зашифрованного текста
        System.out.println("Зашифрованный текст: " + ciphertext);

        scanner.close();
    }

    // Метод для ввода ключа (матрицы)
    public static int[][] inputKey() {
        Scanner scanner = new Scanner(System.in);
        int[][] keyMatrix = new int[MATRIX_SIZE][MATRIX_SIZE];

        System.out.println("Введите ключ (матрицу " + MATRIX_SIZE + "x" + MATRIX_SIZE + "):");
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                keyMatrix[i][j] = scanner.nextInt();
            }
        }

        return keyMatrix;
    }

    // Метод для шифрования текста
    public static String encrypt(String plaintext, int[][] keyMatrix) {
        String ciphertext = "";

        // Разбиваем текст на блоки по размеру матрицы
        for (int i = 0; i < plaintext.length(); i += MATRIX_SIZE) {
            // Преобразуем блок текста в вектор чисел (индексы символов)
            int[] plaintextBlock = new int[MATRIX_SIZE];
            for (int j = 0; j < MATRIX_SIZE; j++) {
                int index = (int) plaintext.charAt(i + j) - (int) 'A';
                plaintextBlock[j] = index;
            }

            // Умножаем вектор текста на ключ (матрицу)
            int[] ciphertextBlock = new int[MATRIX_SIZE];
            for (int j = 0; j < MATRIX_SIZE; j++) {
                for (int k = 0; k < MATRIX_SIZE; k++) {
                    ciphertextBlock[j] += keyMatrix[j][k] * plaintextBlock[k];
                }
                ciphertextBlock[j] %= 26; // Модуль 26, чтобы остаться в пределах алфавита
            }

            // Преобразуем вектор чисел (индексы символов) в блок зашифрованного текста
            for (int j = 0; j < MATRIX_SIZE; j++) {
                int index = ciphertextBlock[j] + (int) 'A';
                ciphertext += (char) index;
            }
        }

        return ciphertext;
    }
}

