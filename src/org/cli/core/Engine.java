package org.cli.core;

import static org.cli.util.GeneicUtil.pressENTER;
import static org.cli.util.GeneicUtil.printUsage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

import org.cli.vo.ConnectionDetails;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

/**
 * The Class Engine.
 */
public class Engine implements Callable<String> {

    /** The details. */
    ConnectionDetails details = null;

    /**
     * Instantiates a new engine.
     * 
     * @param details
     *            the details
     */
    public Engine(ConnectionDetails details) {
        this.details = details;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.concurrent.Callable#call()
     */
    @Override
    public String call() throws Exception {
        connect(details.getHostname(), details.getUsername(),
                details.getPassword(), details.getCommand());
        return "SUCCESS";
    }

    /**
     * Connect.
     * 
     * @param hostname
     *            the hostname
     * @param username
     *            the username
     * @param password
     *            the password
     * @param command
     *            the command
     */
    private void connect(String hostname, String username, String password,
            String command) {
        try {

            // Create a connection instance

            Connection conn = new Connection(hostname);

            // Now connect

            conn.connect();

            // Authenticate

            boolean isAuthenticated = conn.authenticateWithPassword(username,
                    password);

            if (isAuthenticated == false)
                throw new IOException("Authentication failed.");

            // Create a session

            Session sess = conn.openSession();

            sess.execCommand(command);

            InputStream stdout = new StreamGobbler(sess.getStdout());
            InputStream stderr = new StreamGobbler(sess.getStderr());

            BufferedReader stdoutReader = new BufferedReader(
                    new InputStreamReader(stdout));
            BufferedReader stderrReader = new BufferedReader(
                    new InputStreamReader(stderr));

            System.out.println("Here is the output from stdout:");

            while (true) {
                String line = stdoutReader.readLine();
                if (line == null)
                    break;
                System.out.println(line);
            }

            System.out
                    .println("Here is the output from stderr: Press CTRL+C to break");

            while (true) {
                String line = stderrReader.readLine();
                if (line == null)
                    break;
                System.out.println(line);
            }

            // Close this session

            sess.close();

            // Close the connection

            conn.close();
            pressENTER();

        } catch (Exception e) {
            e.printStackTrace(System.out);
            printUsage();
        }

    }
}
