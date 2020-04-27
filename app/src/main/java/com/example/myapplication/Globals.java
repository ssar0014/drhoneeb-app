package com.example.myapplication;

//This class is for creating global variables.
public class Globals{
    private static Globals instance;

    // Global variable
    private byte[] data;

    // Restrict the constructor from being instantiated
    private Globals(){}

    public void setData(byte[] d){
        this.data=d;
    }
    public byte[] getData(){
        return this.data;
    }

    public static synchronized Globals getInstance(){
        if(instance==null){
            instance=new Globals();
        }
        return instance;
    }
}
