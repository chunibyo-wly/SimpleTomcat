package server.session;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chunibyo
 * @createTime 2019-06-20 08:34
 */
public class session {
    public session() {

    }

    private static Map<String, Object> attribute = new HashMap<>();

    public void setAttribute(String name, Object o) {
        attribute.put(name, o);
    }

    public Object getAttribute(String name) {
        return attribute.getOrDefault(name, "Not Login Yet");
    }
}
