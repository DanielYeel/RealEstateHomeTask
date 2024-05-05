public class User {
    private String username;
    private String password;
    private String phoneNumber;
    private boolean isBroker;

    // Constructor
    public User (String username, String password, String phoneNumber, boolean isBroker){
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.isBroker = isBroker;
    }

    //Method that prints user details
    public String toString (){
        String userType = this.isBroker ? "real estate broker" : "regular user";
        return this.username + " " + this.phoneNumber + " (" +userType + ")";
    }


    public String getUsername() {
        return this.username;
    }

    public boolean isBroker() {
        return this.isBroker;
    }

    //Checks if user exists
    public boolean checkCredential (String username, String password){
        boolean success = false;
        if (this.username.equals(username) && this.password.equals(password)) success=true;
        return success;
    }
}
