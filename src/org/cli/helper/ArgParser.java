package org.cli.helper;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Venkateswara VP
 * 
 */
public class ArgParser {

    private Map<String, String> map = new HashMap<String, String>();
    private String[] args;

    /**
     * Instantiates a new arg parser.
     * 
     * @param args
     *            the args
     */
    public ArgParser(String[] args) {
        this.args = args;
        _init();
    }

    private void _init() {
        for (int position = 0; position < args.length; position += 2) {
            map.put(args[position], args[1 + position]);
        }
        System.out.println("MAP:" + map);
    }

    /**
     * Gets the value.
     * 
     * @param key
     *            the key
     * @return the value
     */
    public String getValue(String key) {
        return getValue(key, null);
    }

    /**
     * Gets the value.
     * 
     * @param key
     *            the key
     * @param def
     *            the default value
     * @return the value
     */
    public String getValue(String key, String def) {
        String value = map.get(key);
        if (null == value) {
            value = def;
        }
        return value;
    }
}
