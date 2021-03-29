package liadov.mypackage.lesson3;

public class Address {
    private String city;
    private String street;
    private String houseNumber;
    private int apartment;

    public Address(String city, String street, String houseNumber, int apartment) {
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
        this.apartment = apartment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;

        Address address = (Address) o;

        if (apartment != address.apartment) return false;
        if (city != null ? !city.equals(address.city) : address.city != null) return false;
        if (street != null ? !street.equals(address.street) : address.street != null) return false;
        return houseNumber != null ? houseNumber.equals(address.houseNumber) : address.houseNumber == null;
    }

    @Override
    public int hashCode() {
        int result = city != null ? city.hashCode() : 0;
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (houseNumber != null ? houseNumber.hashCode() : 0);
        result = 31 * result + apartment;
        return result;
    }
}
