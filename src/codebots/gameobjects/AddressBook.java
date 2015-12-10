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
    private final Round round;

    public AddressBook(Round round){
        addressType = new HashMap<>();
        this.round = round;
    }

    public void add(IPAddress address){
        add(address, AddressType.NEUTRAL);
    }

    public void add(IPAddress address, AddressType type){
        addressType.put(address, type);
    }

    public void addAll(Collection<IPAddress> addresses){
        addAll(addresses, AddressType.NEUTRAL);
    }
    public void addAll(Collection<IPAddress> addresses, AddressType type){
        for (IPAddress address: addresses){
            addressType.put(address, type);
        }
    }

    public void clear(){
        addressType.clear();
        ensureContainsAddress();
    }

    public void clear(AddressType type){
        Iterator<Map.Entry<IPAddress, AddressType>> iter = addressType.entrySet().iterator();
        while (iter.hasNext()){
            if (iter.next().getValue() == type){
                iter.remove();
            }
        }
        ensureContainsAddress();
    }

    public void remove(IPAddress address){
        addressType.remove(address);
        ensureContainsAddress();
    }

    private void ensureContainsAddress(){
        if (addressType.size() == 0){
            addressType.put(round.getRandomAddress(), AddressType.NEUTRAL);
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
        return addressType.size();
    }

    public IPAddress getAddress(int n){
        if (n < 0 || n > size()){
            throw new IndexOutOfBoundsException();
        }
        for (IPAddress address : addressType.keySet()){
            if (n == 0){
                return address;
            }
            n--;
        }
        throw new IndexOutOfBoundsException();
    }

    public List<IPAddress> allAddresses(){
        return new ArrayList<IPAddress>(addressType.keySet());
    }
}
