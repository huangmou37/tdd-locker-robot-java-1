import exceptions.ErrorMessageException;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;

@Data
public class Locker {

  private int capacity;
  private Map<Integer, Package> record = new HashMap<>();

  public Locker(int capacity) {
    this.capacity = capacity;
  }

  public Ticket savePackage(Package aPackage) throws ErrorMessageException {
    if (this.capacity <= 0) {
      throw new ErrorMessageException("The locker is full");
    }
    Ticket ticket = new Ticket();
    this.record.put(System.identityHashCode(ticket), aPackage);
    this.capacity -= 1;
    return ticket;
  }

  public Package getPackage(Ticket ticket) throws ErrorMessageException {
    Package aPackage = this.record.get(System.identityHashCode(ticket));
    if (aPackage == null) {
      throw new ErrorMessageException("The ticket is invalid");
    }
    record.remove(System.identityHashCode(ticket));
    this.capacity += 1;
    return aPackage;
  }
}
