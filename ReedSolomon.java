class ReedSolomon {
    private static final int GF_SIZE = 256;
    private static final int GF_PRIMITIVE_POLY = 0x11D;
    private final int[] generator;
    private static final int[] gfExp = new int[GF_SIZE * 2];
    private static final int[] gfLog = new int[GF_SIZE];

    static {
        // Generate Galois Field tables
        int x = 1;
        for (int i = 0; i < GF_SIZE - 1; i++) {
            gfExp[i] = x;
            gfLog[x] = i;
            x = (x << 1) ^ (x >= 0x80 ? GF_PRIMITIVE_POLY : 0);
        }
        for (int i = GF_SIZE - 1; i < gfExp.length; i++) {
            gfExp[i] = gfExp[i - (GF_SIZE - 1)];
        }
    }

    public ReedSolomon(int errorCodewords) {
        generator = generatePolynomial(errorCodewords);
    }

    private int[] generatePolynomial(int errorCodewords) {
        int[] poly = {1};
        for (int i = 0; i < errorCodewords; i++) {
            poly = multiply(poly, new int[]{1, gfExp[i]});
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

    private int gfMultiply(int a, int b) {
        if (a == 0 || b == 0) return 0;
        return gfExp[gfLog[a] + gfLog[b]];
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
