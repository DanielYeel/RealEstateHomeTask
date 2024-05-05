import java.util.Scanner;

public class RealEstate {

    public static final int CHOICE_SIGN_UP = 1;
    public static final int CHOICE_LOGIN = 2;
    public static final int FINISH_PROGRAM = 3;

    public static final int PUBLISH_NEW_PROPERTY = 1;
    public static final int REMOVE_PROPERTY = 2;
    public static final int DISPLAY_ALL_PROPERTIES = 3;
    public static final int LIST_MY_PROPERTIES = 4;
    public static final int FILTER_PROPERTIES = 5;
    public static final int LOGOUT = 6;

    private User[] users;
    private Property[] properties;
    private City[] cities;

    // Prints main menu
    private void printMenu(){
        System.out.println(""" 
                    \nEnter your choice:
                    1 - create an account
                    2 - connect to an existing account
                    3 - finish the program
                    """);
    }

    // Prints Inner menu
    private void printInnerMenu(){
        System.out.println("""
                \nEnter your choice:
                1 - publish a new property
                2 - remove a property
                3 - display all available properties
                4 - list all of my properties
                5 - filter properties
                6 - main menu
                """);
    }

    // Constructor
    public RealEstate() {
        this.users = new User[0];
        this.properties = new Property[0];
        this.cities = new City[10];
        cities[0] = new City("Beer Sheva", "Negev", new String[]{"Rager Blvd", "Heil HaHandasa St", "Hizkiyahu Hanavi St"});
        cities[1] = new City("Eilat", "South", new String[]{"HaShatakh St", "HaMe'ena St", "Yefe Nof St"});
        cities[2] = new City("Tel Aviv", "Center", new String[]{"Dizengoff St", "Rothschild Blvd", "Ben Yehuda St"});
        cities[3] = new City("Herzliya", "Sharon", new String[]{"Sokolov St", "HaKlaus St", "HaBanim St"});
        cities[4] = new City("Haifa", "North", new String[]{"Horev St", "Mount Carmel St", "Herzliya St"});
        cities[5] = new City("Ashdod", "South", new String[]{"HaTayelet St", "Harav Herzog St", "Harimon St"});
        cities[6] = new City("Jerusalem", "Center", new String[]{"Jaffa St", "King David St", "Ben Yehuda St"});
        cities[7] = new City("Kfar Saba", "Sharon", new String[]{"Weizmann St", "Herzl St", "Hatzafon St"});
        cities[8] = new City("Nazareth", "North", new String[]{"Paulus VI St", "HaGalil St", "Old City"});
        cities[9] = new City("Dimona", "Negev", new String[]{"HaYeelim St", "HaNarkiss St", "HaRakefet St"});
        boolean program = true;
        while (program){
            this.printMenu();
            int choice = scanner.nextInt();
            switch (choice){
                case CHOICE_SIGN_UP -> this.createUser();
                case CHOICE_LOGIN -> {
                    User user = login();
                    if (user != null){
                        while (choice != LOGOUT) {
                            this.printInnerMenu();
                            choice = scanner.nextInt();
                            switch (choice) {
                                case LOGOUT -> {
                                    break;
                                }
                                case PUBLISH_NEW_PROPERTY -> {
                                    if (postNewProperty(user)){
                                        System.out.println("Property added successfully.");
                                    } else {
                                        System.out.println("Failed to publish new property.");
                                    }
                                }
                                case REMOVE_PROPERTY -> removeProperty(user);
                                case DISPLAY_ALL_PROPERTIES -> printAllProperties();
                                case LIST_MY_PROPERTIES -> printProperties(user);
                                case FILTER_PROPERTIES -> {
                                    Property[] matchingProperties = search();
                                    if (matchingProperties.length == 0){
                                        System.out.println("No properties found.");
                                    } else {
                                        System.out.println("Properties matching your criteria: ");
                                        for (Property property: matchingProperties){
                                            System.out.println(property);
                                        }
                                    }
                                }
                            }
                        }

                    } else {
                        System.out.println("No such user!");
                    }
                }
                case FINISH_PROGRAM -> program=false;
                default ->  {break;}
            }
        }
    }

    // Allows to create a new user under provided rules
    public void createUser(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("New user registration: ");

        String username;
        do {
            System.out.println("Enter a username: ");
            username = scanner.nextLine();
            if (!isAvailableUsername(username)){
                System.out.println("Username is not available");
            }
        } while (!isAvailableUsername(username));

        String password = null;
        do {
            System.out.println("Enter a password: ");
            password = scanner.nextLine();
            if (!isStrongPassword(password)){
                System.out.println("Invalid password");
            }
        } while (!isStrongPassword(password));

        String phoneNumber = null;
        do {
            System.out.println("Enter a phone number: ");
            phoneNumber = scanner.nextLine();
            if (!validPhoneNumber(phoneNumber)){
                System.out.println("Invalid phone number");
            }
        } while (!validPhoneNumber(phoneNumber));

        boolean userType = false;
        System.out.println("Choose account type: \n1-Broker \n2-Regular User");
        int type = scanner.nextInt();
        if (type == 1){
            userType = true;
        } else {
            userType = false;
        }
        User user = new User(username, password, phoneNumber, userType);
        addUserToArray(user);
    }

