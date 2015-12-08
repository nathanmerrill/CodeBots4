package codebots.gameobjects;

import codebots.controller.Round;

import java.util.*;
import java.util.stream.Collectors;

public final class AddressBook {

    public enum AddressType{
        NEUTRAL,
        TRUSTED,
        UNTRUSTED,
        TO_ATTACK,
        TO_DEFEND,
    }

    private final Map<IPAddress, AddressType> addressType;
    private final List<IPAddress> allAddresses;
    private final Round round;

    public AddressBook(Round round){
        addressType = new HashMap<>();
        this.round = round;
        allAddresses = new ArrayList<>();
    }

    public void add(IPAddress address){
        add(address, AddressType.NEUTRAL);
    }

    public void add(IPAddress address, AddressType type){
        allAddresses.add(address);
        addressType.put(address, type);
    }

    public void addAll(Collection<IPAddress> addresses){
        addAll(addresses, AddressType.NEUTRAL);
    }
    public void addAll(Collection<IPAddress> addresses, AddressType type){
        for (IPAddress address: addresses){
            addressType.put(address, type);
        }
        this.allAddresses.addAll(addresses);
    }

    public void clear(){
        allAddresses.clear();
        ensureContainsAddress();
    }

    public void clear(AddressType type){
        allAddresses.removeAll(getAddressesOfType(type));
        ensureContainsAddress();
    }

    public void remove(IPAddress address){
        allAddresses.remove(address);
        ensureContainsAddress();
    }

    private void ensureContainsAddress(){
        if (allAddresses.size() == 0){
            IPAddress newAddress = round.getRandomAddress();
            allAddresses.add(newAddress);
            addressType.put(newAddress, AddressType.NEUTRAL);
        }
    }

    public AddressType getAddressType(IPAddress address){
        return addressType.get(address);
    }

    public List<IPAddress> getAddressesOfType(AddressType type) {
        return addressType.entrySet().stream()
                .filter(e -> e.getValue() == type)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public int size(){
        return allAddresses.size();
    }

    public IPAddress getAddress(int n){
        return allAddresses.get(n);
    }

    public List<IPAddress> allAddresses(){
        return new ArrayList<IPAddress>(allAddresses);
    }
}
