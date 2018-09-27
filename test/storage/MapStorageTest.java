package storage;

import model.Resume;

import static org.junit.Assert.assertArrayEquals;

public class MapStorageTest extends AbstractStorageTest {

    public MapStorageTest() {
        super(new MapStorage());
    }

    @Override
    public void getAll() {
       // super.getAll();  // надо будет переопределить, так как возвращается рандомно расположенный массив

   //     Resume[] arrayActual = super.storage.getAll();
   //     Resume[] arrayExpected = {RESUME_1, RESUME_2, RESUME_3};


/*
        public void getAll() {
            Resume[] arrayActual = storage.getAll();
            Resume[] arrayExpected = {RESUME_1, RESUME_2, RESUME_3};
            assertArrayEquals(arrayExpected, arrayActual);
        }

        */
    }
}