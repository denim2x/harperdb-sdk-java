package io.harperdb.base;

import static io.harperdb.util.Preconditions.checkNotNull;

/**
 *
 * @author denim2x <denim2x@cyberdude.com>
 */
public enum Stage {
    CONNECT(0x01), REQUEST(0x02);

    private static final int[] flags;
    private static final Stage[] values = values();
    static {
        flags = new int[values.length];
        for (var i = 0; i < flags.length; i++) {
            flags[i] = values[i].flag;
        }
    }

    protected int flag;

    Stage(int flag) {
        this.flag = flag;
    }

    public int flag() {
        return flag;
    }

    public boolean check(int flags) {
        return 0 < (flags & flag);
    }

}