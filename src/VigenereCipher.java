public class VigenereCipher {
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String encrypt(String text, String key) {
        StringBuilder encryptedText = new StringBuilder();
        int keyIndex = 0;

        for (int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);

            if (Character.isLetter(currentChar)) {
                boolean isUpperCase = Character.isUpperCase(currentChar);
                char shiftedChar = ALPHABET.charAt((ALPHABET.indexOf(Character.toUpperCase(currentChar)) +
                        ALPHABET.indexOf(Character.toUpperCase(key.charAt(keyIndex)))) % ALPHABET.length());

                encryptedText.append(isUpperCase ? shiftedChar : Character.toLowerCase(shiftedChar));

                keyIndex = (keyIndex + 1) % key.length();
            } else {
                encryptedText.append(currentChar);
            }
        }

        return encryptedText.toString();
    }

    public static void main(String[] args) {
        String name = "YOUR_NAME";
        String fullName = "YOUR_SURNAME";
        String encryptedFullName = encrypt(fullName, name);

        System.out.println("Зашифрованное ФИО: " + encryptedFullName);
    }
}
