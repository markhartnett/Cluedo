import java.util.Random;
import java.util.Iterator;

/*
 * 16310943 James Byrne
 * 16314763 Jakub Gajewski
 * 16305706 Mark Hartnett
 *
 * Implementation of a circular linked list used to store human players in the game
 */
public class CircularlyLinkedList<E> implements Iterable<E> {
    private Node<E> head;
    private Node<E> tail;
    private int size;

    //constructs a circularly linked list
    public CircularlyLinkedList(){
        head = null;
        tail = null;
        size = 0;
    }

    //function returns the size of the circularly linked list
    public int getSize() {
        return size;
    }

    //function returns true if the circularly linked list is empty
    public boolean isEmpty(){
        return size == 0;
    }

    //returns the first player in the circularly linked list
    public E getFirst(){
        if (isEmpty()){
            return null;
        }
        return head.getElement();
    }

    //returns the last player in the circularly linked list
    public E getLast(){
        if (isEmpty())
            return null;
        return tail.getElement();
    }

    //add a player to the start of the list
    public void addFirst(Player p){
        Node n = new Node(p, null);
        n.setNext(head);
        if (head == null){
            head = n;
            n.setNext(head);
            tail = head;
        }
        else{
            tail.setNext(n);
            head = n;
        }
        size++;
    }

    //add player to tail of list
    public void addLast(Player p){
        Node n = new Node(p, null);
        n.setNext(head);
        if (head == null){
            head = n;
            n.setNext(head);
            tail = head;
        }
        else{
            tail.setNext(n);
            tail = n;
        }
        size++;
    }

    /** function for removal of nodes*/
    public void remove(Player p) {
        Node<E> temp = head;
        Node<E> prev = tail;
        Node<E> prevOfPrev = new Node<>(null,null);

        if(this.isEmpty()){
            throw new Error("Circularly linked List is already empty");
        }
        else {
            while (true) {
                if (temp.element.equals(p)) {
                    prev.setNext(temp.getNext());
                    temp.setNext(null);
                    p=null;
                    if (temp.equals(head)) {
                        head = prev.next;
                        size--;
                        return;
                    }

                    else if (temp.equals(tail)) {
                        tail = prevOfPrev;
                        size--;
                        return;
                    }

                    else {
                        size--;
                        return;
                    }
                }
                prevOfPrev = prev;
                prev=temp;
                temp = temp.getNext();
            }
        }
    }

    @Override
    public Iterator<E> iterator(){
        return new ListIterator();
    }

    private static class Node<E>{
        private E element;  //element of the position in the linked list
        private Node<E> next;      //reference to next node in the list

        //creates a given element before a certain node
        public Node(E e, Node<E> n){
            element = e;
            next = n;
        }

        public E getElement(){
            return element;
        }

        public Node<E> getNext() {
            return next;
        }

        public void setNext(Node<E> n) {
            next = n;
        }
    }

    private class ListIterator implements Iterator<E>{
        Node<E> current;
        int nextNode;

        public ListIterator(){
            current = head;
            nextNode = 0;
        }

        public boolean hasNext(){
            return nextNode < size;
        }

        public E next(){
            E res = current.getElement();
            current = current.getNext();
            nextNode++;
            return res;
        }
    }

}