    // Adds created user to array of users
    public void addUserToArray(User user){
        User[] temp = new User[this.users.length + 1];
        for (int i = 0; i< users.length; i++){
            temp[i] = this.users[i];
        }
        temp[temp.length -1] = user;
        this.users = temp;
    }

    //Checks if the username is available
    public boolean isAvailableUsername(String username) {
        boolean available = true;
        for (int i = 0; i < this.users.length ; i++){
            if (this.users[i].getUsername().equals(username)) {
                available = false;
                break;
            }
        }
        return available;
    }

    // Checks if the password is strong according to rules
    public boolean isStrongPassword(String password){
        return password.length() >= 5 && containsDigits(password) && containsCharacters(password);
    }

    //Helps isStrongPassword(String password) to check if password contains digits
    public boolean containsDigits(String password){
        boolean contains = false;
        for (int i = 0 ; i < password.length(); i++){
            char currentChar = password.charAt(i);
            for (int digit = 0; digit <= 9; digit++){
                if (currentChar - 'a' == digit ){
                    contains = true;
                    break;
                }
            }
        }
        return contains;
    }

    //Helps isStrongPassword(String password) to check if password contains characters
    public boolean containsCharacters(String password){
        boolean exists = false;
        final String[] CHARACTERS = {"$", "%", "_"};
        for (int i = 0 ; i < CHARACTERS.length; i++){
            if (password.contains(CHARACTERS[i])){
                exists=true;
                break;
            }
        }
        return exists;
    }

    //Checks if the number is correct
    public boolean validPhoneNumber(String phoneNumber){
        return phoneNumber.length() == 10 && phoneNumber.startsWith("05") && phoneNumber.matches("[0-9]+");
    }

    //Method for user to login, using username and password
    public User login(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your username: ");
        String username = scanner.nextLine();
        System.out.println("Enter your password: ");
        String password = scanner.nextLine();
        User found = null;
        for (int i =0; i < this.users.length; i++ ){
            if (this.users[i].checkCredential(username, password)){
                found = this.users[i];
                break;
            }
        }
        return found;
    }

    //Saves created property to array of properties
    public boolean addPropertyToArray(Property property){
        if (property == null){
            return false;
        }
        Property[] temp = new Property[this.properties.length + 1];
        for (int i = 0; i< properties.length; i++){
            temp[i] = this.properties[i];
        }
        temp[temp.length -1] = property;
        this.properties = temp;
        return true;
    }

    //Checks how many properties user already published
    private int getPropertyCountByUser(User user){
        int count = 0;
        for (Property property : this.properties) {
            if (property != null && property.getUser().equals(user)) {
                count++;
            }
        }
        return count;
    }

    //Allows a user to post a new property by choosing desired options, checks if options appear on the lists
    //Using all the details, the system will create a Property object and add it to the list of properties in the system
    //Returns value and an appropriate message if the property published or not
    public boolean postNewProperty ( User user) {
        int userPropertyCount = getPropertyCountByUser(user);
        int maxProperties = user.isBroker() ? 5 : 2;

        if (userPropertyCount >= maxProperties) {
            System.out.println("Reached limit of posted properties.");
            return false;
        }

        System.out.println("List of cities:");
        for (int i = 0; i < cities.length; i++) {
            System.out.println(cities[i].getCity());
        }
        scanner.nextLine();
        System.out.println("City: ");
        String cityName = scanner.nextLine();

        City chosenCity = null;
        for (City city : cities) {
            if (city.getCity().equalsIgnoreCase(cityName)) {
                chosenCity = city;
                cityName = city.getCity();
                break;
            }
        }
        if (chosenCity == null) {
            System.out.println("Not a valid city.");
            return false;
        }

        System.out.println("List of streets in this city: ");
        for (String street : chosenCity.getStreets()) {
            System.out.println(street);
        }
        System.out.println("Street: ");
        String streetName = scanner.nextLine();

        boolean streetAppear = false;
        for (String street : chosenCity.getStreets()) {
            if (street.equalsIgnoreCase(streetName)) {
                streetAppear = true;
                streetName = street;
                break;
            }
        }

        if (!streetAppear) {
            System.out.println("Not a valid street");
            return false;
        }

        System.out.println("""
                Enter the type of property:
                1 Regular apartment.
                2 Penthouse apartment.
                3 Private house.
                """);
        int propertyType = scanner.nextInt();
        scanner.nextLine();

        if (propertyType < 1 || propertyType > 3) {
            System.out.println("Invalid property type. Please choose a valid option.");
            return false;
        }

        int floor = 0;
        if (propertyType == 1) {
            System.out.println("Enter the floor: ");
            floor = scanner.nextInt();
        }

        System.out.println("Enter the amount of rooms: ");
        int roomAmount = scanner.nextInt();
        System.out.println("Enter the property number: ");
        int propertyNumber = scanner.nextInt();
        System.out.println("Enter ");
        System.out.println("""
                Is the property for rent or sale :
                1 Rent.
                2 Sale.""");
        int rentOrSale = scanner.nextInt();

        boolean isForRent;
        if (rentOrSale == 1) {
            isForRent = true;
        } else if (rentOrSale == 2) {
            isForRent = false;
        } else {
            System.out.println("Invalid option.");
            return false;
        }

        System.out.println("Enter required price: ");
        int price = scanner.nextInt();

        Property newProperty = new Property(user, cityName, streetName, roomAmount, price, propertyType, isForRent, propertyNumber, floor);
        boolean addedNewProperty = addPropertyToArray(newProperty);
        if (addedNewProperty){
            return true;
        } else {
            return false;
        }
    }

