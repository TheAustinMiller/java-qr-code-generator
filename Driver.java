import java.util.Scanner;

public class Driver {
    static StringBuilder data = new StringBuilder("0100"); //mode

    public static void main(String[] args) {
        QR code = new QR();
        System.out.println(code.getWorkingBits());
        Scanner in = new Scanner(System.in);
        System.out.print("Enter your link here: ");
        String link = in.nextLine();
        data.append(getBinarySize(link));
        encode(link);
        System.out.println(data.toString());
        System.out.println(data.toString().length());
        String allBits = code.getCodeBits(data.toString());
        System.out.println(allBits);
        System.out.println(allBits.length());
        code.addBits(allBits); //adding data bits to qr code
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
            if (i % 2 == 0) {
                data.append("11101100");
            } else {
                data.append("00010001");
            }
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
