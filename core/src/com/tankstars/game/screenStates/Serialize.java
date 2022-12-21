package com.tankstars.game.screenStates;

import com.tankstars.game.DummyTank;

import java.io.*;

public class Serialize {

    public static void load(DummyTank obj) throws IOException {
        File f = new File("TankData.txt");
        FileOutputStream fos = new FileOutputStream(f);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(obj);
    }

    public static DummyTank save() throws IOException, ClassNotFoundException {
        File f = new File("TankData.txt");
        FileInputStream fis = new FileInputStream(f);
        ObjectInputStream ois = new ObjectInputStream(fis);
        DummyTank obj1 = (DummyTank) ois.readObject();
        return obj1;
    }
}