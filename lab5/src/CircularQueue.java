import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CircularQueue<T> implements Iterable<T> {

    private final T[] queue;
    private final Lock[] locks;
    private int head, tail, size;

    @SuppressWarnings("unchecked")
    public CircularQueue() {
        short arraySize = 20;
        queue = (T[]) new Object[arraySize];
        locks = new Lock[arraySize];
        Arrays.fill(locks, new ReentrantLock());
        head = 0;
        tail = 0;
        size = 0;
    }

    @SuppressWarnings("unchecked")
    public CircularQueue(int n) { //assume n >=0
        queue = (T[]) new Object[n];
        locks = new Lock[n];
        Arrays.fill(locks, new ReentrantLock());
        size = 0;
        head = 0;
        tail = 0;
    }

    public boolean join(T x) {
        if (size < queue.length) {
            try {
                locks[tail].lock();
                queue[tail] = x;
                tail = (tail + 1) % queue.length;
                size++;
                return true;
            } finally {
                locks[tail].unlock();
            }
        } else return false;
    }

    public T top() {
        if (size > 0)
            try {
                locks[head].lock();
                return queue[head];
            } finally {
                locks[head].unlock();
            }
        else
            return null;
    }

    public boolean leave() {
        if (size == 0) return false;
        else {
            try {
                locks[head].lock();
                head = (head + 1) % queue.length;
                size--;
                return true;
            } finally {
                locks[head].unlock();
            }
        }
    }

    public boolean full() {
        try {
            for (Lock lock : locks) lock.lock();
            return (size == queue.length);
        } finally {
            for (Lock lock : locks) lock.unlock();
        }
    }

    public boolean empty() {
        try {
            for (Lock lock : locks) lock.lock();
            return (size == 0);
        } finally {
            for (Lock lock : locks) lock.unlock();
        }
    }

    public Iterator<T> iterator() {
        return new QIterator<>(queue, head, size);
    }

    private static class QIterator<T> implements Iterator<T> {
        private final T[] d;
        private int index;
        private final int size;
        private int returned = 0;
        private final Lock[] locks;

        QIterator(T[] dd, int head, int s) {
            d = dd;
            index = head;
            size = s;
            locks = new Lock[dd.length];
            Arrays.fill(locks, new ReentrantLock());
        }

        public boolean hasNext() {
            try {
                for (Lock lock : locks) lock.lock();
                return returned < size;
            } finally {
                for (Lock lock : locks) lock.unlock();
            }
        }

        public T next() {
            try {
                for (Lock lock : locks) lock.lock();
                if (returned == size) throw new NoSuchElementException();
                T item = d[index];
                index = (index + 1) % d.length;
                returned++;
                return item;
            } finally {
                for (Lock lock : locks) lock.unlock();
            }
        }

        public void remove() {
        }
    }

}