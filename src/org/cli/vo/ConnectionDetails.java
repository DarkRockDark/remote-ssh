/**
 * 
 */
package org.cli.vo;

/**
 * @author vvenkatraman
 * 
 */
public class ConnectionDetails {

    String hostname = null;
    String username = null;
    String password = null;
    String command = null;

    /**
     * @return the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * @return the hostname
     */
    public String getHostname() {
        return hostname;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param command
     *            the command to set
     */
    public void setCommand(String command) {
        this.command = command;
    }

    /**
     * @param hostname
     *            the hostname to set
     */
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @param username
     *            the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ConnectionDetails [");
        if (hostname != null) {
            builder.append("hostname=");
            builder.append(hostname);
            builder.append(", ");
        }
        if (username != null) {
            builder.append("username=");
            builder.append(username);
            builder.append(", ");
        }
        if (password != null) {
            builder.append("password=");
            builder.append(password);
            builder.append(", ");
        }
        if (command != null) {
            builder.append("command=");
            builder.append(command);
        }
        builder.append("]");
        return builder.toString();
    }
}
