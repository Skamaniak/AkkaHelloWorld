package cz.jskrabal.concurrency.akka.message;

import akka.actor.ActorRef;

/**
 * Created by Jan Skrabal skrabalja@gmail.com
 */
public class Transfer {
    private ActorRef from;
    private ActorRef to;
    private long amount;

    public Transfer(long amount, ActorRef from, ActorRef to) {
        this.amount = amount;
        this.from = from;
        this.to = to;
    }

    public ActorRef getFrom() {
        return from;
    }

    public ActorRef getTo() {
        return to;
    }

    public long getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transfer that = (Transfer) o;

        if (amount != that.amount) return false;
        if (from != null ? !from.equals(that.from) : that.from != null) return false;
        return to != null ? to.equals(that.to) : that.to == null;

    }

    @Override
    public int hashCode() {
        int result = from != null ? from.hashCode() : 0;
        result = 31 * result + (to != null ? to.hashCode() : 0);
        result = 31 * result + (int) (amount ^ (amount >>> 32));
        return result;
    }
}
