package com.moorle.generator;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class ProjectDaoGenerator {
    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "com.moorle.bookkeeping.dao");
        addAccount(schema);
        new DaoGenerator().generateAll(schema, "./app/src/main/java");

    }

    private static void addAccount(Schema schema) {
        Entity room = schema.addEntity("Account");
        //房间id，自动生成
        room.addIdProperty().autoincrement();
        //房间名字
        room.addStringProperty("name");
    }
}
