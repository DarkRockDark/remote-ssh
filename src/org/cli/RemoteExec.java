package org.cli;

import static org.cli.util.GeneicUtil.console;
import static org.cli.util.GeneicUtil.printUsage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.cli.core.Engine;
import org.cli.helper.ArgParser;
import org.cli.vo.ConnectionDetails;

public class RemoteExec {
    /** The executor. */
    private static final ExecutorService executor = Executors
            .newFixedThreadPool(5);

    /**
     * The main method.
     * 
     * @param args
     *            the arguments
     */
    public static void main(String[] args) {
        RemoteExec exec = new RemoteExec();
        List<ConnectionDetails> details = parseInput(args);
        System.out.println("Processing..." + details);
        // I believe people provide commas for miltiple hosts
        for (ConnectionDetails detail : details) {
            final FutureTask<String> future = exec.getFutureTask(detail);
            executor.submit(future);
        }
        executor.shutdown();

    }

    /**
     * Parses the input.
     * 
     * @param args
     *            the args
     * @return the list
     */
    private static List<ConnectionDetails> parseInput(String[] args) {
        int i = 0;
        for (String a: args) {
            System.out.printf("Argument[%d]  %s\n", i++, a);
        }
        if (args.length == 0 || (args.length % 2) != 0) {
            printUsage();
        }
        
        System.out.println("Parsing input:");

        List<ConnectionDetails> details = new ArrayList<ConnectionDetails>();
        ArgParser parser = new ArgParser(args);
        String hostnames = parser.getValue("-h");
        String command = parser.getValue("-c");
        command = command.replace("^", " ");
        String username = parser.getValue("-u");
        String password = parser.getValue("-p");

        if (null == username) {
            username = console.readLine("User Name to connect to " + hostnames
                    + "? ");
        }

        if (null == password) {
            char[] passwordChar = console.readPassword("Password for "
                    + username + "? ");
            password = new String(passwordChar);
            // the javadoc for the Console class recommends "zeroing-out"
            // the
            // password
            // when finished verifying it :
            Arrays.fill(passwordChar, ' ');
        }

        String hosts[] = hostnames.split(",");
        for (String host : hosts) {
            ConnectionDetails detail = new ConnectionDetails();
            detail.setHostname(host);
            detail.setCommand(command);
            detail.setUsername(username);
            detail.setPassword(password);
            details.add(detail);
        }

        return details;
    }

    /**
     * Create a future task for the provided URL.
     * 
     * @param url
     *            to download concurrently
     * @return new Future task instance
     */
    public FutureTask<String> getFutureTask(final ConnectionDetails detail) {
        final FutureTask<String> future = new FutureTask<String>(new Engine(
                detail));
        return future;
    }

}
