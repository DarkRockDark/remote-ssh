package org.cli.util;

import java.io.Console;

/**
 * The Class GeneicUtil.
 */
public class GeneicUtil {

    /** The console. */
    public static Console console = System.console();

    /**
     * Press enter.
     */
    public static void pressENTER() {
        System.out.println("Press ENTER key to continue...");
        console.readLine();
    }

    /**
     * Prints the usage.
     */
    public static void printUsage() {
        System.out.println("java org.cli.RemoteExec OPTIONS");
        System.out.println("OPTIONS:");
        System.out.println("REQUIRED");
        System.out.println("-h <hostname> - Supply the hostnames to SSH Eg: `wwww.hostname.com[,www.anotherhostname.com,...]`");
        System.out.println("-c <command>  - The command to be executed `^` to escape space in command.");
        System.out.println("OPTIONAL: It will be prompted");
        System.out.println("-u <username> - The username to SSH");
        System.out.println("-p <password> - The password for currently login");
        pressENTER();
        System.exit(1);
    }

}
