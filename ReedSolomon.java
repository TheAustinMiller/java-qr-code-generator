class ReedSolomon {
    private final int[] generator;

    public ReedSolomon(int errorCodewords) {
        generator = generatePolynomial(errorCodewords);
    }

    private int[] generatePolynomial(int errorCodewords) {
        int[] poly = {1};
        for (int i = 0; i < errorCodewords; i++) {
            poly = multiply(poly, new int[]{1, gfExp(i)});
        }
        return poly;
    }

    private int[] multiply(int[] poly1, int[] poly2) {
        int[] result = new int[poly1.length + poly2.length - 1];
        for (int i = 0; i < poly1.length; i++) {
            for (int j = 0; j < poly2.length; j++) {
                result[i + j] ^= gfMultiply(poly1[i], poly2[j]);
            }
        }
        return result;
    }

    private int gfExp(int x) {
        return (int) Math.pow(2, x) % 0x11D;
    }

    private int gfMultiply(int a, int b) {
        return (a * b) % 0x11D;
    }

    public int[] encode(int[] data, int errorCodewords) {
        int[] paddedData = new int[data.length + errorCodewords];
        System.arraycopy(data, 0, paddedData, 0, data.length);

        for (int i = 0; i < data.length; i++) {
            int factor = paddedData[i];
            if (factor == 0) continue;

            for (int j = 0; j < generator.length; j++) {
                paddedData[i + j] ^= gfMultiply(generator[j], factor);
            }
        }

        int[] errorCorrection = new int[errorCodewords];
        System.arraycopy(paddedData, data.length, errorCorrection, 0, errorCodewords);
        return errorCorrection;
    }
}
