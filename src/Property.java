public class Property{
    private String city;
    private String street;
    private int numberOfRooms;
    private int price;
    private int type;
    private boolean forRent;
    private int houseNumber;
    private int floorNumber;
    private User user;

    //Constructor
    public Property(User user, String city, String street, int numberOfRooms, int price, int type, boolean forRent, int houseNumber, int floorNumber) {
        this.user = user;
        this.city = city;
        this.street = street;
        this.numberOfRooms = numberOfRooms;
        this.price = price;
        this.type = type;
        this.forRent = forRent;
        this.houseNumber = houseNumber;
        this.floorNumber = floorNumber;
    }

    //Remakes type into String for toString()
    public String typeToString (int type){
        String tempType = "";
        switch (this.type){
            case 1 -> {tempType = "Regular apartment";}
            case 2 -> {tempType = "Penthouse apartment";}
            case 3 -> {tempType = "Private house";}
        }
        return tempType;
    }

    //Method that prints property details
    public String toString() {
        String rentalStatus = this.forRent ? "for rent" : "for sale";
        String propertyType = typeToString(this.type);
        String floor = "";
        if (this.type == 1){
            floor = ", floor " + floorNumber;
        }

        return "\n" + this.city + " - " + this.street + " " + this.houseNumber + "." +
                "\n" + propertyType + " - " + rentalStatus + ": " + this.numberOfRooms + " rooms" + floor +
                "\n" + "Price: $" + this.price +
                "\n" + "Contact info: " + this.user;
    }

    public User getUser() {
        return user;
    }


    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public int getPrice() {
        return price;
    }

    public int getType() {
        return type;
    }

    public boolean isForRent() {
        return forRent;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public int getFloorNumber() {
        return floorNumber;
    }
}
