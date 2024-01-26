public class SegmentTree {
    int length;
    int[] segmentTree;
    int[] nums;
    int n;

    public SegmentTree(int[] nums) {
        this.nums = nums;
        n = nums.length;
        if((n != 1) && ((n & (n - 1)) == 0)){
            length = n * 2 - 1;
        }else{
            int power = 1;
            while(power < n){
                power *= 2;
            }
            length = power * 2 - 1;
        }
        segmentTree = new int[length];
        buildTree(0, n - 1, 0);
    }
    public void buildTree(int low, int high, int position){
        if(low == high){
            segmentTree[position] = nums[low];
            return;
        }
        int mid = low + (high - low) / 2;

        buildTree(low, mid, 2 * position + 1);
        buildTree(mid + 1, high, 2 * position + 2);
        segmentTree[position] = segmentTree[2 * position + 1] + segmentTree[2 * position + 2];
    }

    public void update(int index, int val) {
        updateTree(0, n - 1, 0, index, val);
    }
    public void updateTree(int low, int high, int position, int index, int val) {
        // If the index is outside the range of this segment
        if (index < low || index > high) {
            return;
        }

        // If the current segment is a leaf node
        if (low == high) {
            nums[index] = val;
            segmentTree[position] = val;
            return;
        }

        // If the index is within the range of this segment, update both left and right subtrees
        int mid = low + (high - low) / 2;
        updateTree(low, mid, 2 * position + 1, index, val);
        updateTree(mid + 1, high, 2 * position + 2, index, val);

        // Update the current segment value after updating the subtrees
        segmentTree[position] = segmentTree[2 * position + 1] + segmentTree[2 * position + 2];
    }

    public int sumRange(int left, int right) {

        return rangeSumQuery(0, n - 1, 0, left, right);
    }
    public int rangeSumQuery(int low, int high, int position, int queryLow, int queryHigh) {
        // If the current segment is completely outside the query range
        if (low > queryHigh || high < queryLow) {
            return 0;
        }
        // If the current segment is completely inside the query range
        if (low >= queryLow && high <= queryHigh) {
            return segmentTree[position];
        }

        // If the current segment partially overlaps with the query range
        int mid = low + (high - low) / 2;
        int leftSum = rangeSumQuery(low, mid, 2 * position + 1, queryLow, queryHigh);
        int rightSum = rangeSumQuery(mid + 1, high, 2 * position + 2, queryLow, queryHigh);

        return leftSum + rightSum;
    }
}

