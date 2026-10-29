package com.rest.models;

public class Tables {

    public enum TableStatus{
        FREE,OCCUPIED
    }

    private int id;
    private int capacity;
    private TableStatus status;

    public Tables(int id,int capacity){
        this.id = id;
        this.capacity = capacity;
        this.status = TableStatus.FREE;
    }
    
    public int getId() {
        return id;
    }
    
    public int getCapacity() {
        return capacity;
    }

    public TableStatus getStatus() {
        return status;
    }

    public void freeTable(){
        this.status = TableStatus.FREE;
    }    

    public void occupyTable(){
        this.status = TableStatus.OCCUPIED;
    }

    public String toString() {
        return "Table ID: " + id + ", Capacity: " + capacity + ", Status: " + status;
    }
    


    
}
