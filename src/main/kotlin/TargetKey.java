/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.time.LocalDateTime;

/**
 *
 * @author c.zampaglione
 */
public abstract class TargetKey {

    public static enum Type {

        A((short) 1),
        B((short) 2),
        C((short) 3),
        D((short) 4);

        private short id;

        private Type(short id) {
            this.id = id;
        }

        public short id() {
            return id;
        }
    }

    public abstract Type type();

}
