import java.util.Scanner;

public class Driver {
    static StringBuilder data = new StringBuilder("0100"); //mode

    public static void main(String[] args) {
        QR code = new QR();
        Scanner in = new Scanner(System.in);
        System.out.print("Enter your link here: ");
        String link = in.nextLine();

        data.append(getBinarySize(link)); //character count
        encode(link); //encoding...
        code.encode(data.toString());


        code.printCode();
    }

    public static String getBinarySize(String input) {
        int size = input.length();
        String binaryString = Integer.toBinaryString(size);
        return String.format("%8s", binaryString).replace(' ', '0');
    }

    public static void encode(String link) {
        char[] chars = link.toCharArray();
        for (char c: chars) {
            String bs = charToBinary(c);
            data.append(bs);
        }
        data.append("0000");
        int repeatingPatternLength = 53 - chars.length;
        for (int i = 0; i < repeatingPatternLength; i++) {
            data.append("11101100");
        }
    }

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
