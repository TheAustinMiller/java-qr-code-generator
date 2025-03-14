import java.util.Scanner;

/**
 * Creates QR Code
 * Version 3
 * Error Correction L
 * Byte Mode
 * Mask 0
 *
 * @author - Austin Miller
 * 03/13/2025
 */
public class Driver {
    static StringBuilder data = new StringBuilder("0100"); //mode

    public static void main(String[] args) {
        QR code = new QR();

        //INPUT
        Scanner in = new Scanner(System.in);
        System.out.print("Enter your link here: ");
        String link = in.nextLine();
        while (link.length() > 53 || link.length() == 0) {
            System.out.print("Link is invalid! Please provide a link up to 53 characters: ");
            link = in.nextLine();
        }

        //CALCULATION
        data.append(getBinarySize(link));
        encode(link); // encode data bits
        String allBits = code.getCodeBits(data.toString()); //encode code bits
        code.addBits(allBits);  //add all bits to the page
        code.mask(); //mask them

        //OUTPUT
        code.printCode();
    }

    /**
     * Returns the size of the link in binary
     * @param input - The link
     * @return - The size of the input converted to binary
     */
    public static String getBinarySize(String input) {
        int size = input.length();
        String binaryString = Integer.toBinaryString(size);
        return String.format("%8s", binaryString).replace(' ', '0');
    }

    /**
     * @param link - Converts the link to binary; adds padding to the data bits
     */
    public static void encode(String link) {

        char[] chars = link.toCharArray();
        for (char c: chars) {
            String bs = charToBinary(c);
            data.append(bs);
        }
        data.append("0000");
        int repeatingPatternLength = 53 - chars.length;
        for (int i = 0; i < repeatingPatternLength; i++) {
            if (i % 2 == 0) {
                data.append("11101100");
            } else {
                data.append("00010001");
            }
        }
    }

    /**
     * @param character - Takes a single character from the link
     * @return - The binary representation of the character
     */
    public static String charToBinary(char character) {
        int asciiValue = (int) character;
        StringBuilder binary = new StringBuilder();

        for (int i = 7; i >= 0; i--) {
            int bit = (asciiValue >> i) & 1;
            binary.append(bit);
        }
        return binary.toString();
    }

}
