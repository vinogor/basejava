package storage;

import model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume r) {
        // сохранять так, чтобы уже сразу было отсортировано по ВОЗРАСТАНИЮ используя бинарный поиск.
        int index = findResumeNumber(r.getUuid());
        if (pointerToFirstNull == STORAGE_LIMIT) {
            System.out.println("ERROR: хранилище резюме полностью заполнено");
        } else {
            if (index > -1) {
                System.out.println("ERROR: такое резюме УЖЕ существует");
            } else {
                if (index == -1) {
                    // добавляем новое резюме в самое начало
                    System.arraycopy(storage, 0, storage, 1, pointerToFirstNull);
                    storage[0] = r;
                } else {
                    // добавлем новое резюме в самый конец
                    storage[pointerToFirstNull] = r;
                }
                pointerToFirstNull++;
            }
        }
    }

    @Override
    public void delete(String uuid) {
        // удалять так, чтобы сортировка не сбивалась, сдвигать с конца
        int index = findResumeNumber(uuid);
        if (index < 0) {
            System.out.println("ERROR: такого резюме НЕ существует");
        } else {
            System.arraycopy(storage, index + 1, storage, index, pointerToFirstNull - index - 1);
            storage[pointerToFirstNull - 1] = null;
            pointerToFirstNull--;
        }
    }

    @Override
    protected int findResumeNumber(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, pointerToFirstNull, searchKey);
        // массив должен быть отсортирован по ВОЗРАСТАНИЮ перед запуском поиска. Реализуем сортировку заранее при сохранении.
        // возвращает -1 если меньше минимального и (-2 и менее) если больше максимального
    }
}
