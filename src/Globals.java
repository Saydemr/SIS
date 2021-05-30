/*
    END OF SERVICE
*/ 

class Globals {
    public static String OsInfo = "";
    private static String username = "";
    private static String password = "";
    static boolean doubleLogin = false;

    static String getUsername() {
        return username;
    }

    static void setUsername(String username) {
        Globals.username = username;
    }

    static String getPassword() {
        return password;
    }

    static void setPassword(String password) {
        Globals.password = password;
    }

}
