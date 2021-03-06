package Array;

import java.util.HashMap;

/**
 * 169. Majority Element（求众数）
 * 给定一个大小为 n 的数组，找到其中的众数。众数是指在数组中出现次数大于 ⌊ n/2 ⌋ 的元素。
 * 你可以假设数组是非空的，并且给定的数组总是存在众数。
 */
public class Solution169 {
    public static void main(String[] args) {
        Solution169 solution169 = new Solution169();
        int[] nums = new int[]{3, 2, 3};
        System.out.println(solution169.majorityElement_4(nums));

    }

    /**
     * 利用哈希表的映射，储存数组中的数字以及它们出现的次数，当众数出现时，返回这个数字。
     *
     * @param nums
     * @return
     */
    public int majorityElement(int[] nums) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num :
                nums) {
            Integer cnt = map.get(num);
            if (cnt == null) {
                cnt = 1;
            } else {
                cnt++;
            }
            if (cnt > nums.length / 2) {
                return num;
            }
            map.put(num, cnt);
        }
        return 0;
    }

    /**
     * 因为众数是出现次数大于n/2的数字，所以排序之后中间的那个数字一定是众数。即nums[n/2]为众数。但是在计算比较大的数组时，时间会超过限制。
     *
     * @param nums
     * @return
     */
    public int majorityElement_2(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (nums[j] > nums[j + 1]) {
                    int temp = nums[j];
                    nums[j] = nums[j + 1];
                    nums[j + 1] = temp;
                }
            }
        }
        return nums[n / 2];
    }

    /**
     * 分治法，将整个数组分成两个部分，先分别筛选出这两部分中出现次数最多的数，记为left和right，如果left等于right，则返回left
     * 如果left不等于right，则left和right都是最终结果的候选，此时需要遍历整个数组考察left和right出现的次数，出现次数较多的就是最终返回的结果。
     *
     * @param nums
     * @return
     */
    public int majorityElement_3(int[] nums) {
        return find(nums, 0, nums.length - 1);
    }

    public int find(int[] nums, int begin, int end) {
        if (begin == end) {
            return nums[begin];
        } else {
            int mid = (begin + end) / 2;
            int left = find(nums, begin, mid);
            int right = find(nums, mid + 1, end);
            //左右两部分的众数相同 则这个数是这部分的众数
            if (left == right) {
                return left;
            } else {
                //左右两部分的众数不相同 则这两个数都有可能是这部分的众数,那么遍历这个数组，看一下哪个数字的出现次数多
                int countLeft = 0;
                int countRight = 0;
                for (int i = begin; i <= end; i++) {
                    if (nums[i] == left) {
                        countLeft++;
                    } else if (nums[i] == right) {
                        countRight++;
                    }
                }
                if (countLeft > countRight) {
                    return left;
                } else {
                    return right;
                }
            }
        }
    }

    /**
     * 摩尔投票算法,先将第一个数字假设为众数，然后把计数器设为1，比较下一个数和此数是否相等，若相等则计数器加一，反之减一。
     * 然后看此时计数器的值，若为零，则将当前值设为候选众数。以此类推直到遍历完整个数组，当前候选众数即为该数组的众数。
     *
     * @param nums
     * @return
     */
    public int majorityElement_4(int[] nums) {
        int maj = nums[0];
        int count = 1;
        for (int num : nums) {
            if (maj == num) {
                count++;
            } else {
                count--;
                if (count == 0) {
                    maj = num;
                    count = 1;
                }
            }
        }
        return maj;
    }

}
