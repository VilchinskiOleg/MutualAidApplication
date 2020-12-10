package org.tms.finalproject.entity.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tms.finalproject.entity.Location;
import org.tms.finalproject.entity.User;
import org.tms.finalproject.utils.OrderStatus;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.List;

@Entity
@DiscriminatorValue(value = "UN_PAID")

@Data
@NoArgsConstructor
public class UnpaidOrder extends Order {
    private String priority;

    public UnpaidOrder(long id,
                       String title,
                       String description,
                       User author,
                       User executor,
                       List<User> applicantsToOrder,
                       Location location,
                       OrderStatus status,
                       String priority) {
        super(id, title, description, author, executor, applicantsToOrder, location, status);
        this.priority = priority;
    }



    @Override
    public String toString() {
        return "Priority: " + priority;
    }
}
