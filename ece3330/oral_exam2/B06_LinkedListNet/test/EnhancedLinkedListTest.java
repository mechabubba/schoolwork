import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EnhancedLinkedListTest {
    @Test
    public void doFibonacci() {
        int[] vals = { 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597, 2584, 4181, 6765 };
        EnhancedLinkedList<Integer> list = new EnhancedLinkedList<>();
        for(int i = 0; i < 20; i++) {
            if(list.size() == 0 || list.size() == 1) {
                list.insertAtBack(1);
                continue;
            }
            int one = list.get(i - 2);
            int two = list.get(i - 1);
            list.insertAtBack(one + two);
        }

        for(int i = 0; i < list.size(); i++) {
            assertEquals(vals[i], list.get(i));
        }
    }

    @Test
    public void emptyListFromBack() {
        EnhancedLinkedList<Integer> list = new EnhancedLinkedList<>();
        for(int i = 0; i < 100; i++) {
            list.insertAtBack(i);
        }

        int size = list.size();
        for(int i = 0; i < size; i++) {
            list.removeFromBack();
        }

        assertEquals(0, list.size());
    }

    @Test
    public void emptyListFromFront() {
        EnhancedLinkedList<Integer> list = new EnhancedLinkedList<>();
        for(int i = 0; i < 100; i++) {
            list.insertAtFront(i);
        }

        int size = list.size();
        for(int i = 0; i < size; i++) {
            list.removeFromFront();
        }

        assertEquals(0, list.size());
    }
}
