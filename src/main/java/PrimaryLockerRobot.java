import exceptions.ErrorMessageException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class PrimaryLockerRobot {

  private List<Locker> lockers;
  private Map<Integer, Integer> ticketLockerMap = new HashMap<>();

  public PrimaryLockerRobot(List<Locker> lockers) {
    this.lockers = lockers;
  }

  public Ticket savePackage(Package aPackage) throws exceptions.ErrorMessageException {
    int i = 0;
    for (Locker locker : lockers) {
      if (locker.getCapacity() > 0) {
        Ticket ticket = locker.savePackage(aPackage);
        bindTicketWithLabel(ticket, i);
        return ticket;
      }
      i++;
    }
    throw new ErrorMessageException("All lockers are full");
  }

  public Package getPackage(Ticket ticket) throws ErrorMessageException {
    Package aPackage = null;
    for (Locker locker : lockers) {
      try {
        aPackage = locker.getPackage(ticket);
      } catch (ErrorMessageException ignored) {
      }
    }
    if (aPackage == null) {
      throw new ErrorMessageException("The ticket is invalid");
    }
    return aPackage;
  }

  public int getLockerLabelByTicket(Ticket ticket) {
    return this.ticketLockerMap.get(System.identityHashCode(ticket));
  }

  public void bindTicketWithLabel(Ticket ticket, int label) {
    this.getTicketLockerMap().put(System.identityHashCode(ticket), label);
  }
}