    //Checks if user created any properties
    //Removes chosen property from array of properties and prints appropriate message
    public void removeProperty(User user){
        int[] userPropertyIndexes = new int[properties.length];
        int userPropertyCount = 0;

        for (int i = 0; i < properties.length; i++) {
            if (properties[i] != null && properties[i].getUser().equals(user)){
                userPropertyIndexes[userPropertyCount] = i;
                userPropertyCount ++;
            }
        }

        if (userPropertyCount == 0) {
            System.out.println("No published properties.");
            return;
        }

        System.out.println("List of yours published properties: ");
        for (int i = 0; i < userPropertyCount; i++) {
            int index = userPropertyIndexes[i];
            System.out.println((i+1) + " -" + properties[index]);
        }

        System.out.println("Please enter the number of property you want to delete: ");
        int choice = scanner.nextInt();

        if (choice < 1 || choice > userPropertyCount){
            System.out.println("Invalid choice. Please try again. ");
            return;
        }

        int propertyIndexToRemove = userPropertyIndexes[choice - 1];

        for (int i = propertyIndexToRemove; i < properties.length - 1; i++) {
            properties[i] = properties[i+1];
        }

        properties[properties.length -1 ] = null;
        System.out.println("Property removed successfully.");
    }

    //Prints all properties created by all users
    public void printAllProperties(){
        boolean propertiesFound = false;
        for (int i = 0; i < this.properties.length; i++) {
            if (properties[i] != null){
                System.out.println(this.properties[i]);
                propertiesFound = true;
            }
        }

        if (!propertiesFound) {
            System.out.println("No properties found.");
        }
    }

    //Prints all properties created by logged User
    public void printProperties(User user){
        boolean propertiesFound = false;
        for (int i = 0; i < this.properties.length; i++) {
            Property property = this.properties[i];
            if (properties[i] != null){
                if (property.getUser().equals(user)) {
                    System.out.println(property);
                    propertiesFound = true;
                }

            }
        }

        if (!propertiesFound) {
            System.out.println("No properties found.");
        }
    }

    //Checks if properties array contains empty properties
    public boolean nullProperties(){
        for (Property property : properties){
            if (property != null){
                return false;
            }
        }
        return true;
    }

    //Searches for properties in the properties array based on user choices
    //Checks if property contains desired choices, creates a new properties array that contains only filtered properties
    public Property[] search() {

        if (nullProperties()){
            return new Property[0];
        }

        boolean skipRent = false;
        boolean forRent = false;

        System.out.println("Is it for rent or sale?");
        scanner.nextLine();
        String rentStatus = scanner.nextLine();
        if (rentStatus.equalsIgnoreCase("rent")) {
            forRent = true;
        } else if (rentStatus.equalsIgnoreCase("sale")) {
            forRent = false;
        } else if (rentStatus.equals("-999")) {
            skipRent = true;
        }

        System.out.println("What type of property do you want: (choose the number) \n1 Regular apartment.\n2 Penthouse apartment.\n3 Private house.");
        int propertyType = scanner.nextInt();
        System.out.println("What is the desired number of rooms?");
        int numOfRooms = scanner.nextInt();
        System.out.println("What is the price range? (minimum and maximum)");
        int minPrice = scanner.nextInt();
        scanner.nextLine();
        int maxPrice = scanner.nextInt();

        Property[] tempProperties = new Property[properties.length];
        int matched = 0;

        for (int i = 0; i < properties.length; i++) {
            Property property = properties[i];
            if (property != null) {
                boolean matchesCriteria = true;

                if (!skipRent){
                    if (property.isForRent() != forRent){
                        matchesCriteria = false;
                    }
                }

                if (propertyType != -999 && property.getType() != propertyType){
                    matchesCriteria = false;
                }

                if (numOfRooms != -999 && property.getNumberOfRooms() != numOfRooms){
                    matchesCriteria = false;
                }

                if ((minPrice != -999 && property.getPrice() < minPrice) || (maxPrice != -999 && property.getPrice() > maxPrice)){
                    matchesCriteria = false;
                }

                if (matchesCriteria) {
                    tempProperties[matched] = property;
                    matched++;
                }
            }
        }

        Property[] result = new Property[matched];
        for (int i = 0; i < matched; i++) {
            result[i] = tempProperties[i];
        }

        return result;
    }

    //Scanner
    public static final Scanner scanner = new Scanner(System.in);
}
