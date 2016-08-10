import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

public class Main {

    public static void main(String[] args) {

        //        List<String> list = Splitter.on(",").splitToList("RU,BY,KZ");
        //        StringBuilder inPattern = new StringBuilder("'");
        //        inPattern.append(Joiner.on("','").join(list));
        //        inPattern.append("'");
        //        System.out.println(inPattern);
        //        System.out.println(IntStream.range(1, 10));
        //        System.out.println(Locale.getDefault());
        //        System.out.println(System.getProperty("user.language"));
        //        System.out.println(Boolean.parseBoolean("0"));
        //
        //        Map<String, String> states = new HashMap();
        //        states.put("test1", "q  q   q asasas");
        //        states.put("test2", "ewrerqew341234");
        //
        //        System.out.println(Boolean.parseBoolean("0"));

        //        System.out.println(-3 % 24);
        //
        //        System.out.println(LocalDateTime.now().plusHours(-3).getHour());
        //
        //        TimeZone aDefault = TimeZone.getDefault();
        //        System.out.println(aDefault);
        //        int currentTimezoneInMinutes =
        //                (int) TimeUnit.MILLISECONDS.toMinutes(aDefault.getOffset(System.currentTimeMillis()));
        //        System.out.println(currentTimezoneInMinutes);

        //        System.out.println(getLongestSubsequence("AAPPPPPPPPPAAAAAXXXCDDDEEEEBBBNNNE"));
        //        System.out.println(getLongestSubsequence("PPPPPPPPPAAAAAXXXCDDDEEEEBBBNNNE"));
        //        System.out.println(getLongestSubsequence("ZZZZZZZZAAAAAXXXCDDDEEEEBBBNNNEPPPPPPPPP"));
        //        System.out.println(getLongestSubsequence("PPPPPPPPP"));
        //        System.out.println(getLongestSubsequence("A"));
        //        System.out.println(test(true));
        //        System.out.println(test(false));
        Stack<String> stack = new Stack<String>();
        stack.push("A");
        System.out.println("1: " + stack);
        stack.push("B");
        System.out.println("2: " + stack);
        stack.push("C");
        System.out.println("3: " + stack);
        stack.pop();
        System.out.println("4: " + stack);
        stack.pop();
        System.out.println("5: " + stack);
        stack.pop();
        System.out.println("6: " + stack);
        stack.pop();

        //        System.out.println(test(false));

        List<Integer> unsorted = Lists.newArrayList();
        Collections.sort(unsorted);
    }

    public static String test(boolean value) {
        String text = "None";
        boolean flag = true;
        if (value == true) {
            text = "Yes";
            flag = false;
        } else {
            if (value == false) {
                text = "No";
                flag = false;
            }
        }
        if (flag) {
            text = "Maybe";
        }
        return text;
        //        return value ? "Yes" : "No";
    }

    // AAPPPPPPPPPAAAAAXXXCDDDEEEEBBBNNNE
    public static String getLongestSubsequence(String text) {
        char last = 0;

        int start = 0;
        int finish = 0;
        int s = 0;
        int cnt = 0;
        int max = 0;

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (last != c) {
                s = i;
                cnt = 0;
                last = c;
            } else {
                cnt++;
            }
            if (cnt > max) {
                max = cnt;
                start = s;
                finish = i;
            }
        }

        return text.substring(start, finish + 1);
    }

    public static class Stack<T> {

        private List<T> stack = new ArrayList<T>();

        public void push(T value) {
            stack.add(value);
        }

        public T pop() {
            if (stack.size() < 1) {
                throw new IndexOutOfBoundsException();
            }
            T o = stack.get(stack.size() - 1);
            stack.remove(stack.size() - 1);
            return o;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Stack{");
            sb.append("stack=").append(stack);
            sb.append('}');
            return sb.toString();
        }
    }


}
