package codebots.bots;

import codebots.bot.CodeBot;
import codebots.bot.ReadonlyBot;
import codebots.gameobjects.*;
import java.util.*;

public class TrustBot extends CodeBot {
    @Override
    public IPAddress selectMessageRecipient() {
        AddressBook book = getAddressBook();
        return book.getAddress(getRandom().nextInt(book.size()));
    }

    @Override
    public Message sendMessage() {
        AddressBook book = getAddressBook();
        return new Message(Message.MessageType.INFORM, book.getAddress(getRandom().nextInt(book.size())));
    }

    @Override
    public void processMessage(IPAddress s, Message m) {
        AddressBook book = getAddressBook();
        if(m.getAddress() != null){
            if(m.getType() == Message.MessageType.ATTACK){
                book.add(m.getAddress(), AddressBook.AddressType.TO_ATTACK);
            }
            else if(m.getType() == Message.MessageType.HELP){
                book.add(m.getAddress(), AddressBook.AddressType.TO_DEFEND);
            }
            else if(m.getType() == Message.MessageType.CONFIRM){
                book.add(m.getAddress(), AddressBook.AddressType.TRUSTED);
            }
            else if(m.getType() == Message.MessageType.REJECT){
                book.add(m.getAddress(), AddressBook.AddressType.UNTRUSTED);
            }
            else if(m.getType() == Message.MessageType.AVOID){
                book.remove(m.getAddress());
            }
            else{
                book.add(m.getAddress());
            }
        }else{
            Message msg = new Message(m.getType(), s);
            processMessage(s, msg);
        }
    }

    @Override
    public FunctionType selectFunctionToBlock() {
        return FunctionType.SELECT_FUNCTION_TO_BLOCK;
    }

    @Override
    public IPAddress selectAttackTarget() {
        AddressBook book = getAddressBook();
        List<IPAddress> l;
        l = book.getAddressesOfType(AddressBook.AddressType.TO_ATTACK);
        Iterator<IPAddress> I = l.iterator();
        if(!I.hasNext())
            return book.getAddress(getRandom().nextInt(book.size()));
        return I.next();
    }

    @Override
    public void readData(ReadonlyBot bot) {
        AddressBook myBook = getAddressBook();
        ReadonlyAddressBook hisBook = bot.getAddressBook();
        AddressBook.AddressType[] values = AddressBook.AddressType.values();
        for(int i=0;i<values.length;i++){
            myBook.addAll(hisBook.getAddressesOfType(values[i]), values[i]);
        }
    }

    @Override
    public FunctionType selectFunctionToReplace() {
        return getRandom().nextInt(2)==1?FunctionType.GET_FLAG:FunctionType.SELECT_FUNCTION_TO_BLOCK;
    }

    @Override
    public String getFlag() {
        return "Trust in Trust!";
    }
}