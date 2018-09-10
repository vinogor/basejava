import model.Resume;
import storage.ArrayStorage;
import storage.SortedArrayStorage;
import storage.Storage;

/**
 * Test for your storage.ArrayStorage implementation
 */
public class MainTestArrayStorage {
     private final static Storage ARRAY_STORAGE = new SortedArrayStorage();
    // private final static ArrayStorage ARRAY_STORAGE = new ArrayStorage();

    public static void main(String[] args) {
        final Resume r1 = new Resume();
        r1.setUuid("uuid111");
        final Resume r2 = new Resume();
        r2.setUuid("uuid22");
        final Resume r3 = new Resume();
        r3.setUuid("uuid3");

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));
        printAll();

 //     System.out.println("Index of r2: " + Arrays.binarySearch(ARRAY_STORAGE.storage, 0 , ARRAY_STORAGE.size(), r2));


        // переделать тестирование UPDATE
//        r3.setUuid("uuid333");
//        ARRAY_STORAGE.update(r3);
//        printAll();

        ARRAY_STORAGE.delete(r1.getUuid());
        printAll();

        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());
    }

    private static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAll()) {
            System.out.println(r);
        }
    }
}
