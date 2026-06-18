class Solution {
    public double angleClock(int hour, int minutes) {
        double hourdegree = (hour % 12) * 30 + (0.5) * minutes;
        double mindegree = minutes * 6;
        double diff = Math.abs(hourdegree - mindegree);
        return Math.min(diff, 360 - diff);
    }
}