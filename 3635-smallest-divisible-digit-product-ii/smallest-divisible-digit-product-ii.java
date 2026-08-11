class Solution {
    public String smallestNumber(String num, long t) {
        long remFactor = t;

        for(int factor =2; factor<=9; factor++){
            while(remFactor % factor == 0){
              remFactor /= factor;
            }
        }
        if(remFactor > 1){
            return "-1";
        }
        int len = num.length();

        long[] reqFactor = new long[len+1];
        reqFactor[0] = t;

        int firstZeroIdx = len-1;
        char[] digits = num.toCharArray();

        for(int i=0; i<len; i++){
            if(digits[i] == '0'){
                firstZeroIdx = i;
                break;
            }
            reqFactor[i + 1] =reqFactor[i] / gcd(reqFactor[i], digits[i] - '0');
        }
        if(reqFactor[len] == 1){
            return num;
        }

        for(int i=firstZeroIdx; i>=0; i--){
            while(++digits[i] <= '9'){
                long curNeed = reqFactor[i] /gcd(reqFactor[i], digits[i] - '0');
                int canDigit = 9;
                for(int j=len-1; j>i; j--){
                    while(curNeed % canDigit != 0){
                        canDigit--;
                    }
                    curNeed /= canDigit;
                    digits[j] = (char)('0'+ canDigit);
                }
                if(curNeed == 1){
                    return new String(digits);
                }
            }
        }
        StringBuilder answer = new StringBuilder();

        long remaining = t;
        for (int digit = 9; digit >= 2; digit--) {
            while (remaining % digit == 0) {
                answer.append((char) ('0' + digit));
                remaining /= digit;
            }
        }
        int extraOnes = Math.max(len + 1 - answer.length(), 0);
        while (extraOnes-- > 0) {
            answer.append('1');
        }
        return answer.reverse().toString();
    }
    private long gcd(long first, long second) {

        while (second != 0) {
            long temp = second;
            second = first % second;
            first = temp;
        }

        return first;
    }
}