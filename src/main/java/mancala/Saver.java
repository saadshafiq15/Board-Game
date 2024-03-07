package mancala;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * The Saver is a Java class that is used for saving data.
 */
public class Saver{

    /**
     * The function saves a serializable object to a file using Java's ObjectOutputStream.
     * 
     * @param toSave The object that you want to save. It should implement the Serializable interface.
     * @param filename The filename parameter is a String that represents the name of the file where
     * the object will be saved.
     */
    public void saveObject(final Serializable toSave, final String filename){

        try (ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream("assets/" + filename))){
            objectOut.writeObject(toSave);
        } catch (IOException e){
            e.getMessage();
        }
    }

    /**
     * The function loads a serialized object from a file and returns it as a Serializable object.
     * 
     * @param filename The filename parameter is a String that represents the name of the file that you
     * want to load the object from.
     * @return The method is returning a Serializable object.
     */
    public Serializable loadObject(final String filename){
        Object loadedObject = null;
        
        try (ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream("assets/" + filename))){
            loadedObject = objectIn.readObject();
        } catch (IOException | ClassNotFoundException e){
            e.getMessage();
        }

        return (Serializable) loadedObject;
    }

}
