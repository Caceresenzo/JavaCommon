package caceresenzo.libs.bytes;


public class ByteUtils {
	
    /**
     * Search the data byte array for the first occurrence 
     * of the byte array pattern.
     * 
     * This algorithm is called " Knuth-Morris-Pratt Pattern Matching Algorithm(KPM)"
     * and this method is gotten from http://helpdesk.objects.com.au/java/search-a-byte-array-for-a-byte-sequence
     */
    public static int indexOf(byte[] data, byte[] pattern) {
        int[] failure = computeFailure(pattern);

        int j = 0;

        for (int i = 0; i < data.length; i++) {
            while (j > 0 && pattern[j] != data[i]) {
                j = failure[j - 1];
            }
            if (pattern[j] == data[i]) { 
                j++; 
            }
            if (j == pattern.length) {
                return i - pattern.length + 1;
            }
        }
        return -1;
    }

    /**
     * Computes the failure function using a boot-strapping process,
     * where the pattern is matched against itself.
     */
    private static int[] computeFailure(byte[] pattern) {
        int[] failure = new int[pattern.length];

        int j = 0;
        for (int i = 1; i < pattern.length; i++) {
            while (j>0 && pattern[j] != pattern[i]) {
                j = failure[j - 1];
            }
            if (pattern[j] == pattern[i]) {
                j++;
            }
            failure[i] = j;
        }

        return failure;
    }
    
}