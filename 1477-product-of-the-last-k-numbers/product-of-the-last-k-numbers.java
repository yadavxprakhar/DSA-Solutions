class ProductOfNumbers {
    private List<Integer> nums; 
    private int n;

    public ProductOfNumbers() {
        nums = new ArrayList<>();
        n = 0;
    }
    
    public void add(int num) {
        if(num == 0) {
            nums.clear();
            n = 0;
        } else {
            if(nums.isEmpty()) {
                nums.add(num);
            } else {
                nums.add(nums.get(n-1) * num);
            }
            n++; 
        }
    }
    
    public int getProduct(int k) {
        if(k > n) {
            return 0;
        } else if(k == n) {
            return nums.get(n-1);
        }

        return nums.get(n-1) / nums.get(n-k-1);
    }
}