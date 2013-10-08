package org.cli;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

import org.cli.helper.ArgParser;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

public class RemoteExec {

   static String hostname = null;
   static String username = null;
   static String password = null;
   static String command = null;

   static Console console = System.console();

   /**
    * The main method.
    * 
    * @param args
    *           the arguments
    */
   public static void main(String[] args) {

      if (args.length == 0 || (args.length % 2) != 0) {
         printUsage();
      }
      try {
         ArgParser parser = new ArgParser(args);
         hostname = parser.getValue("-h");// "cbolw01btst.dev.cbeyond.net";
         command = parser.getValue("-c");

         username = parser.getValue("-u");
         password = parser.getValue("-p");

         if (null == username) {
            username = console.readLine("User Name to connect to " + hostname
                  + "? ");
         }

         if (null == password) {
            char[] passwordChar = console.readPassword("Password for "
                  + username + "? ");
            password = new String(passwordChar);
            // the javadoc for the Console class recommends "zeroing-out" the
            // password
            // when finished verifying it :
            Arrays.fill(passwordChar, ' ');
         }
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

         System.out.println("Here is the output from stderr: Press CTRL+C to break");

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

      }
      catch (IOException e) {
         e.printStackTrace(System.err);
         printUsage();
      }

   }

   private static void printUsage() {
      System.out.println("java org.cli.RemoteExec OPTIONS");
      System.out.println("OPTIONS:");
      System.out.println("REQUIRED");
      System.out.println("-h <hostname> - Supply the hostname to SSH");
      System.out.println("-c <command>  - The command to be executed");
      System.out.println("OPTIONAL: It will be prompted");
      System.out.println("-u <username> - The username to SSH");
      System.out.println("-p <password> - The password for currently login");
      pressENTER();
      System.exit(1);
   }

   private static void pressENTER() {
      System.out.println("Press ENTER key to continue...");
      console.readLine();
   }

}
