import java.io.*;

public class   Serializer {

    private File objectDirectory;

    public Serializer() {
        this("objects");
    }

    public Serializer(String path) {
        objectDirectory = new File(path);
        if (!objectDirectory.exists() || !objectDirectory.isDirectory()) {
            if (!objectDirectory.mkdir())
                throw new RuntimeException("Cannot create \"" + path + "\" directory");
        }
    }

    public void serialize(Serializable object, String name) {
        try {
            FileOutputStream fileOut = new FileOutputStream(objectDirectory.toString() + File.separator + name + ".ser");
            ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
            objOut.writeObject(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object deserialize(String name) {
        if (!objectDirectory.exists() || !objectDirectory.isDirectory())
            throw new RuntimeException("Directory \"" + objectDirectory.toString() + "\" does not exists");
        File serializedObj = new File(objectDirectory.toString() + File.separator + name + ".ser");
        Object deserializedObj = null;
        if (!serializedObj.exists())
            throw new RuntimeException("File \"" + serializedObj.toString() + "\" does not exists");
        try {
            FileInputStream fileIn = new FileInputStream(serializedObj);
            ObjectInputStream objIn = new ObjectInputStream(fileIn);
            deserializedObj = objIn.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deserializedObj;
    }

    public void setObjectDirectory(String path) {
        objectDirectory = new File(path);
    }

    public String getObjectDirectory() {
        return objectDirectory.toString();
    }

}

