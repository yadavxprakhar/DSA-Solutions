class Solution {

    private int findDayOfYear(String day, int[] cumMonthDays) {
        int month = Integer.parseInt(day.substring(0, 2));
        int date  = Integer.parseInt(day.substring(3));
        return cumMonthDays[month - 1] + date;
    }

    public int countDaysTogether(String arriveAlice, String leaveAlice, String arriveBob, String leaveBob) {
        int[] monthDays = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        int[] cumMonthDays = new int[13];
        cumMonthDays[1] = 31; 
        for (int i = 1; i <= 12; i++) {
            cumMonthDays[i] = cumMonthDays[i - 1] + monthDays[i - 1];
        }

        int arriveAliceDay = findDayOfYear(arriveAlice, cumMonthDays);
        int leaveAliceDay  = findDayOfYear(leaveAlice, cumMonthDays);
        int arriveBobDay   = findDayOfYear(arriveBob, cumMonthDays);
        int leaveBobDay    = findDayOfYear(leaveBob, cumMonthDays);

        int overlapDays = Math.min(leaveAliceDay, leaveBobDay) - Math.max(arriveAliceDay, arriveBobDay) + 1;

        return Math.max(overlapDays, 0);
    }
}