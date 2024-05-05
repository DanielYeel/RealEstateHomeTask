import java.util.Arrays;

public class City {
    private String city;
    private String district;
    private String[] streets;

    //Constructor
    public City(String city, String district, String[] streets) {
        this.city = city;
        this.district = district;
        this.streets = streets;
    }

    public String getCity() {
        return city;
    }

    public String getDistrict() {
        return district;
    }

    public String[] getStreets() {
        return streets;
    }
}
