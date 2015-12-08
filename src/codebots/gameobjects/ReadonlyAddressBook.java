package codebots.gameobjects;

import java.util.*;

public final class ReadonlyAddressBook {

    private final AddressBook book;

    public ReadonlyAddressBook(AddressBook book){
        this.book = book;
    }

    public AddressBook.AddressType getAddressType(IPAddress address){
        return book.getAddressType(address);
    }

    public int size(){
        return book.size();
    }

    public IPAddress getAddress(int n){
        return book.getAddress(n);
    }

    public List<IPAddress> getAddressesOfType(AddressBook.AddressType type) {
        return book.getAddressesOfType(type);
    }

    public List<IPAddress> allAddresses(){
        return book.allAddresses();
    }
}
