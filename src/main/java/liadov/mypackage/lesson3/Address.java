package liadov.mypackage.lesson3;

public class Address {
    private String city;
    private String street;
    private String houseNumber;
    private int apartmentNumber;

    public Address(String city, String street, String houseNumber, int apartmentNumber) {
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
        this.apartmentNumber = apartmentNumber;
    }

    /**
     * Method returns String value of city variable
     *
     * @return String value of city variable
     */
    public String getCity() {
        return city;
    }

    /**
     * Method returns String value of street variable
     *
     * @return String value of street variable
     */
    public String getStreet() {
        return street;
    }

    /**
     * Method returns String value of houseNumber variable
     *
     * @return String value of houseNumber variable
     */
    public String getHouseNumber() {
        return houseNumber;
    }

    /**
     * Method returns int value of apartmentNumber variable
     *
     * @return int value of apartmentNumber variable
     */
    public int getApartmentNumber() {
        return apartmentNumber;
    }

    @Override
    public String toString() {
        return "Address{" +
                "city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", houseNumber='" + houseNumber + '\'' +
                ", apartmentNumber=" + apartmentNumber +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;

        Address address = (Address) o;

        if (apartmentNumber != address.apartmentNumber) return false;
        if (city != null ? !city.equals(address.city) : address.city != null) return false;
        if (street != null ? !street.equals(address.street) : address.street != null) return false;
        return houseNumber != null ? houseNumber.equals(address.houseNumber) : address.houseNumber == null;
    }

    @Override
    public int hashCode() {
        int result = city != null ? city.hashCode() : 0;
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (houseNumber != null ? houseNumber.hashCode() : 0);
        result = 31 * result + apartmentNumber;
        return result;
    }
}
